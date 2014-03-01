package ideasPackage;

import processing.core.*;

public class Camera
{
	private GridCoordinate location;
	private PlayerCharacter tracker;
	private PApplet parent;
	private float offsetX, offsetY;
	private CollisionGrid collisionGrid;

	public Camera(GridCoordinate location, PlayerCharacter tracker,
			PApplet parent)
	{
		this.location = location;
		this.tracker = tracker;
		this.parent = parent;
		collisionGrid = tracker.collisionGrid;

		int screenGridWidth = parent.width / Main.GRID_SIZE;
		int screenGridHeight = parent.height / Main.GRID_SIZE;

		location = new GridCoordinate(tracker.getCoordinates().getGridX()
				- screenGridWidth / 2, tracker.getCoordinates().getGridY()
				- screenGridHeight / 2);
	}

	public GridCoordinate getLocation()
	{
		return location;
	}

	public void update()
	{
		System.out.println(location.getGridX() +", " + location.getGridY());
		int screenGridWidth = parent.width / Main.GRID_SIZE;
		int screenGridHeight = parent.height / Main.GRID_SIZE;

		GridCoordinate nextCoordinate = null;
		if (tracker.isMoving())
		{
			nextCoordinate = CollisionGrid.getNextCoordinate(tracker);
		}

		if (nextCoordinate != null
				&& isNearEdge(nextCoordinate, 2, screenGridWidth,
						screenGridHeight))
		{
			switch (tracker.getDirection())
			{
			case Character.DIRECTION_UP:
				location.decrementY();
				break;
			case Character.DIRECTION_RIGHT:
				location.incrementX();
				break;
			case Character.DIRECTION_DOWN:
				location.incrementY();
				break;
			case Character.DIRECTION_LEFT:
				location.incrementX();
				break;
			}
		}

		if (location.getGridX() < 0)
		{
			location.setGridX(0);
		}

		if (location.getGridY() < 0)
		{
			location.setGridY(0);
		}

		if (location.getGridX() + screenGridWidth > collisionGrid
				.getGridWidth())
		{
			location.setGridX(collisionGrid.getGridWidth() - screenGridWidth);
		}

		if (location.getGridY() + screenGridHeight > collisionGrid
				.getGridHeight())
		{
			location.setGridY(collisionGrid.getGridHeight() - screenGridHeight);
		}
	}

	public boolean isNearEdge(GridCoordinate coordinate, int distance,
			int screenGridWidth, int screenGridHeight)
	{
		int x = coordinate.getGridX();
		int y = coordinate.getGridY();

		if (x - location.getGridX() <= distance
				|| y - location.getGridY() <= distance
				|| x - location.getGridX() >= screenGridWidth - distance
				|| y - location.getGridY() >= screenGridHeight - distance)
		{
			return true;

		}
		return false;
	}
}
