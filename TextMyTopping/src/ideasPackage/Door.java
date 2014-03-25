package ideasPackage;

public class Door implements Collidable
{
	private String fromLevel, toLevel;
	private GridCoordinate coordinates;
	private int direction;
	private String variableName;
	private boolean requiredValue = false;
	private Dialog dialog;
	private boolean fadeTransition;
	public final static int FADE_TRANSITION = 5;
	
	public Door(GridCoordinate coordinates, CollisionGrid collisionGrid, String fromLevel, String toLevel, int direction, String variableName, boolean requiredValue, boolean fadeTransition, Dialog d)
	{
		this(coordinates, collisionGrid, fromLevel, toLevel, direction, fadeTransition);
		this.dialog = d;
		this.variableName = variableName;
		this.requiredValue  = requiredValue;
	}
	
	public Door(GridCoordinate coordinates, CollisionGrid collisionGrid, String fromLevel, String toLevel, int direction, boolean fadeTranisition)
	{
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
		this.coordinates = coordinates;
		collisionGrid.addElement(coordinates, this);
		this.direction = direction;
		this.fadeTransition = fadeTranisition;
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
			if (!fadeTransition)
			{
				LevelManager.setActiveLevel(toLevel, fromLevel, direction);
			}
			else
			{
				LevelManager.setActiveLevel(toLevel, fromLevel, FADE_TRANSITION);
			}
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
