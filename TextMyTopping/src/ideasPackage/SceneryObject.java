package ideasPackage;

import processing.core.PApplet;
import processing.core.PImage;

public class SceneryObject implements Drawable
{

	private PImage[] sceneryImages;
	protected GridCoordinate coordinates;
	private float currentAnimationFrame;
	private int animationDuration;
	private int animationIndex;
	PApplet parent;
	
	protected SceneryObject(GridCoordinate coordinates, PImage image, SceneryGrid sceneryGrid, boolean addToGrid)
	{
		currentAnimationFrame = 0;
		animationIndex = 0;
		animationDuration = 20;
		sceneryImages = new PImage[1];
		sceneryImages[0] = image;
		this.coordinates = coordinates;
		if (addToGrid)
		{
			sceneryGrid.forceAddObject(coordinates, this);
		}
		parent = Main.getMainObject();
	}
	public SceneryObject(GridCoordinate coordinates, String imageName, int animationFrames, int animationDuration,
			SceneryGrid sceneryGrid, boolean addToGrid)
	{
		currentAnimationFrame = 0;
		animationIndex = 0;
		this.animationDuration = animationDuration;
		this.coordinates = coordinates;
		parent = Main.getMainObject();

		if (addToGrid)
			sceneryGrid.addSceneryObject(coordinates, this);
		else
		{
			sceneryGrid.addDuplicateObject(this);
		}

		sceneryImages = new PImage[animationFrames];
		for (int index = 0; index < animationFrames; index++)
		{
			sceneryImages[index] = parent.loadImage("data\\sprites\\scenery\\" + imageName + "\\" + index + ".png");
		}
	}

	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		parent.image(sceneryImages[animationIndex], coordinates.getGridX() * Main.GRID_SIZE + cameraOffsetX,
				coordinates.getGridY() * Main.GRID_SIZE + cameraOffsetY);
		updateAnimation();
		
	}

	public void updateAnimation()
	{
		currentAnimationFrame+=Main.getDeltaTime();
		if (currentAnimationFrame >= animationDuration)
		{
			currentAnimationFrame = 0;
			animationIndex++;
		}

		if (animationIndex == sceneryImages.length)
		{
			animationIndex = 0;
		}
	}

	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}

	@Override
	public void drawAtExactly(float x, float y, boolean updateAnimation)
	{
		parent.image(sceneryImages[animationIndex], x, y);
		if (updateAnimation)
			updateAnimation();

	}
	
	public PImage getImage()
	{
		return sceneryImages[animationIndex];
	}
}
