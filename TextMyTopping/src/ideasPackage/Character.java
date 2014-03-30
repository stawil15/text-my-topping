package ideasPackage;


import java.util.ArrayList;

import processing.core.*;

public class Character implements Collidable
{
	/*
	 * This class creates a generic character that can move
	 */
	
	private PImage[] charLeftImages;
	private PImage[] charRightImages;
	private PImage[] charUpImages;
	private PImage[] charDownImages;
	protected PApplet parent;
	private int currentDirection;
	protected int animationIndex = 0;
	protected GridCoordinate coordinates;
	private float currentAnimationFrame;
	private int animationDuration = 3;
	private float moveSpeed = 4f;
	protected float offsetX = 0;
	protected float offsetY = 0;
	protected boolean isMoving = false;
	private int animationFrames;
	public final static int DIRECTION_UP = 0;
	public final static int DIRECTION_RIGHT = 1;
	public final static int DIRECTION_DOWN = 2;
	public final static int DIRECTION_LEFT = 3;

	private ArrayList<InventoryItem> items;
	protected CollisionGrid collisionGrid;

	// This constructor is used for characters with one image.
	protected Character(GridCoordinate coordinates, int initialDirection,
			int animationFrames, String imageName, CollisionGrid c, String folderName, boolean addToGrid)
	{
		parent = Main.getMainObject();
		charRightImages = new PImage[animationFrames];
		charLeftImages = new PImage[animationFrames];
		charUpImages = new PImage[animationFrames];
		charDownImages = new PImage[animationFrames];

		for (int index = 0; index < charRightImages.length; index++)
		{
			charRightImages[index] = parent
					.loadImage("data\\sprites\\" + folderName +  "\\" + imageName
							+ "\\" + index + ".png");
		}

		for (int index = 0; index < charRightImages.length; index++)
		{
			charUpImages[index] = charRightImages[index];
		}

		for (int index = 0; index < charRightImages.length; index++)
		{
			charDownImages[index] = charRightImages[index];
		}

		for (int index = 0; index < charLeftImages.length; index++)
		{
			charLeftImages[index] = charRightImages[index];
		}

		this.coordinates = coordinates;
		this.currentDirection = initialDirection;
		this.animationFrames = animationFrames;
		
		if (addToGrid)
			c.addEntity(coordinates, this);
		else
			c.addDuplicateObject(this);
		
		collisionGrid = c;
		items = new ArrayList<>();
	}
	
	// Regular constructor
	public Character(GridCoordinate coordinates, int initialDirection,
			int animationFrames, String imageName, CollisionGrid c, boolean addToGrid)
	{
		parent = Main.getMainObject();
		charRightImages = new PImage[animationFrames];
		charLeftImages = new PImage[animationFrames];
		charUpImages = new PImage[animationFrames];
		charDownImages = new PImage[animationFrames];

		for (int index = 0; index < charRightImages.length; index++)
		{
			charRightImages[index] = parent
					.loadImage("data\\sprites\\character\\" + imageName
							+ "\\right" + index + ".png");
		}

		for (int index = 0; index < charRightImages.length; index++)
		{
			charUpImages[index] = parent.loadImage("data\\sprites\\character\\"
					+ imageName + "\\up" + index + ".png");
		}

		for (int index = 0; index < charRightImages.length; index++)
		{
			charDownImages[index] = parent
					.loadImage("data\\sprites\\character\\" + imageName
							+ "\\down" + index + ".png");
		}

		for (int index = 0; index < charLeftImages.length; index++)
		{
			charLeftImages[index] = parent
					.loadImage("data\\sprites\\character\\" + imageName
							+ "\\left" + index + ".png");
		}

		this.coordinates = coordinates;
		this.currentDirection = initialDirection;
		this.animationFrames = animationFrames;
		
		if (addToGrid)
			c.addEntity(coordinates, this);
		else
			c.addDuplicateObject(this);
		
		collisionGrid = c;
		items = new ArrayList<>();
	}
	
	// Gets the moves speed of the character
	public float getMoveSpeed()
	{
		return moveSpeed*Main.getTimeMultiplier();
	}

	// Set how long the duration of one frame should be animated
	public void setAnimationDuration(int duration)
	{
		animationDuration = duration;
	}

	// Draw the character
	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		PImage imageTodraw = getImageToDraw();

		updateAnimation();
		updateOffset();

		if (imageTodraw != null)
			parent.image(imageTodraw, coordinates.getGridX() * Main.GRID_SIZE
					+ offsetX + cameraOffsetX, coordinates.getGridY()
					* Main.GRID_SIZE + offsetY + cameraOffsetY);
	}

	// Move the character in a direction. Returns whether the movement
	// was successful or not. 
	public boolean move(int direction)
	{
		boolean didMove = false;
		if (!isMoving)
		{
			if (direction == currentDirection
					&& collisionGrid.moveElement(this))
			{
				didMove = true;
				currentDirection = direction;
				isMoving = true;
				switch (currentDirection)
				{
				case DIRECTION_UP:
					coordinates.setGridY(coordinates.getGridY() - 1);
					offsetY = Main.GRID_SIZE;
					break;
				case DIRECTION_RIGHT:
					coordinates.setGridX(coordinates.getGridX() + 1);
					offsetX = -Main.GRID_SIZE;
					break;
				case DIRECTION_DOWN: 
					coordinates.setGridY(coordinates.getGridY() + 1);
					offsetY = -Main.GRID_SIZE;
					break;
				case DIRECTION_LEFT:
					coordinates.setGridX(coordinates.getGridX() - 1);
					offsetX = Main.GRID_SIZE;
					break;
				}
			} else
			{
				currentDirection = direction;
			}
		}
		return didMove;
	}

	// Update the offset of the character, or the distance relative to their
	// position on the grid in pixels. 
	private void updateOffset()
	{
		if (!isMoving)
			return;

		switch (currentDirection)
		{
		case DIRECTION_UP:
			offsetY -= moveSpeed*Main.getTimeMultiplier();
			if (offsetY <= 0)
			{
				isMoving = false;
				offsetY = 0;
				finishedMoving();
			}
			break;
		case DIRECTION_RIGHT:
			offsetX += moveSpeed*Main.getTimeMultiplier();
			if (offsetX >= 0)
			{
				isMoving = false;
				offsetX = 0;
				finishedMoving();
			}
			break;
		case DIRECTION_DOWN:
			offsetY += moveSpeed*Main.getTimeMultiplier();
			if (offsetY >= 0)
			{
				isMoving = false;
				offsetY = 0;
				finishedMoving();
			}
			break;
		case DIRECTION_LEFT:
			offsetX -= moveSpeed*Main.getTimeMultiplier();
			if (offsetX <= 0)
			{
				isMoving = false;
				offsetX = 0;
				finishedMoving();
			}
			break;
		}

	}

	// Update the animation of the character
	private void updateAnimation()
	{
		if (!isMoving)
		{
			animationIndex = 0;
			currentAnimationFrame = 0;
			return;
		}

		currentAnimationFrame+=Main.getTimeMultiplier();
		if (currentAnimationFrame >= animationDuration)
		{
			animationIndex++;
			animationIndex %= animationFrames;
			currentAnimationFrame = 0;
		}
	}

	// Unused
	public void giveItem(InventoryItem item)
	{
		items.add(item);
	}

	// Sets the direction of the character
	public void setDirection(int direction)
	{
		currentDirection = direction;
	}

	// Unused
	public InventoryItem takeAwayItem(InventoryItem item)
	{
		if (!items.contains(item))
		{
			return null;
		} else
		{
			InventoryItem itemToReturn = items.get(items.indexOf(item));
			items.remove(item);
			return itemToReturn;
		}
	}

	// Interaction is the character. 
	public void doInteract(int interactionId)
	{
		// Do nothing
	}

	// get the collision grid
	public CollisionGrid getCollisionGrid()
	{
		return collisionGrid;
	}

	// Get the direction of the character
	public int getDirection()
	{
		return currentDirection;
	}

	//Get the coordinates of the character
	@Override
	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}

	// Get the opposite direction of character
	public int getOppositeDirection()
	{
		switch (currentDirection)
		{
		case DIRECTION_UP:
			return DIRECTION_DOWN;
		case DIRECTION_RIGHT:
			return DIRECTION_LEFT;
		case DIRECTION_DOWN:
			return DIRECTION_UP;
		case DIRECTION_LEFT:
			return DIRECTION_RIGHT;
		}
		return -1;
	}

	// Draw the character at an exact position on the screen
	@Override
	public void drawAtExactly(float x, float y, boolean updateAnimation)
	{
		PImage imageTodraw = getImageToDraw();

		if (imageTodraw != null)
		{
			parent.image(imageTodraw, x, y);
			if (updateAnimation)
				updateAnimation();
		}

	}

	// Get the image that should be drawn
	protected PImage getImageToDraw()
	{
		PImage imageTodraw = null;
		switch (currentDirection)
		{
		case DIRECTION_LEFT:
			imageTodraw = charLeftImages[animationIndex];
			break;
		case DIRECTION_RIGHT:
			imageTodraw = charRightImages[animationIndex];
			break;
		case DIRECTION_UP:
			imageTodraw = charUpImages[animationIndex];
			break;
		case DIRECTION_DOWN:
			imageTodraw = charDownImages[animationIndex];
			break;
		}
		return imageTodraw;
	}
	
	// Return whether the character should be allowed to move or not
	protected boolean canMove()
	{
		return GUISystem.allowMovement();
	}
	
	// Called when movement is finished.
	public void finishedMoving()
	{
		// Can be overridden in child classes, (ex: MoveableObject)
	}
}
