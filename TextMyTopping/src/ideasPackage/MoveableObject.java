package ideasPackage;

import processing.core.PImage;

public class MoveableObject extends Character
{

	public MoveableObject(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, "moveable", addToGrid);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doInteract()
	{
		if (canMove())
		{
			move((Main.getPlayer()).getDirection());
		}
	}

	
}
