package ideasPackage;

public class GridCoordinate
{
	private int gridX, gridY;

	public GridCoordinate(int gridX, int gridY)
	{
		this.gridX = gridX;
		this.gridY = gridY;
	}

	public int getGridX()
	{
		return gridX;
	}

	public void setGridX(int gridX)
	{
		this.gridX = gridX;
	}

	public int getGridY()
	{
		return gridY;
	}

	public void setGridY(int gridY)
	{
		this.gridY = gridY;
	}
	
	public void incrementX()
	{
		gridX++;
	}
	
	public void incrementY()
	{
		gridY++;
	}
	
	public void decrementX()
	{
		gridX--;
	}
	
	public void decrementY()
	{
		gridY--;
	}
}
