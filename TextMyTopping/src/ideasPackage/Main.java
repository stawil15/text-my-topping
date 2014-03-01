package ideasPackage;

import processing.core.*;

// Eric Mustee
// 2/28/2014
// This package contains ideas on how we think things can
// be implemented. 

// In Character and MovementIdea, I have an idea how we
// can move a character across the screen, as well as
// some basic ideas on how to animate the characters
// movements.
public class Main extends PApplet
{
	private static final long serialVersionUID = 1L;
	public static int GRID_SIZE = 32;
	private final static int SCREEN_WIDTH = 640;
	private final static int SCREEN_HEIGHT = 480;
	private CollisionGrid collisionGrid;
	private SceneryGrid sceneryGrid;
	private Dialog currentDialog;

	private StaticObject[] treesRowLeft;
	private StaticObject[] treesRowRight;
	private Dialog TestDialog;
	private Camera camera;

	private boolean keyLeft, keyRight, keyUp, keyDown;

	public static void main(String args[])
	{
		PApplet.main("ideasPackage.Main");

	}

	private PlayerCharacter testCharacter;
	private NonPlayerCharacter testNPC;

	private StaticObject tree;

	public void setup()
	{
		collisionGrid = new CollisionGrid(SCREEN_WIDTH / GRID_SIZE + 20,
				SCREEN_HEIGHT / GRID_SIZE + 20);
		sceneryGrid = new SceneryGrid(SCREEN_WIDTH / GRID_SIZE + 20,
				SCREEN_HEIGHT / GRID_SIZE + 20);

		for (int x = 0; x < SCREEN_WIDTH / GRID_SIZE + 20; x++)
		{
			for (int y = 0; y < SCREEN_HEIGHT / GRID_SIZE + 20; y++)
			{
				if (Math.random() < .90)
				{
					new SceneryObject(new GridCoordinate(x, y), "grass", 1, 20,
							sceneryGrid, this);
				} else
				{
					new SceneryObject(new GridCoordinate(x, y), "flower", 2,
							20 + (int) (Math.random() * 8), sceneryGrid, this);
				}
			}
		}

		Dialog npcDialog = new Dialog(new String[] { "Hello, I am an NPC!",
				"This is a new Page!" }, this);
		npcDialog
				.setNextDialog(new Dialog(
						new String[] { "This shows how dialogs can be stringed\ntogether like linked lists." },
						this));

		testNPC = new NonPlayerCharacter(new GridCoordinate(8, 9), 2, 1, "npc",
				collisionGrid, npcDialog, this);

		treesRowLeft = new StaticObject[8];
		treesRowRight = new StaticObject[treesRowLeft.length];

		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		testCharacter = new PlayerCharacter(new GridCoordinate(2, 2),
				Character.DIRECTION_RIGHT, 4, "player", collisionGrid, this);
		camera = new Camera(new GridCoordinate(0, 0), testCharacter, this);
		tree = new StaticObject(new GridCoordinate(5, 5), "tree",
				collisionGrid, 4, 25, this);

		for (int index = 0; index < treesRowLeft.length; index++)
		{
			treesRowLeft[index] = new StaticObject(
					new GridCoordinate(7, index), "tree", collisionGrid, 4, 25,
					this);
			treesRowRight[index] = new StaticObject(
					new GridCoordinate(9, index), "tree", collisionGrid, 4, 25,
					this);
		}
		frame.setTitle("Use Arrow Keys To Move");
		TestDialog = new Dialog(
				new String[] { "Hello, World!\nUse arrow keys to move\nPress [Space] To Continue." },
				this);
		TestDialog.showDialog();
		collisionGrid.setCamera(camera);
		sceneryGrid.setCamera(camera);

	}

	public void draw()
	{
		
		background(255);
		camera.update();
		sceneryGrid.draw();
		collisionGrid.draw();
		

		if (currentDialog != null)
		{
			currentDialog.drawDialog();
		}

		if (keyLeft)
		{
			keyLeftDown();
		} else if (keyRight)
		{
			keyRightDown();
		} else if (keyDown)
		{
			keyDownDown();
		} else if (keyUp)
		{
			keyUpDown();
		}

	}

	public boolean canMove()
	{
		if (currentDialog != null)
			return false;

		return true;
	}

	public void keyLeftDown()
	{
		if (canMove())
			testCharacter.move(Character.DIRECTION_LEFT);
	}

	public void keyRightDown()
	{
		if (canMove())
			testCharacter.move(Character.DIRECTION_RIGHT);
	}

	public void keyDownDown()
	{
		if (canMove())
			testCharacter.move(Character.DIRECTION_DOWN);
	}

	public void keyUpDown()
	{
		if (canMove())
			testCharacter.move(Character.DIRECTION_UP);
	}

	public void spaceKeyPressed()
	{
		boolean justClosedDialog = false;
		if (currentDialog != null)
		{
			currentDialog.advanceText();
			if (currentDialog == null)
			{
				justClosedDialog = true;
			}
		}

		if (!justClosedDialog && currentDialog == null)
			testCharacter.doInteract();
	}

	public void showDialog(Dialog d)
	{
		currentDialog = d;
	}

	public void keyPressed()
	{
		switch (keyCode)
		{
		case LEFT:
			keyLeft = true;
			break;
		case RIGHT:
			keyRight = true;
			break;
		case UP:
			keyUp = true;
			break;
		case DOWN:
			keyDown = true;
			break;
		case ' ':
			spaceKeyPressed();
			break;
		}
	}

	public void doCharacterInteraction()
	{

	}

	public void keyReleased()
	{
		switch (keyCode)
		{
		case LEFT:
			keyLeft = false;
			break;
		case RIGHT:
			keyRight = false;
			break;
		case UP:
			keyUp = false;
			break;
		case DOWN:
			keyDown = false;
			break;
		}
	}
}
