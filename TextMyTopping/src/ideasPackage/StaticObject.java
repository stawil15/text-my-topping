package ideasPackage;

import processing.core.*;

public class StaticObject implements Collidable
{

	private GridCoordinate coordinates;
	private PImage images[];
	private PApplet parent;

	private int animationIndex;
	private int animationDuration;
	private int currentAnimationFrame;
	private Dialog dialog;

	public StaticObject(GridCoordinate coordinates, String imageName, CollisionGrid c, int animationFrames,
			int anationDuration, boolean addToGrid)
	{
		this(coordinates, c, addToGrid);
		this.animationDuration = anationDuration;
		images = new PImage[animationFrames];
		for (int index = 0; index < animationFrames; index++)
		{
			images[index] = parent.loadImage("\\data\\sprites\\scenery\\" + imageName + "\\" + index + ".png");
		}

	}

	public StaticObject(GridCoordinate coordinates, String imageName, CollisionGrid c, boolean addToGrid)
	{
		this(coordinates, c, addToGrid);
		images = new PImage[1];
		images[0] = parent.loadImage("\\data\\sprites\\scenery\\" + imageName + "\\" + 0 + ".png");
	}

	public StaticObject(GridCoordinate coordinates, CollisionGrid c, boolean addToGrid)
	{
		this.coordinates = coordinates;
		if (addToGrid)
			c.addElement(coordinates, this);
		else
			c.addDuplicateObject(this);
		parent = Main.getMainObject();
	}

	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		if (images != null)
		{
			parent.image(images[animationIndex], coordinates.getGridX() * Main.GRID_SIZE + cameraOffsetX,
					coordinates.getGridY() * Main.GRID_SIZE + cameraOffsetY);
			updateAnimation();
		}

	}

	private void updateAnimation()
	{
		currentAnimationFrame++;
		if (currentAnimationFrame == animationDuration)
		{
			animationIndex++;
			animationIndex %= images.length;
			currentAnimationFrame = 0;
		}
	}

	public int getDirection()
	{
		return 0;
	}
	
	public void setDialog(Dialog dialog)
	{
		this.dialog = dialog;
	}

	@Override
	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}

	public void doInteract(int interactionId)
	{
		if (dialog!=null && interactionId == PlayerCharacter.MAIN_INTERACTION)
		{
			GUISystem.showDialog(dialog);
		}
	}

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
}
