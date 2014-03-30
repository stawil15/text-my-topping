package ideasPackage;

public class Door implements Collidable
{
	// This class links levels to other levels
	private String fromLevel, toLevel;
	private GridCoordinate coordinates;
	private int direction;
	private String variableName;
	private boolean requiredValue = false;
	private Dialog dialog;
	private boolean fadeTransition;
	public final static int FADE_TRANSITION = 5;
	
	// Create the door with a required variable to be in a certain state. The dialog is shown when the 
	// state is not the same as the requriedValue.
	public Door(GridCoordinate coordinates, CollisionGrid collisionGrid, String fromLevel, String toLevel, int direction, String variableName, boolean requiredValue, boolean fadeTransition, Dialog d)
	{
		this(coordinates, collisionGrid, fromLevel, toLevel, direction, fadeTransition);
		this.dialog = d;
		this.variableName = variableName;
		this.requiredValue  = requiredValue;
	}
	
	// Creates a door that always works
	public Door(GridCoordinate coordinates, CollisionGrid collisionGrid, String fromLevel, String toLevel, int direction, boolean fadeTranisition)
	{
		this.fromLevel = fromLevel;
		this.toLevel = toLevel;
		this.coordinates = coordinates;
		collisionGrid.addEntity(coordinates, this);
		this.direction = direction;
		this.fadeTransition = fadeTranisition;
	}

	// Here for Collidable implementation
	@Override
	public void drawAtExactly(float x, float y, boolean updateAnimation)
	{
		// Doors are invisible
		
	}

	// Return the coordinates of the door
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

	// Returns the direction that the player is to be placed when they come through the door
	@Override
	public int getDirection()
	{
		return direction;
	}

	// Does the interaction. If the requiredValue is equal to the value in the boolean manager, then the door can be accessed. 
	// Otherwise, the dialog is shown why the player is not allowed through the door.
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
	
	// Get the fromLevel
	public String getFromLevel()
	{
		return fromLevel;
	}

	// Get the toLevel
	public String getToLevel()
	{
		return toLevel;
	}
}
