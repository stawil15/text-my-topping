package ideasPackage;

import processing.core.PImage;

public class MoveableObject extends Character
{
	// This class creates the rock that can be moved into a hole by the player. The rock
	// extends character because character already has nice movement built in.
	public boolean updatePlayerDirectionWhenFinishedMoving = false;
	private boolean destroyOnMove = false;
	private SceneryHole sceneryHole;
	private PImage holeImage;

	public MoveableObject(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, "moveable", addToGrid);
		holeImage = parent.loadImage("/data/sprites/moveable/" + imageName + "/inHole.png");
		// TODO Auto-generated constructor stub
	}

	@Override
	
	// if the user does a main interaction (the space key), then 
	// the rock is pushed. The secondary interaction pulls the rock. 
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
		} else if (interactionId == PlayerCharacter.SECONDARY_INTERACTION)
		{
			setDirection(Main.getPlayer().getOppositeDirection());
			Main.getPlayer().setDirection(getDirection());

			if (Main.getPlayer().move(Main.getPlayer().getDirection()))
			{
				updatePlayerDirectionWhenFinishedMoving = true;
				move((Main.getPlayer()).getDirection());
			} else
			{
				Main.getPlayer().setDirection(Main.getPlayer().getOppositeDirection());
			}

		}
	}

	// Does the movement
	@Override
	public boolean move(int direction)
	{
		boolean didMove = super.move(direction);

		return didMove;
	}

	// Destroy the object when its finished moving if its been marked for
	// destruction
	@Override
	public void finishedMoving()
	{
		if (updatePlayerDirectionWhenFinishedMoving)
			Main.getPlayer().setDirection(getOppositeDirection());
		if (destroyOnMove)
		{
			collisionGrid.removeEntitytAt(coordinates);
			sceneryHole.setMoveableImage(holeImage);
		}
	}

	// get the current drawn image
	public PImage getImage()
	{
		return getImageToDraw();
	}

	// Mark the object for destruction, or reset it by setting
	// the hole to null
	public void setDestroyOnMove(SceneryHole hole)
	{
		if (hole != null)
		{
			destroyOnMove = true;
			sceneryHole = hole;
		} else
		{
			destroyOnMove = false;
			sceneryHole = null;
		}

	}
}
