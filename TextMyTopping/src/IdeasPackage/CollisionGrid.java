package IdeasPackage;

public class CollisionGrid
{
	private Collidable collisionGrid[][];
	
	public CollisionGrid(int x_elements, int y_elements)
	{
		collisionGrid = new Collidable[x_elements][y_elements];
	}
	
	public void addElement(int xPosition, int yPosition, Collidable element)
	{
		if (collisionGrid.length == 0)
			return;
		
		if (xPosition >= 0 && xPosition < collisionGrid.length && yPosition >= 0 && yPosition < collisionGrid[0].length)
		{
			collisionGrid[xPosition][yPosition] = element;
		}
	}
	
	public Collidable removeElementAt(int xPosition, int yPosition)
	{
		if (collisionGrid.length == 0)
			return null;
		
		
		Collidable returnedElement = null;
		if (xPosition >= 0 && xPosition < collisionGrid.length && yPosition >= 0 && yPosition < collisionGrid[0].length)
		{
			returnedElement= collisionGrid[xPosition][yPosition];
			collisionGrid[xPosition][yPosition] = null;
		}
		
		return returnedElement;
	}
	
	private boolean canElementMove(Collidable e, int direction)
	{
		int elementX = e.getGridX();
		int elementY = e.getGridY();
		
		if (collisionGrid.length == 0)
			return false;
		
		switch (direction)
		{
		case Character.DIRECTION_UP:
			elementY--;
			break;
		case Character.DIRECTION_RIGHT:
			elementX++;
			break;
		case Character.DIRECTION_DOWN:
			elementY++;
			break;
		case Character.DIRECTION_LEFT:
			elementX--;
			break;
		}
		
		if (!(elementX >= 0 && elementX < collisionGrid.length && elementY >= 0 && elementY < collisionGrid[0].length))
		{
			return false;
		}
		
		if (collisionGrid[elementX][elementY] != null)
		{
			return false;
		}
			
		
		return true;
	}
	
	public boolean moveElement(Collidable e, int direction)
	{
		if (canElementMove(e, direction))
		{
			Collidable element = removeElementAt(e.getGridX(), e.getGridY());
			int elementX = element.getGridX();
			int elementY = element.getGridY();
			
			switch (direction)
			{
			case Character.DIRECTION_UP:
				elementY--;
				break;
			case Character.DIRECTION_RIGHT:
				elementX++;
				break;
			case Character.DIRECTION_DOWN:
				elementY++;
				break;
			case Character.DIRECTION_LEFT:
				elementX--;
				break;
			}
			addElement(elementX, elementY, element);
			return true;
		}
		else
		{
			return false;
		}
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
}
