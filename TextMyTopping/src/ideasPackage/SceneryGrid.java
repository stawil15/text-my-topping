package ideasPackage;

public class SceneryGrid
{
	public SceneryObject[][] sceneryGrid;
	private Camera camera;
	private GridHelper helper;

	public SceneryGrid(int xEntities, int yEntities)
	{
		sceneryGrid = new SceneryObject[xEntities][yEntities];
		helper = new GridHelper(sceneryGrid, camera);
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
		helper.setCamera(camera);
	}

	public void addSceneryObject(GridCoordinate coordinates, SceneryObject entity)
	{
		if (helper.isValidPosition(coordinates))
		{
			sceneryGrid[coordinates.getGridX()][coordinates.getGridY()] = entity;
		}
	}
	
	public void forceAddObject(GridCoordinate coordinates, SceneryObject entity)
	{
		sceneryGrid[coordinates.getGridX()][coordinates.getGridY()] = entity;
	}

	public void draw()
	{
		helper.draw(sceneryGrid);
	}

	public void addDuplicateObject(Drawable entity)
	{
		helper.addDuplicate(entity);
	}
	
	public SceneryObject getObjectAt(GridCoordinate coordinate)
	{
		return sceneryGrid[coordinate.getGridX()][coordinate.getGridY()];
	}

}
