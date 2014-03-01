package ideasPackage;

import processing.core.PApplet;
import processing.core.PImage;

public class SceneryObject
{

	private PImage[] sceneryImages;
	private GridCoordinate coordinates;
	private int currentAnimationFrame;
	private int animationDuration;
	private int animationIndex;
	PApplet parent;

	public SceneryObject(GridCoordinate coordinates, String imageName,
			int animationFrames, int animationDuration,
			SceneryGrid sceneryGrid, PApplet parent)
	{
		currentAnimationFrame = 0;
		animationIndex = 0;
		this.animationDuration = animationDuration;
		this.coordinates = coordinates;
		this.parent = parent;
		sceneryGrid.addSceneryObject(this);

		sceneryImages = new PImage[animationFrames];
		for (int index = 0; index < animationFrames; index++)
		{
			sceneryImages[index] = parent.loadImage("data\\sprites\\scenery\\"
					+ imageName + "\\" + index + ".png");
		}
	}

	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		parent.image(sceneryImages[animationIndex], coordinates.getGridX()
				* Main.GRID_SIZE + cameraOffsetX, coordinates.getGridY()
				* Main.GRID_SIZE + cameraOffsetY);
		updateAnimation();
	}

	public void updateAnimation()
	{
		currentAnimationFrame++;
		if (currentAnimationFrame == animationDuration)
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
}
