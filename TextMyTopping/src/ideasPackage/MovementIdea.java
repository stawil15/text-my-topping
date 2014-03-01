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
public class MovementIdea extends PApplet
{
	private static final long serialVersionUID = 1L;
	public static int GRID_SIZE = 32;
	private final static int SCREEN_WIDTH = 640;
	private final static int SCREEN_HEIGHT = 480;
	private CollisionGrid collisionGrid;
	private StaticObject[] treesRowLeft;
	private StaticObject[] treesRowRight;
	
	private boolean keyLeft, keyRight, keyUp, keyDown;
	

	public static void main(String args[])
	{
		PApplet.main("ideasPackage.MovementIdea");
		
	}

	private Character testCharacter;
	private StaticObject tree;

	public void setup()
	{
		collisionGrid = new CollisionGrid(SCREEN_WIDTH/GRID_SIZE, SCREEN_HEIGHT/GRID_SIZE);
		
		treesRowLeft = new StaticObject[8];
		treesRowRight = new StaticObject[treesRowLeft.length];
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		testCharacter = new Character(new GridCoordinate(2, 2), Character.DIRECTION_RIGHT, 4,
				"player", collisionGrid, this);
		tree = new StaticObject(new GridCoordinate(5, 5), "tree", collisionGrid, 4, 25, this);
		
		for (int index = 0; index < treesRowLeft.length; index++)
		{
			treesRowLeft[index] = new StaticObject(new GridCoordinate(7, index), "tree", collisionGrid, 4, 25, this);
			treesRowRight[index] = new StaticObject(new GridCoordinate(9, index), "tree", collisionGrid, 4, 25, this);
		}
		frame.setTitle("Use Arrow Keys To Move");
		
	}

	public void draw()
	{
		background(255);
		collisionGrid.draw();
		
		if (keyLeft)
		{
			keyLeftDown();
		}
		else if (keyRight)
		{
			keyRightDown();
		}
		else if (keyDown)
		{
			keyDownDown();
		}
		else if (keyUp)
		{
			keyUpDown();
		}
		
	}
	
	public void keyLeftDown()
	{
		testCharacter.move(Character.DIRECTION_LEFT);
	}
	
	public void keyRightDown()
	{
		testCharacter.move(Character.DIRECTION_RIGHT);		
	}
	
	public void keyDownDown()
	{
		testCharacter.move(Character.DIRECTION_DOWN);
	}
	
	public void keyUpDown()
	{
		testCharacter.move(Character.DIRECTION_UP);
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
		}
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
