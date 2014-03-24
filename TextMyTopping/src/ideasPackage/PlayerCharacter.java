package ideasPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerCharacter extends Character implements KeyListener
{

	private boolean leftKeyDown, rightKeyDown, upKeyDown, downKeyDown;
	public final static int MAIN_INTERACTION = 0;
	public final static int SECONDARY_INTERACTION = 1;
	public final static int BUMP_INTERACTION = 2;

	public PlayerCharacter(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, addToGrid);
		parent.addKeyListener(this);
	}

	@Override
	public void doInteract(int interactionId)
	{
		if (!isMoving)
		{
			collisionGrid.doInteraction(this, interactionId);
		}
	}

	public boolean isMoving()
	{
		return isMoving;
	}

	public float getOffsetX()
	{
		return offsetX;
	}

	public float getOffsetY()
	{
		return offsetY;
	}

	public void draw(float cameraOffsetX, float cameraOffsetY)
	{
		super.draw(cameraOffsetX, cameraOffsetY);
		if (leftKeyDown)
		{
			keyLeftDown();
		} else if (rightKeyDown)
		{
			keyRightDown();
		} else if (downKeyDown)
		{
			keyDownDown();
		} else if (upKeyDown)
		{
			keyUpDown();
		}

	}

	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
		int keyCode = keyEvent.getKeyCode();
		if (keyCode == Main.LEFT_KEY)
		{
			leftKeyDown = true;
		}
		if (keyCode == Main.RIGHT_KEY)
		{
			rightKeyDown = true;
		}
		if (keyCode == Main.UP_KEY)
		{
			upKeyDown = true;
		}
		if (keyCode == Main.DOWN_KEY)
		{
			downKeyDown = true;
		}
		if (keyCode == Main.SPACE_KEY)
		{
			spaceKeyPressed();
		}
		if (keyCode == Main.SHIFT_KEY)
		{
			shiftKeyPressed();
		}

	}

	@Override
	public void keyReleased(KeyEvent keyEvent)
	{
		int keyCode = keyEvent.getKeyCode();
		if (keyCode == Main.LEFT_KEY)
		{
			leftKeyDown = false;
		} else if (keyCode == Main.RIGHT_KEY)
		{
			rightKeyDown = false;
		} else if (keyCode == Main.UP_KEY)
		{
			upKeyDown = false;
		} else if (keyCode == Main.DOWN_KEY)
		{
			downKeyDown = false;
		}

	}

	private void spaceKeyPressed()
	{
		boolean justClosedDialog = false;
		if (GUISystem.showingDialog())
		{
			GUISystem.advancedCurrentDialog();
			if (!GUISystem.showingDialog())
			{
				justClosedDialog = true;
			}
		}

		if (!justClosedDialog && !GUISystem.showingDialog())
			doInteract(MAIN_INTERACTION);
	}

	private void shiftKeyPressed()
	{
		if (!isMoving)
		{
			doInteract(SECONDARY_INTERACTION);
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	// Called when the left key is held down
	private void keyLeftDown()
	{
		if (canMove())
			move(Character.DIRECTION_LEFT);
	}

	// Called when the right key is held down
	private void keyRightDown()
	{
		if (canMove())
			move(Character.DIRECTION_RIGHT);
	}

	// Called when the down key is held down
	private void keyDownDown()
	{
		if (canMove())
			move(Character.DIRECTION_DOWN);
	}

	// Called when the up key is held down
	private void keyUpDown()
	{
		if (canMove())
			move(Character.DIRECTION_UP);
	}
	
	public void setCoordinates(GridCoordinate coordinates)
	{
		this.coordinates = coordinates;
		Main.getCamera().centerCameraAroundTracker();
	}
	
	public void setCollisionGrid(CollisionGrid c)
	{
		c.addElement(coordinates, this);
		collisionGrid = c;
	}

}
