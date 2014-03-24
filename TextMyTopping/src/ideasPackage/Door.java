package ideasPackage;

public class Door implements Collidable
{
	private String fromLevel, toLevel;
	private GridCoordinate coordinates;
	private CollisionGrid collisiongrid;
	private int direction;
	
	public Door(GridCoordinate coordinates, CollisionGrid collisionGrid, String fromLevel, String toLevel, int direction)
	{
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
		this.coordinates = coordinates;
		this.collisiongrid = collisionGrid;
		collisionGrid.addElement(coordinates, this);
		this.direction = direction;
	}

	@Override
	public void drawAtExactly(float x, float y, boolean updateAnimation)
	{
		// Doors are invisible
		
	}

	@Override
	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}

	@Override
	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		// Doors are invisible
		
	}

	@Override
	public int getDirection()
	{
		return direction;
	}

	@Override
	public void doInteract(int interactionId)
	{
		if (interactionId == 0)
		{
			LevelManager.setActiveLevel(toLevel, fromLevel);
		}
		
	}
	
	public String getFromLevel()
	{
		return fromLevel;
	}
	
	public String getToLevel()
	{
		return toLevel;
	}
}
