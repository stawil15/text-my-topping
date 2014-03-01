package ideasPackage;

public class CollisionGrid
{
	private Collidable collisionGrid[][];
	
	public CollisionGrid(int x_entities, int y_entities)
	{
		collisionGrid = new Collidable[x_entities][y_entities];
	}
	
	public void addElement(GridCoordinate coordinates, Collidable element)
	{
		if (collisionGrid.length == 0)
			return;
		if (isValidPosition(coordinates))
		{
			collisionGrid[coordinates.getGridX()][coordinates.getGridY()] = element;
		}
	}
	
	public Collidable removeElementAt(GridCoordinate coordinates)
	{
		if (collisionGrid.length == 0)
			return null;
		
		
		Collidable returnedElement = null;
		if (isValidPosition(coordinates))
		{
			returnedElement = collisionGrid[coordinates.getGridX()][coordinates.getGridY()];
			collisionGrid[coordinates.getGridX()][coordinates.getGridY()] = null;
		}
		
		return returnedElement;
	}
	
	private boolean canElementMove(Collidable entity)
	{
		if (entity==null)
		{
			return false;
		}
		
		if (collisionGrid.length == 0)
			return false;
		
		GridCoordinate coordinate = getNextCoordinate(entity);
		
		if (!isValidPosition(coordinate))
		{
			return false;
		}
		
		if (collisionGrid[coordinate.getGridX()][coordinate.getGridY()] != null)
		{
			return false;
		}
			
		
		return true;
	}

	private GridCoordinate getNextCoordinate(Collidable entity)
	{
		GridCoordinate coordinate = new GridCoordinate(entity.getCoordinates().getGridX(),entity.getCoordinates().getGridY());

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
		}
		else
		{
			return false;
		}
	}
	
	
	public Collidable doInteraction(Collidable entity)
	{
		return null;
	}
	
	public void draw()
	{
		for (Collidable[] row: collisionGrid)
		{
			for (Collidable c: row)
			{
				if (c!=null)
					c.draw();
			}
		}
	}
	
	private boolean isValidPosition(GridCoordinate coordinates)
	{
		if (coordinates==null)
		{
			return false;
		}
		return isValidPosition(coordinates.getGridX(),coordinates.getGridY());
	}
	
	private boolean isValidPosition(int xPosition, int yPosition)
	{
		return (xPosition >= 0 && xPosition < collisionGrid.length && yPosition >= 0 && yPosition < collisionGrid[0].length);
	}
}
