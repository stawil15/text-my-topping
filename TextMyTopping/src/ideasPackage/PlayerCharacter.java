package ideasPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayerCharacter extends Character implements KeyListener
{
	// This class adds the player
	
	private boolean leftKeyDown, rightKeyDown, upKeyDown, downKeyDown;
	public final static int MAIN_INTERACTION = 0;
	public final static int SECONDARY_INTERACTION = 1;
	public final static int BUMP_INTERACTION = 2;
	
	private int interactionNumber = -1;

	public PlayerCharacter(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, addToGrid);
		parent.addKeyListener(this);
	}

	
	// Interact on an object
	private void doInteraction(int interactionId)
	{
		interactionNumber = -1;
		if (!isMoving)
		{
			collisionGrid.doInteraction(this, interactionId);
		}
	}
	
	// Player character has no interaction because it is the thing doing all the
	// interactions
	@Override
	public void doInteract(int interactionId)
	{

	}

	// Returns whether the player is moving
	public boolean isMoving()
	{
		return isMoving;
	}

	// Get the offset x relative the grid space the player is on
	public float getOffsetX()
	{
		return offsetX;
	}

	// Get the offset y relative the grid space the player is on
	public float getOffsetY()
	{
		return offsetY;
	}

	// Draw the character, and move the palyer or do an interaction if a key is held down or was pressed
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
		
		if (interactionNumber!=-1)
		{
			doInteraction(interactionNumber);
		}

	}

	// Action to perform when a key was pressed
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

	// Action to perform when a key was released
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
	
	// Action to perform when the space key was pressed
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

		if (!justClosedDialog && GUISystem.allowMovement())
			interactionNumber = MAIN_INTERACTION;
	}

	// Action to perform when the shift key was pressed
	private void shiftKeyPressed()
	{
		if (!isMoving)
		{
			interactionNumber = SECONDARY_INTERACTION;
		}
	}

	// Needed for keyListener implementation
	@Override
	public void keyTyped(KeyEvent arg0)
	{

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
	
	// Set the coordiantes of the player
	public void setCoordinates(GridCoordinate coordinates)
	{
		this.coordinates = coordinates;
		Main.getCamera().centerCameraAroundTracker();
	}
	
	// Set the collisionGrid of the player
	public void setCollisionGrid(CollisionGrid c)
	{
		c.addEntity(coordinates, this);
		collisionGrid = c;
	}

}
