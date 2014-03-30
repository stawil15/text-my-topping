package ideasPackage;

import processing.core.*;

public class StaticObject implements Collidable
{
	// Static objects are objects that cannot be moved, but they can be collided with
	private GridCoordinate coordinates;
	private PImage images[];
	private PApplet parent;

	private int animationIndex;
	private int animationDuration;
	private float currentAnimationFrame;
	private Dialog dialog;
	private String imageName;
	
	private int interactionDurationTimer = 0;
	private int previousAnimationDuration = 0;
	private final static int SPECIAL_ANIMATION_DURATION = 60;

	// A different way of creating a static object with more parameters
	public StaticObject(GridCoordinate coordinates, String imageName, CollisionGrid c, int animationFrames,
			int anamationDuration, boolean addToGrid)
	{
		this(coordinates, c, addToGrid);
		this.animationDuration = anamationDuration;
		images = new PImage[animationFrames];
		this.imageName = imageName;
		for (int index = 0; index < animationFrames; index++)
		{
			images[index] = parent.loadImage("\\data\\sprites\\scenery\\" + imageName + "\\" + index + ".png");
		}
		previousAnimationDuration = anamationDuration;

	}
	
	// A different way of creating a static object with more parameters
	public StaticObject(GridCoordinate coordinates, String imageName, CollisionGrid c, boolean addToGrid)
	{
		this(coordinates, c, addToGrid);
		images = new PImage[1];
		images[0] = parent.loadImage("\\data\\sprites\\scenery\\" + imageName + "\\" + 0 + ".png");
	}

	// Creats an invisible static object
	public StaticObject(GridCoordinate coordinates, CollisionGrid c, boolean addToGrid)
	{
		this.coordinates = coordinates;
		if (addToGrid)
			c.addEntity(coordinates, this);
		else
			c.addDuplicateObject(this);
		parent = Main.getMainObject();
	}

	// Draws the object
	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		if (images != null)
		{
			parent.image(images[animationIndex], coordinates.getGridX() * Main.GRID_SIZE + cameraOffsetX,
					coordinates.getGridY() * Main.GRID_SIZE + cameraOffsetY);
			updateAnimation();
		}

	}

	// Updates the animation
	private void updateAnimation()
	{
		currentAnimationFrame+=Main.getTimeMultiplier();
		if (currentAnimationFrame >= animationDuration)
		{
			animationIndex++;
			animationIndex %= images.length;
			currentAnimationFrame = 0;
		}
		
		if (interactionDurationTimer > 0)
		{
			interactionDurationTimer--;
			if (interactionDurationTimer == 0)
			{
				this.animationDuration = previousAnimationDuration;
			}
		}
	}

	// Static objects have no direction
	public int getDirection()
	{
		return 0;
	}
	
	// Sets the dialog of the static object
	public void setDialog(Dialog dialog)
	{
		this.dialog = dialog;
	}

	// Gets the coordinates
	@Override
	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}

	public void doInteract(int interactionId)
	{
		if (dialog!=null && interactionId == PlayerCharacter.MAIN_INTERACTION)
		{
			dialog.showDialog();
		}
		
		// An easter egg if. If the user has the axe of the wind, they can make the animation period
		// of trees and cactus faster to appear more windy. 
		if (imageName != null && (imageName.contains("tree") || imageName.contains("cactus")))
		{
			if (GlobalBooleanManager.getValue("hasWindAxe"))
			{
				this.animationDuration--;
				interactionDurationTimer += SPECIAL_ANIMATION_DURATION;
				if (interactionDurationTimer > SPECIAL_ANIMATION_DURATION*10)
				{
					interactionDurationTimer = SPECIAL_ANIMATION_DURATION*10;
				}
			}
		}
	}

	// Draws the static object at an exact location
	@Override
	public void drawAtExactly(float x, float y, boolean updateAnimation)
	{
		if (images != null)
		{
			parent.image(images[animationIndex], x, y);
			if (updateAnimation)
				updateAnimation();
		}

	}
	
	// Gets the current image
	public PImage getImage()
	{
		return images[animationIndex];
	}
}
