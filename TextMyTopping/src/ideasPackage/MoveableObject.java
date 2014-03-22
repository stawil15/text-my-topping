package ideasPackage;

import processing.core.PImage;

public class MoveableObject extends Character
{

	public boolean updatePlayerDirectionWhenFinishedMoving = false;
	public MoveableObject(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, "moveable", addToGrid);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doInteract(int interactionId)
	{
		if (interactionId == PlayerCharacter.MAIN_INTERACTION)
		{
			setDirection(Main.getPlayer().getDirection());
			if (canMove())
			{
				updatePlayerDirectionWhenFinishedMoving = false;
				move((Main.getPlayer()).getDirection());
				Main.getPlayer().move(Main.getPlayer().getDirection());
			}
		} 
		else if (interactionId == PlayerCharacter.SECONDARY_INTERACTION)
		{
			setDirection(Main.getPlayer().getOppositeDirection());
			Main.getPlayer().setDirection(getDirection());

			if (Main.getPlayer().move(Main.getPlayer().getDirection()))
			{
				updatePlayerDirectionWhenFinishedMoving = true;
				move((Main.getPlayer()).getDirection());
			}
			else
			{
				Main.getPlayer().setDirection(Main.getPlayer().getOppositeDirection());
			}

		}
	}
	
	@Override 
	public boolean move(int direction)
	{
		boolean didMove = super.move(direction);
		
		return didMove;
	}

	@Override
	public void finishedMoving()
	{
		if (updatePlayerDirectionWhenFinishedMoving)
			Main.getPlayer().setDirection(getOppositeDirection());
	}
}
