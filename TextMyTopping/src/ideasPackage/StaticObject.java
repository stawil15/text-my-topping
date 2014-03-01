package ideasPackage;

import processing.core.*;

public class StaticObject implements Collidable
{

	private int gridX, gridY;
	private PImage images[];
	private PApplet parent;
	
	private int animationIndex;
	private int animationDuration;
	private int currentAnimationFrame;
	
	public StaticObject(int gridX, int gridY, String imageName, CollisionGrid c, int animationFrames, int anationDuration, PApplet parent)
	{
		this(gridX, gridY, c, parent);
		this.animationDuration = anationDuration;
		images = new PImage[animationFrames];
		for (int index = 0; index < animationFrames; index++)
		{
			images[index] = parent.loadImage("\\data\\sprites\\static\\" + imageName + index + ".png");
		}
		
	}
	public StaticObject(int gridX, int gridY, String imageName, CollisionGrid c,PApplet parent)
	{
		this(gridX,gridY,c,parent);
		images = new PImage[1];
		images[0] = parent.loadImage("\\data\\sprites\\static\\" + imageName + ".png");
	}
	
	public StaticObject(int gridX, int gridY, CollisionGrid c, PApplet parent)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		c.addElement(gridX, gridY, this);
		this.parent = parent;
	}
	
	public void draw()
	{
		if (images!=null)
		{
			parent.image(images[animationIndex], gridX*MovementIdea.GRID_SIZE, gridY*MovementIdea.GRID_SIZE);
		}
		
		currentAnimationFrame++;
		if (currentAnimationFrame == animationDuration)
		{
			animationIndex++;
			animationIndex %= images.length;
			currentAnimationFrame = 0;
		}
		
		
	}

	@Override
	public int getGridX()
	{
		return gridX;
	}

	@Override
	public int getGridY()
	{
		return gridY;
	}
}
