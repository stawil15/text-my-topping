package ideasPackage;

import java.util.ArrayList;

import processing.core.*;

public class Character implements Collidable
{
	private PImage[] charLeftImages;
	private PImage[] charRightImages;
	private PImage[] charUpImages;
	private PImage[] charDownImages;
	private PApplet parent;
	private int currentDirection;
	private int animationIndex = 0;
	private GridCoordinate coordinates;
	private int currentAnimationFrame;
	private int animationDuration = 3;
	private float moveSpeed = 2.2f;
	private float offsetX = 0;
	private float offsetY = 0;
	protected boolean isMoving = false;
	private int animationFrames;

	public final static int DIRECTION_UP = 0;
	public final static int DIRECTION_RIGHT = 1;
	public final static int DIRECTION_DOWN = 2;
	public final static int DIRECTION_LEFT = 3;

	private ArrayList<InventoryItem> items;
	protected CollisionGrid collisionGrid;

	public Character(GridCoordinate coordinates, int initialDirection,
			int animationFrames, String imageName, CollisionGrid c, PApplet parent)
	{
		charRightImages = new PImage[animationFrames];
		charLeftImages = new PImage[animationFrames];
		charUpImages = new PImage[animationFrames];
		charDownImages = new PImage[animationFrames];

		for (int index = 0; index < charRightImages.length; index++)
		{
			charRightImages[index] = parent
					.loadImage("data\\sprites\\character\\" + imageName + "\\right"
							+ index + ".png");
		}

		for (int index = 0; index < charRightImages.length; index++)
		{
			charUpImages[index] = parent.loadImage("data\\sprites\\character\\"
					+ imageName + "\\up" + index + ".png");
		}

		for (int index = 0; index < charRightImages.length; index++)
		{
			charDownImages[index] = parent
					.loadImage("data\\sprites\\character\\" + imageName + "\\down"
							+ index + ".png");
		}

		for (int index = 0; index < charLeftImages.length; index++)
		{
			charLeftImages[index] = mirrorHorizontal(charRightImages[index]);
		}

		this.coordinates = coordinates;
		this.currentDirection = initialDirection;
		this.parent = parent;
		this.animationFrames = animationFrames;
		c.addElement(coordinates, this);
		collisionGrid = c;
		items = new ArrayList<>();
	}

	public void setMoveSpeed(float speed)
	{
		this.moveSpeed = speed;
	}

	public void setAnimationDuration(int duration)
	{
		animationDuration = duration;
	}

	public static PImage mirrorHorizontal(PImage imageToMirror)
	{
		if (imageToMirror == null)
		{
			return null;
		}

		imageToMirror.loadPixels();
		PImage mirroredImage = new PImage(imageToMirror.width,
				imageToMirror.height);
		for (int x = 0; x < imageToMirror.width; x++)
		{
			for (int y = 0; y < imageToMirror.height; y++)
			{
				mirroredImage.set(imageToMirror.width - 1 - x, y,
						imageToMirror.get(x, y));
			}
		}
		mirroredImage.updatePixels();
		return mirroredImage;
	}

	public void draw()
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

		updateOffset();
		updateAnimation();
		if (imageTodraw != null)
			parent.image(imageTodraw, coordinates.getGridX() * Main.GRID_SIZE + offsetX,
					coordinates.getGridY() * Main.GRID_SIZE + offsetY);
	}

	public void move(int direction)
	{
		if (!isMoving)
		{
			if (direction == currentDirection && collisionGrid.moveElement(this))
			{
				currentDirection = direction;
				isMoving = true;
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
			if (offsetY <= -Main.GRID_SIZE)
			{
				isMoving = false;
				offsetY = 0;
				coordinates.setGridY(coordinates.getGridY()-1);
			}
			break;
		case DIRECTION_RIGHT:
			offsetX += moveSpeed;
			if (offsetX >= Main.GRID_SIZE)
			{
				isMoving = false;
				offsetX = 0;
				coordinates.setGridX(coordinates.getGridX()+1);
			}
			break;
		case DIRECTION_DOWN:
			offsetY += moveSpeed;
			if (offsetY >= Main.GRID_SIZE)
			{
				isMoving = false;
				offsetY = 0;
				coordinates.setGridY(coordinates.getGridY()+1);
			}
			break;
		case DIRECTION_LEFT:
			offsetX -= moveSpeed;
			if (offsetX <= -Main.GRID_SIZE)
			{
				isMoving = false;
				offsetX = 0;
				coordinates.setGridX(coordinates.getGridX()-1);
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
			return  DIRECTION_RIGHT;
		}
		return -1;
	}
}
