package ideasPackage;

import processing.core.*;

public class Camera
{
	private GridCoordinate location;
	private PlayerCharacter tracker;
	private PApplet parent;
	private float offsetXLeft, offsetXRight, offsetYDown,offsetYUp;
	private CollisionGrid collisionGrid;
	private final static int BORDER = 2;
	public boolean movingLeft = false, movingRight = false, movingUp = false,
			movingDown = false;

	public Camera(GridCoordinate location, PlayerCharacter tracker,
			PApplet parent)
	{
		this.location = location;
		this.tracker = tracker;
		this.parent = parent;
		collisionGrid = tracker.getCollisionGrid();

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
		int screenGridWidth = parent.width / Main.GRID_SIZE;
		int screenGridHeight = parent.height / Main.GRID_SIZE;

		GridCoordinate nextCoordinate = null;
		if (tracker.isMoving())
		{
			nextCoordinate = CollisionGrid.getNextCoordinate(tracker);
		}

		if (tracker.isMoving && nextCoordinate != null)
		{
			if (isNearTopEdge(nextCoordinate, BORDER, screenGridHeight))
			{
				location.decrementY();
				movingUp = true;
				offsetYUp = Main.GRID_SIZE;
			}
			if (isNearBottomEdge(nextCoordinate, BORDER,
					screenGridHeight))
			{
				location.incrementY();
				movingDown = true;
				offsetYDown = -Main.GRID_SIZE;
			} 
			if (isNearLeftEdge(nextCoordinate, BORDER, screenGridHeight))
			{
				location.decrementX();
				movingLeft = true;
				offsetXLeft = Main.GRID_SIZE;
			} 
			if (isNearRightEdge(nextCoordinate, BORDER, screenGridWidth))
			{
				location.incrementX();
				movingRight = true;
				offsetXRight = -Main.GRID_SIZE;
			}
		}
		else
		{
			movingUp = false;
			movingDown = false;
			movingLeft = false;
			movingRight = false;
		}

		if (location.getGridX() < 0)
		{
			location.setGridX(0);
			movingLeft = false;
		}

		if (location.getGridY() < 0)
		{
			location.setGridY(0);
			movingUp = false;
		}

		if (location.getGridX() + screenGridWidth > collisionGrid
				.getGridWidth())
		{
			location.setGridX(collisionGrid.getGridWidth() - screenGridWidth);
			movingRight = false;
		}

		if (location.getGridY() + screenGridHeight > collisionGrid
				.getGridHeight())
		{
			location.setGridY(collisionGrid.getGridHeight() - screenGridHeight);
			movingDown = false;
		}

		if (movingUp)
		{
			updateOffset(Character.DIRECTION_UP);
		}
		else
		{
			offsetYUp = 0;
		}
		
		if (movingLeft)
		{
			updateOffset(Character.DIRECTION_LEFT);
		}
		else
		{
			offsetXLeft = 0;
		}
		
		if (movingRight)
		{
			updateOffset(Character.DIRECTION_RIGHT);
		}
		else
		{
			offsetXRight = 0;
		}
		
		if (movingDown)
		{
			updateOffset(Character.DIRECTION_DOWN);
		}
		else
		{
			offsetYDown = 0;
		}
	}

	public void updateOffset(int direction)
	{
		switch (direction)
		{
		case Character.DIRECTION_UP:
			offsetYUp -= tracker.getMoveSpeed();
			if (offsetYUp <= 0)
			{
				movingUp = false;
				offsetYUp = 0;
			}
			break;
		case Character.DIRECTION_RIGHT:
			offsetXRight += tracker.getMoveSpeed();
			if (offsetXRight >= 0)
			{
				movingRight = false;
				offsetXRight = 0;
			}
			break;
		case Character.DIRECTION_DOWN:
			offsetYDown += tracker.getMoveSpeed();
			if (offsetYDown >= 0)
			{
				movingDown = false;
				offsetYDown = 0;
			}
			break;
		case Character.DIRECTION_LEFT:
			offsetXLeft -= tracker.getMoveSpeed();
			if (offsetXLeft <= 0)
			{
				movingLeft = false;
				offsetXLeft = 0;
			}
			break;
		}
	}

	public boolean isNearLeftEdge(GridCoordinate coordinate, int distance,
			int screenGridWidth)
	{
		return (coordinate.getGridX() - location.getGridX() < distance);
	}

	public boolean isNearRightEdge(GridCoordinate coordinate, int distance,
			int screenGridWidth)
	{
		return (coordinate.getGridX() - location.getGridX() >= screenGridWidth
				- distance);
	}

	public boolean isNearBottomEdge(GridCoordinate coordinate, int distance,
			int screenGridHeight)
	{
		return (coordinate.getGridY() - location.getGridY() >= screenGridHeight
				- distance);
	}

	public boolean isNearTopEdge(GridCoordinate coordinate, int distance,
			int screenGridHeight)
	{
		return (coordinate.getGridY() - location.getGridY() < distance);
	}

	public float getCameraOffsetX()
	{
		return -(location.getGridX() * Main.GRID_SIZE + offsetXLeft + offsetXRight);
	}

	public float getCameraOffsetY()
	{
		return -(location.getGridY() * Main.GRID_SIZE + offsetYUp + offsetYDown);
	}
}
