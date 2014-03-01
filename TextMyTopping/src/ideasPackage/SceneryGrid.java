package ideasPackage;


public class SceneryGrid
{
	public SceneryObject[][] sceneryGrid;
	private Camera camera;
	
	public SceneryGrid(int xEntities, int yEntities)
	{
		sceneryGrid = new SceneryObject[xEntities][yEntities];
	}
	
	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}
	
	public void addSceneryObject(SceneryObject entity)
	{
		if (isValidPosition(entity.getCoordinates()))
		{
			sceneryGrid[entity.getCoordinates().getGridX()][entity.getCoordinates().getGridY()] =  entity;
		}
	}
	
	public void addSceneryObject(SceneryObject entity, GridCoordinate coordinates)
	{
		
	}
	public void draw()
	{
		for  (SceneryObject row[] : sceneryGrid)
		{
			for (SceneryObject entity: row)
			{
				if (entity!=null && camera!=null)
				{
					entity.draw(camera.getCameraOffsetX(), camera.getCameraOffsetY());
				}
			}
		}
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
		return (xPosition >= 0 && xPosition < sceneryGrid.length
				&& yPosition >= 0 && yPosition < sceneryGrid[0].length);
	}
	
	public int getMinDrawX()
	{
		return 0;
	}
	
	public int getMaxDrawX()
	{
		return 0;
	}
	
	public int getMinDrawY()
	{
		return 0;
	}
	
	public int getMaxDrawY()
	{
		return 0;
	}
	
	public int min(int a, int b)
	{
		if (a<b)
		{
			return a;
		}
		else
		{
			return b;
		}
	}
	
	public int max(int a, int b)
	{
		if (a>b)
		{
			return a;
		}
		else
		{
			return b;
			
		}
	}
}
