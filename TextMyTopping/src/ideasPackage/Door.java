package ideasPackage;

public class Door implements Collidable
{
	private String fromLevel, toLevel;
	private GridCoordinate coordinates;
	private int direction;
	private String variableName;
	private boolean requiredValue = false;
	private Dialog dialog;
	
	public Door(GridCoordinate coordinates, CollisionGrid collisionGrid, String fromLevel, String toLevel, int direction, String variableName, boolean requiredValue, Dialog d)
	{
		this(coordinates, collisionGrid, fromLevel, toLevel, direction);
		this.dialog = d;
		this.variableName = variableName;
		this.requiredValue  = requiredValue;
	}
	
	public Door(GridCoordinate coordinates, CollisionGrid collisionGrid, String fromLevel, String toLevel, int direction)
	{
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
		this.coordinates = coordinates;
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
		if ((interactionId == PlayerCharacter.MAIN_INTERACTION || interactionId == PlayerCharacter.BUMP_INTERACTION) && GlobalBooleanManager.getValue(variableName) == requiredValue)
		{
			LevelManager.setActiveLevel(toLevel, fromLevel);
		}
		else if (GlobalBooleanManager.getValue(variableName) != requiredValue && dialog != null && interactionId == PlayerCharacter.MAIN_INTERACTION || interactionId == PlayerCharacter.BUMP_INTERACTION)
		{
			GUISystem.showDialog(dialog);
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
