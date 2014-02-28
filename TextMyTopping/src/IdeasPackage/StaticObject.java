package IdeasPackage;

import processing.core.*;

public class StaticObject implements Collidable
{

	private int gridX, gridY;
	private PImage image;
	private PApplet parent;
	
	public StaticObject(int gridX, int gridY, String imageName, CollisionGrid c,PApplet parent)
	{
		this.gridX = gridX;
		this.gridY = gridY;
		c.addElement(gridX, gridY, this);
		image = parent.loadImage("\\data\\sprites\\static\\" + imageName + ".png");
		this.parent = parent;
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
		if (image!=null)
		{
			parent.image(image, gridX*MovementIdea.GRID_SIZE, gridY*MovementIdea.GRID_SIZE);
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
