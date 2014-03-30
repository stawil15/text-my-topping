package ideasPackage;

import java.util.ArrayList;

import javazoom.jl.player.Player;
import processing.core.PApplet;

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

	public void setCamera(Camera camera)
	{
		this.camera = camera;
		helper.setCamera(camera);
	}

	public void addElement(GridCoordinate coordinates, Collidable entity)
	{
		if (isValidPosition(coordinates))
		{
			collisionGrid[coordinates.getGridX()][coordinates.getGridY()] = entity;
		}

		if (entity != null && entity.getClass() == MoveableObject.class
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

	public Collidable getEntityAt(GridCoordinate coordinates)
	{
		if (!isValidPosition(coordinates))
			return null;

		return collisionGrid[coordinates.getGridX()][coordinates.getGridY()];
	}

	public Collidable removeElementAt(GridCoordinate coordinates)
	{
		Collidable returnedElement = null;
		if (isValidPosition(coordinates))
		{
			returnedElement = getEntityAt(coordinates);
			collisionGrid[coordinates.getGridX()][coordinates.getGridY()] = null;
		}

		return returnedElement;
	}

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
				if (getEntityAt(coordinate).getClass() != MoveableObject.class)
					doInteraction(getEntityAt(coordinate), PlayerCharacter.BUMP_INTERACTION);
			} else if (entity.getClass() == MoveableObject.class)
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

	public boolean moveElement(Collidable entity)
	{
		if (canElementMove(entity))
		{
			removeElementAt(entity.getCoordinates());
			addElement(getNextCoordinate(entity), entity);
			return true;
		} else
		{
			return false;
		}
	}

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

	public void draw()
	{
		helper.draw(collisionGrid);
	}

	public boolean isValidPosition(GridCoordinate coordinates)
	{
		if (coordinates == null)
		{
			return false;
		}
		return isValidPosition(coordinates.getGridX(), coordinates.getGridY());
	}

	private boolean isValidPosition(int xPosition, int yPosition)
	{
		return (xPosition >= 0 && xPosition < collisionGrid.length && yPosition >= 0 && yPosition < collisionGrid[0].length);
	}

	public int getGridWidth()
	{
		return collisionGrid.length;
	}

	public int getGridHeight()
	{
		return collisionGrid[0].length;
	}

	public void addDuplicateObject(Drawable entity)
	{
		helper.addDuplicate(entity);
	}

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

	public SceneryGrid getCorrespondingSceneryGrid()
	{
		return correspondingSceneryGrid;
	}

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
					removeElementAt(moveableObjects.get(index).getCoordinates());
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
