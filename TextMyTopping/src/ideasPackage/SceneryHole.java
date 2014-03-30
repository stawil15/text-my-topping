package ideasPackage;

import processing.core.PImage;

public class SceneryHole extends SceneryObject
{

	// An object of this class is drawn after an object is pushed into a hole. 
	private PImage holeImage;
	private PImage moveableImage;

	public SceneryHole(GridCoordinate coordinates, PImage image, SceneryGrid sceneryGrid, boolean addToGrid)
	{
		super(coordinates, image, sceneryGrid, addToGrid);
	}

	// Set the image of the hole
	public void setHoleImage(PImage holeImage)
	{
		this.holeImage = holeImage;
	}

	// Set the image of the moveable object
	public void setMoveableImage(PImage moveableImage)
	{
		this.moveableImage = moveableImage;
	}

	// Draw the sceneryHole
	@Override
	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		super.draw(cameraOffsetX, cameraOffsetY);

		if (holeImage != null)
		{
			parent.image(holeImage, coordinates.getGridX() * Main.GRID_SIZE + cameraOffsetX, coordinates.getGridY() * Main.GRID_SIZE + cameraOffsetY);
		}

		if (moveableImage != null)
		{
			parent.image(moveableImage, coordinates.getGridX() * Main.GRID_SIZE + cameraOffsetX, coordinates.getGridY() * Main.GRID_SIZE + cameraOffsetY);
		}

	}

	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}

	@Override
	public void drawAtExactly(float x, float y, boolean updateAnimation)
	{
		super.drawAtExactly(x, y, updateAnimation);
		if (holeImage != null)
		{
			parent.image(holeImage, x, y);
		}

		if (moveableImage != null)
		{
			parent.image(moveableImage, x, y);
		}

	}

}
