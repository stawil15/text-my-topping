package ideasPackage;

import processing.core.*;

/* 
 * The camera keeps track of the PlayerCharacter on the screen such that the camera is in the
 * top left corner and the player is always visible, near the center of the screen. 
 */
public class Camera
{
	private GridCoordinate location;
	private PlayerCharacter tracker;
	private PApplet parent;
	private float offsetXLeft, offsetXRight, offsetYDown,offsetYUp;
	private CollisionGrid collisionGrid;
	private final static int BORDER = 4;
	public boolean movingLeft = false, movingRight = false, movingUp = false,
			movingDown = false;

	public Camera(PlayerCharacter tracker)
	{
		this.tracker = tracker;
		parent = Main.getMainObject();

		centerCameraAroundTracker();
	}

	// Get the location of the camera
	public GridCoordinate getLocation()
	{
		return location;
	}

	// Update the camera to see if the player has moved
	public void update()
	{
		// Get the collisionGrid of the tracker
		collisionGrid = tracker.getCollisionGrid();
		int screenGridWidth = parent.width / Main.GRID_SIZE;
		int screenGridHeight = parent.height / Main.GRID_SIZE;

		// See if the tracker is moving and if it is, check which direction
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

		// If we are at the top of the grid, do not move up
		if (location.getGridX() < 0)
		{
			location.setGridX(0);
			movingLeft = false;
		}

		// If we are all the way left, do not move left
		if (location.getGridY() < 0)
		{
			location.setGridY(0);
			movingUp = false;
		}

		// If we are all the way right, do not move right
		if (location.getGridX() + screenGridWidth > collisionGrid
				.getGridWidth())
		{
			location.setGridX(collisionGrid.getGridWidth() - screenGridWidth);
			movingRight = false;
		}

		// If we are all the way down, do not move down
		if (location.getGridY() + screenGridHeight > collisionGrid
				.getGridHeight())
		{
			location.setGridY(collisionGrid.getGridHeight() - screenGridHeight);
			movingDown = false;
		}

		// Move the camera if we determined we should move
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

	// Updates the offset, or the cameras relative position to its location on the grid
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
	
	// Makes sure the player is visible when a map is loaded
	public void centerCameraAroundTracker()
	{
		int screenGridWidth = parent.width / Main.GRID_SIZE;
		int screenGridHeight = parent.height / Main.GRID_SIZE;
		location = new GridCoordinate(tracker.getCoordinates().getGridX()
				- screenGridWidth / 2, tracker.getCoordinates().getGridY()
				- screenGridHeight / 2);
	}

	// Determine if the player is near the left edge
	public boolean isNearLeftEdge(GridCoordinate coordinate, int distance,
			int screenGridWidth)
	{
		return (coordinate.getGridX() - location.getGridX() < distance);
	}

	// Determine if the player is near the right edge
	public boolean isNearRightEdge(GridCoordinate coordinate, int distance,
			int screenGridWidth)
	{
		return (coordinate.getGridX() - location.getGridX() >= screenGridWidth
				- distance);
	}

	// Determine if the player is near the bottom edge
	public boolean isNearBottomEdge(GridCoordinate coordinate, int distance,
			int screenGridHeight)
	{
		return (coordinate.getGridY() - location.getGridY() >= screenGridHeight
				- distance);
	}

	// Determine if the player is near the top edge
	public boolean isNearTopEdge(GridCoordinate coordinate, int distance,
			int screenGridHeight)
	{
		return (coordinate.getGridY() - location.getGridY() < distance);
	}

	// Gets the x offset relative to the point (0,0)
	public float getAbsoluteCameraOffsetX()
	{
		return -(location.getGridX() * Main.GRID_SIZE + offsetXLeft + offsetXRight);
	}

	// Gets the y offset relative to the point (0,0)
	public float getAbsoluteCameraOffsetY()
	{
		return -(location.getGridY() * Main.GRID_SIZE + offsetYUp + offsetYDown);
	}
}
