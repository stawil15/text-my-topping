package ideasPackage;

import processing.core.PImage;

public class SceneryHole extends SceneryObject
{

	private PImage holeImage;
	private PImage moveableImage;

	public SceneryHole(GridCoordinate coordinates, PImage image, SceneryGrid sceneryGrid, boolean addToGrid)
	{
		super(coordinates, image, sceneryGrid, addToGrid);
		// TODO Auto-generated constructor stub
	}

	public void setHoleImage(PImage holeImage)
	{
		this.holeImage = holeImage;
	}

	public void setMoveableImage(PImage moveableImage)
	{
		this.moveableImage = moveableImage;
	}

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
