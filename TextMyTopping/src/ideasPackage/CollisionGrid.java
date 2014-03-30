package ideasPackage;

import java.util.ArrayList;

/*
 * This class creates a grid of collidable objects in which they can collide with each other. 
 */
public class CollisionGrid
{
	private Collidable collisionGrid[][];
	private Camera camera;
	private GridHelper helper;
	private SceneryGrid correspondingSceneryGrid;
	private ArrayList<MoveableObject> moveableObjects;
	private ArrayList<GridCoordinate> moveableObjectCoordinates;
	private ArrayList<SceneryObject> originalSceneryObjects;
	private ArrayList<Hole> originalHoles;
	private boolean wasLoadedMoreThanOnce = false;

	public CollisionGrid(int xEntities, int yEntities, SceneryGrid correspondingSceneryGrid)
	{
		collisionGrid = new Collidable[xEntities][yEntities];
		helper = new GridHelper(collisionGrid, camera);
		this.correspondingSceneryGrid = correspondingSceneryGrid;
		moveableObjects = new ArrayList<MoveableObject>();
		moveableObjectCoordinates = new ArrayList<GridCoordinate>();
		originalSceneryObjects = new ArrayList<SceneryObject>();
		originalHoles = new ArrayList<Hole>();
	}

	// Sets the camera of the grid
	public void setCamera(Camera camera)
	{
		this.camera = camera;
		helper.setCamera(camera);
	}

	// add an element to the grid
	public void addEntity(GridCoordinate coordinates, Collidable entity)
	{
		if (isValidPosition(coordinates))
		{
			collisionGrid[coordinates.getGridX()][coordinates.getGridY()] = entity;
		}

		if (entity != null && entity instanceof MoveableObject
				&& !moveableObjects.contains((MoveableObject) entity))
		{
			moveableObjects.add((MoveableObject) entity);
			moveableObjectCoordinates.add(new GridCoordinate(coordinates.getGridX(), coordinates.getGridY()));
		}

		if (entity != null && entity.getClass() == Hole.class)
		{
			originalHoles.add((Hole) entity);
			originalSceneryObjects.add(correspondingSceneryGrid.getObjectAt(entity.getCoordinates()));
		}
	}

	// Get the entity at coordinates
	public Collidable getEntityAt(GridCoordinate coordinates)
	{
		if (!isValidPosition(coordinates))
			return null;

		return collisionGrid[coordinates.getGridX()][coordinates.getGridY()];
	}

	// This removes an entity at a certain location
	public Collidable removeEntitytAt(GridCoordinate coordinates)
	{
		Collidable returnedElement = null;
		if (isValidPosition(coordinates))
		{
			returnedElement = getEntityAt(coordinates);
			collisionGrid[coordinates.getGridX()][coordinates.getGridY()] = null;
		}

		return returnedElement;
	}

	// Returns whether an entity is able to move. Does an interaction if the entity is a palyerCharacter
	// or sets the a hole to be filled if the object is a moveable object and the next entity is a hole
	private boolean canElementMove(Collidable entity)
	{
		if (entity == null)
		{
			return false;
		}

		GridCoordinate coordinate = getNextCoordinate(entity);

		if (getEntityAt(coordinate) != null)
		{
			if (entity.getClass() == PlayerCharacter.class)
			{
				if (!(getEntityAt(coordinate) instanceof MoveableObject))
				{
					doInteraction(getEntityAt(coordinate), PlayerCharacter.BUMP_INTERACTION);
				}
			} else if (entity instanceof MoveableObject)
			{
				Collidable nextEntity = getEntityAt(getNextCoordinate(entity));
				if (nextEntity != null && nextEntity.getClass() == Hole.class)
				{
					Hole hole = (Hole) nextEntity;
					MoveableObject object = (MoveableObject) entity;
					hole.fillWithMovableObject(object);
					return true;
				}
			}
			return false;
		}

		if (!isValidPosition(coordinate))
		{
			return false;
		}

		return true;
	}

	// Get the coordinate next position of an entity based on their direction
	public static GridCoordinate getNextCoordinate(Collidable entity)
	{
		if (entity == null)
		{
			return null;
		}

		if (entity.getCoordinates() == null)
		{
			return null;
		}
		GridCoordinate coordinate = new GridCoordinate(entity.getCoordinates().getGridX(), entity.getCoordinates()
				.getGridY());

		switch (entity.getDirection())
		{
		case Character.DIRECTION_UP:
			coordinate.decrementY();
			break;
		case Character.DIRECTION_RIGHT:
			coordinate.incrementX();
			break;
		case Character.DIRECTION_DOWN:
			coordinate.incrementY();
			break;
		case Character.DIRECTION_LEFT:
			coordinate.decrementX();
			break;
		}
		return coordinate;
	}

	// Moves an element
	public boolean moveElement(Collidable entity)
	{
		if (canElementMove(entity))
		{
			removeEntitytAt(entity.getCoordinates());
			addEntity(getNextCoordinate(entity), entity);
			return true;
		} else
		{
			return false;
		}
	}

	// does an interaction of an entity and the next entity
	public void doInteraction(Collidable entity, int interactionId)
	{
		Collidable entityToInteractWith = getEntityAt(getNextCoordinate(entity));
		if (entityToInteractWith != null)
		{
			if (entityToInteractWith.getClass() == NonPlayerCharacter.class
					&& entity.getClass() == PlayerCharacter.class)
			{
				NonPlayerCharacter npc = (NonPlayerCharacter) (entityToInteractWith);
				PlayerCharacter player = (PlayerCharacter) (entity);
				npc.setDirection(player.getOppositeDirection());
			}
			entityToInteractWith.doInteract(interactionId);
		}
	}

	// Draws the entire grid
	public void draw()
	{
		helper.draw(collisionGrid);
	}

	// Returns whether a position is valid, meaning that it is within bounds and empty.
	public boolean isValidPosition(GridCoordinate coordinates)
	{
		if (coordinates == null)
		{
			return false;
		}
		return isValidPosition(coordinates.getGridX(), coordinates.getGridY());
	}
	
	// Another way to call isValidPosition
	private boolean isValidPosition(int xPosition, int yPosition)
	{
		return (xPosition >= 0 && xPosition < collisionGrid.length && yPosition >= 0 && yPosition < collisionGrid[0].length);
	}

	// gets the width of the grid in terms of the number of objects that can fit on a row
	public int getGridWidth()
	{
		return collisionGrid.length;
	}

	// gets the height of the grid in terms of the number of objects that can fit in a column
	public int getGridHeight()
	{
		return collisionGrid[0].length;
	}

	// Adds a duplicate object that can help save memory by not creating a new object for 
	// every single location
	public void addDuplicateObject(Drawable entity)
	{
		helper.addDuplicate(entity);
	}

	// Sets the player character near a door from a level
	public void setPlayerAtDoorFromLevel(String fromLevel)
	{
		// Find the door's location
		Door door = null;
		for (int x = 0; x < collisionGrid.length; x++)
		{
			for (int y = 0; y < collisionGrid[0].length; y++)
			{
				if (collisionGrid[x][y] != null && collisionGrid[x][y].getClass() == Door.class)
				{
					Door doorToCheck = (Door) collisionGrid[x][y];
					if (doorToCheck.getToLevel().equals(fromLevel))
					{
						door = doorToCheck;
					}
				}
			}
		}

		if (door == null)
		{
			return;
		}

		PlayerCharacter player = Main.getPlayer();
		GridCoordinate newPlayerCoordinates = new GridCoordinate(door.getCoordinates().getGridX(), door
				.getCoordinates().getGridY());
		switch (door.getDirection())
		{
		case Character.DIRECTION_UP:
			newPlayerCoordinates.decrementY();
			break;
		case Character.DIRECTION_RIGHT:
			newPlayerCoordinates.incrementX();
			break;
		case Character.DIRECTION_DOWN:
			newPlayerCoordinates.incrementY();
			break;
		case Character.DIRECTION_LEFT:
			newPlayerCoordinates.decrementX();
			break;
		}

		player.setCoordinates(newPlayerCoordinates);
		player.setCollisionGrid(this);
		camera.centerCameraAroundTracker();
		player.setDirection(door.getDirection());

	}

	// Remove the palyer from the grid
	public void removePlayerFromGrid()
	{
		for (int x = 0; x < collisionGrid.length; x++)
		{
			for (int y = 0; y < collisionGrid[0].length; y++)
			{
				if (collisionGrid[x][y] != null && collisionGrid[x][y] == Main.getPlayer())
				{
					collisionGrid[x][y] = null;
				}
			}
		}
	}

	// Get the scenery grid that corresponds to the collision grid
	public SceneryGrid getCorrespondingSceneryGrid()
	{
		return correspondingSceneryGrid;
	}

	// Reset all the holes and moveable objects
	public void resetCollisionGrid()
	{
		if (wasLoadedMoreThanOnce)
		{
			for (int index = 0; index < moveableObjects.size(); index++)
			{
				GridCoordinate originalCoordinates = moveableObjectCoordinates.get(index);
				System.out.println("index " + index);
				if (collisionGrid[originalCoordinates.getGridX()][originalCoordinates.getGridY()] == null)
				{
					removeEntitytAt(moveableObjects.get(index).getCoordinates());
					moveableObjects.get(index).getCoordinates().setGridX(originalCoordinates.getGridX());
					moveableObjects.get(index).getCoordinates().setGridY(originalCoordinates.getGridY());
					moveableObjects.get(index).setDestroyOnMove(null);
					collisionGrid[originalCoordinates.getGridX()][originalCoordinates.getGridY()] = moveableObjects
							.get(index);
				}
			}

			for (int index = 0; index < originalHoles.size(); index++)
			{
				GridCoordinate originalCoordinates = originalHoles.get(index).getCoordinates();
				// Hole was removed
				if (collisionGrid[originalCoordinates.getGridX()][originalCoordinates.getGridY()] == null)
				{
					collisionGrid[originalCoordinates.getGridX()][originalCoordinates.getGridY()] = originalHoles
							.get(index);
					correspondingSceneryGrid.forceAddObject(originalCoordinates, originalSceneryObjects.get(index));

				}
			}
		}

		wasLoadedMoreThanOnce = true;
	}
}
