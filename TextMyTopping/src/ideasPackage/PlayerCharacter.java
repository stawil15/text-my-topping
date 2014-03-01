package ideasPackage;

import processing.core.PApplet;

public class PlayerCharacter extends Character
{

	public PlayerCharacter(GridCoordinate coordinates, int initialDirection,
			int animationFrames, String imageName, CollisionGrid c, PApplet parent)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, parent);
	}
	
	@Override
	public void doInteract()
	{
		if (!isMoving)
		{
			collisionGrid.doInteraction(this);
		}
	}
}
