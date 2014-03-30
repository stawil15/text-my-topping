package ideasPackage;

public class GridCoordinate
{
	// This class is a container for an x and y position on a grid
	private int gridX, gridY;

	public GridCoordinate(int gridX, int gridY)
	{
		this.gridX = gridX;
		this.gridY = gridY;
	}

	// Get the gridX
	public int getGridX()
	{
		return gridX;
	}

	// set the gridX
	public void setGridX(int gridX)
	{
		this.gridX = gridX;
	}

	// get the gridY
	public int getGridY()
	{
		return gridY;
	}

	// get the gridY
	public void setGridY(int gridY)
	{
		this.gridY = gridY;
	}
	
	// Increment x by 1
	public void incrementX()
	{
		gridX++;
	}
	
	// Increment y by 1
	public void incrementY()
	{
		gridY++;
	}
	
	// Decrement x by 1
	public void decrementX()
	{
		gridX--;
	}
	
	// Decrement y by 1
	public void decrementY()
	{
		gridY--;
	}
}
