package IdeasPackage;

import processing.core.*;

public class Character
{
	private PImage[] charLeftImages;
	private PImage[] charRightImages;
	private PImage[] charUpImages;
	private PImage[] charDownImages;
	private PApplet parent;
	private int currentDirection;
	private int animationIndex = 0;
	private int gridX;
	private int gridY;
	private int currentAnimationFrame;
	private int animationDuration = 3;
	private float moveSpeed = 2.2f;
	private float offsetX = 0;
	private float offsetY = 0;
	private boolean isMoving = false;
	private int animationFrames;

	public final static int DIRECTION_UP = 0;
	public final static int DIRECTION_RIGHT = 1;
	public final static int DIRECTION_DOWN = 2;
	public final static int DIRECTION_LEFT = 3;

	public Character(int gridX, int gridY, int initialDirection,
			int animationFrames, String name, PApplet parent)
	{
		charRightImages = new PImage[animationFrames];
		charLeftImages = new PImage[animationFrames];
		charUpImages = new PImage[animationFrames];
		charDownImages = new PImage[animationFrames];
		
		
		for (int index = 0; index < charRightImages.length; index++)
		{
			charRightImages[index] = parent.loadImage("sprites\\character\\"
					+ name + "\\right" + index + ".png");
		}
		
		for (int index = 0; index < charRightImages.length; index++)
		{
			charUpImages[index] = parent.loadImage("sprites\\character\\"
					+ name + "\\up" + index + ".png");
		}
		
		for (int index = 0; index < charRightImages.length; index++)
		{
			charDownImages[index] = parent.loadImage("sprites\\character\\"
					+ name + "\\down" + index + ".png");
		}

		for (int index = 0; index < charLeftImages.length; index++)
		{
			charLeftImages[index] = mirrorHorizontal(charRightImages[index]);
		}

		this.gridX = gridX;
		this.gridY = gridY;
		this.currentDirection = initialDirection;
		this.parent = parent;
		this.animationFrames = animationFrames;
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
			parent.image(imageTodraw, gridX * MovementIdea.GRID_SIZE + offsetX,
					gridY * MovementIdea.GRID_SIZE + offsetY);
	}

	public void move(int direction)
	{
		if (!isMoving)
		{
			currentDirection = direction;
			isMoving = true;
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
			if (offsetY <= -MovementIdea.GRID_SIZE)
			{
				isMoving = false;
				offsetY = 0;
				gridY--;
			}
			break;
		case DIRECTION_RIGHT:
			offsetX += moveSpeed;
			if (offsetX >= MovementIdea.GRID_SIZE)
			{
				isMoving = false;
				offsetX = 0;
				gridX++;
			}
			break;
		case DIRECTION_DOWN:
			offsetY += moveSpeed;
			if (offsetY >= MovementIdea.GRID_SIZE)
			{
				isMoving = false;
				offsetY = 0;
				gridY++;
			}
			break;
		case DIRECTION_LEFT:
			offsetX -= moveSpeed;
			if (offsetX <= -MovementIdea.GRID_SIZE)
			{
				isMoving = false;
				offsetX = 0;
				gridX--;
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
			currentAnimationFrame=0;
		}
	}
}
