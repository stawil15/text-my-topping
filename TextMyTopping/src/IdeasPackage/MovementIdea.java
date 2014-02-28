package IdeasPackage;

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
	private boolean keyLeft, keyRight, keyUp, keyDown;

	public static void main(String args[])
	{
		PApplet.main("IdeasPackage.MovementIdea");
	}

	private Character testCharacter;

	public void setup()
	{

		size(640, 480);
		testCharacter = new Character(2, 2, Character.DIRECTION_RIGHT, 4,
				"player", this);
		frame.setTitle("Use Arrow Keys To Move");
	}

	public void draw()
	{
		background(255);
		testCharacter.draw();
		
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
