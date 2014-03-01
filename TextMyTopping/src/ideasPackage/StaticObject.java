package ideasPackage;

import processing.core.*;

public class StaticObject implements Collidable
{

	private GridCoordinate coordinates;
	private PImage images[];
	private PApplet parent;
	
	private int animationIndex;
	private int animationDuration;
	private int currentAnimationFrame;
	
	public StaticObject(GridCoordinate coordinates, String imageName, CollisionGrid c, int animationFrames, int anationDuration, PApplet parent)
	{
		this(coordinates, c, parent);
		this.animationDuration = anationDuration;
		images = new PImage[animationFrames];
		for (int index = 0; index < animationFrames; index++)
		{
			images[index] = parent.loadImage("\\data\\sprites\\static\\" + imageName + "\\" + index + ".png");
		}
		
	}
	public StaticObject(GridCoordinate coordinates, String imageName, CollisionGrid c,PApplet parent)
	{
		this(coordinates,c,parent);
		images = new PImage[1];
		images[0] = parent.loadImage("\\data\\sprites\\static\\" + imageName  + "\\" + 0 + ".png");
	}
	
	public StaticObject(GridCoordinate coordinates, CollisionGrid c, PApplet parent)
	{
		this.coordinates = coordinates;
		c.addElement(coordinates, this);
		this.parent = parent;
	}
	
	public void draw()
	{
		if (images!=null)
		{
			parent.image(images[animationIndex], coordinates.getGridX()*Main.GRID_SIZE, coordinates.getGridY()*Main.GRID_SIZE);
		}
		
		currentAnimationFrame++;
		if (currentAnimationFrame == animationDuration)
		{
			animationIndex++;
			animationIndex %= images.length;
			currentAnimationFrame = 0;
		}
	}
	
	public int getDirection()
	{
		return 0;
	}

	@Override
	public GridCoordinate getCoordinates()
	{
		return coordinates;
	}
	
	public void doInteract()
	{
		// Static object does not currently have an interaction
	}
}
