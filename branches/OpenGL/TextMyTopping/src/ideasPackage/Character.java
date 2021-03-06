package ideasPackage;


import java.util.ArrayList;
import processing.core.*;

public class Character implements Collidable
{
	private PImage[] charLeftImages;
	private PImage[] charRightImages;
	private PImage[] charUpImages;
	private PImage[] charDownImages;
	protected PApplet parent;
	private int currentDirection;
	protected int animationIndex = 0;
	private GridCoordinate coordinates;
	private int currentAnimationFrame;
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
			c.addElement(coordinates, this);
		else
			c.addDuplicateObject(this);
		
		collisionGrid = c;
		items = new ArrayList<>();
	}

	public void setMoveSpeed(float speed)
	{
		this.moveSpeed = speed;
	}

	public float getMoveSpeed()
	{
		return moveSpeed;
	}

	public void setAnimationDuration(int duration)
	{
		animationDuration = duration;
	}

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

	public void move(int direction)
	{
		if (!isMoving)
		{
			if (direction == currentDirection
					&& collisionGrid.moveElement(this))
			{
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
	}

	private void updateOffset()
	{
		if (!isMoving)
			return;

		switch (currentDirection)
		{
		case DIRECTION_UP:
			offsetY -= moveSpeed;
			if (offsetY <= 0)
			{
				isMoving = false;
				offsetY = 0;

			}
			break;
		case DIRECTION_RIGHT:
			offsetX += moveSpeed;
			if (offsetX >= 0)
			{
				isMoving = false;
				offsetX = 0;
				
			}
			break;
		case DIRECTION_DOWN:
			offsetY += moveSpeed;
			if (offsetY >= 0)
			{
				isMoving = false;
				offsetY = 0;
				
			}
			break;
		case DIRECTION_LEFT:
			offsetX -= moveSpeed;
			if (offsetX <= 0)
			{
				isMoving = false;
				offsetX = 0;
				
			}
			break;
		}

	}

	private void updateAnimation()
	{
		if (!isMoving)
		{
			animationIndex = 0;
			currentAnimationFrame = 0;
			return;
		}

		currentAnimationFrame++;
		if (currentAnimationFrame == animationDuration)
		{
			animationIndex++;
			animationIndex %= animationFrames;
			currentAnimationFrame = 0;
		}
	}

	public void giveItem(InventoryItem item)
	{
		items.add(item);
	}

	public void setDirection(int direction)
	{
		currentDirection = direction;
	}

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

	public void doInteract()
	{
		// Do nothing
	}

	public CollisionGrid getCollisionGrid()
	{
		return collisionGrid;
	}

	public int getDirection()
	{
		return currentDirection;
	}

	@Override
	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}

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

	private PImage getImageToDraw()
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
}
