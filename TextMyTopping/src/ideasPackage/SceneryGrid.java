package ideasPackage;

public class SceneryGrid
{
	// This class creates a scenery grid that hides behind all the objects on the collisionGrid
	public SceneryObject[][] sceneryGrid;
	private Camera camera;
	private GridHelper helper;

	public SceneryGrid(int xEntities, int yEntities)
	{
		sceneryGrid = new SceneryObject[xEntities][yEntities];
		helper = new GridHelper(sceneryGrid, camera);
	}

	// Set the camera of the grid
	public void setCamera(Camera camera)
	{
		this.camera = camera;
		helper.setCamera(camera);
	}

	// Add an object to the grid
	public void addSceneryObject(GridCoordinate coordinates, SceneryObject entity)
	{
		if (helper.isValidPosition(coordinates))
		{
			sceneryGrid[coordinates.getGridX()][coordinates.getGridY()] = entity;
		}
	}
	
	// Force an object to be added even if an object already exists
	public void forceAddObject(GridCoordinate coordinates, SceneryObject entity)
	{
		sceneryGrid[coordinates.getGridX()][coordinates.getGridY()] = entity;
	}

	// Draw the grid
	public void draw()
	{
		helper.draw(sceneryGrid);
	}

	// Add a duplicate object to save on memory
	public void addDuplicateObject(Drawable entity)
	{
		helper.addDuplicate(entity);
	}
	
	// get the object at a specified coordinate
	public SceneryObject getObjectAt(GridCoordinate coordinate)
	{
		return sceneryGrid[coordinate.getGridX()][coordinate.getGridY()];
	}

}
