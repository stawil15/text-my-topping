package ideasPackage;

public class CollisionGrid
{
	private Collidable collisionGrid[][];
	private Camera camera;
	private GridHelper helper;

	public CollisionGrid(int xEntities, int yEntities)
	{
		collisionGrid = new Collidable[xEntities][yEntities];
		helper = new GridHelper(collisionGrid, camera);
	}
	
	public void setCamera(Camera camera)
	{
		this.camera = camera;
		helper.setCamera(camera);
	}

	public void addElement(GridCoordinate coordinates, Collidable element)
	{
		if (isValidPosition(coordinates))
		{
			collisionGrid[coordinates.getGridX()][coordinates.getGridY()] = element;
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
		GridCoordinate coordinate = new GridCoordinate(entity.getCoordinates()
				.getGridX(), entity.getCoordinates().getGridY());

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

	public void doInteraction(Collidable entity)
	{
		Collidable entityToInteractWith = getEntityAt(getNextCoordinate(entity));
		if (entityToInteractWith != null)
		{
			if (entityToInteractWith.getClass() == NonPlayerCharacter.class && entity.getClass() == PlayerCharacter.class)
			{
				NonPlayerCharacter npc = (NonPlayerCharacter)(entityToInteractWith);
				PlayerCharacter player = (PlayerCharacter)(entity);
				npc.setDirection(player.getOppositeDirection());
			}
			entityToInteractWith.doInteract();
		}
	}

	public void draw()
	{
		helper.draw(collisionGrid);
	}

	private boolean isValidPosition(GridCoordinate coordinates)
	{
		if (coordinates == null)
		{
			return false;
		}
		return isValidPosition(coordinates.getGridX(), coordinates.getGridY());
	}

	private boolean isValidPosition(int xPosition, int yPosition)
	{
		return (xPosition >= 0 && xPosition < collisionGrid.length
				&& yPosition >= 0 && yPosition < collisionGrid[0].length);
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
}
