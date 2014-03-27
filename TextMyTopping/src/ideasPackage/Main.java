package ideasPackage;

import java.awt.event.KeyEvent;
import processing.core.*;
import ddf.minim.*;

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

	// Needed constants
	public static int GRID_SIZE = 64; //
	public final static int SCREEN_WIDTH = 1024;
	public final static int SCREEN_HEIGHT = 768;
	private static Main mainClass;

	// The camera is positioned in the top left corner
	private static Camera camera;

	// Keyboard controls
	public static int LEFT_KEY = KeyEvent.VK_LEFT;
	public static int RIGHT_KEY = KeyEvent.VK_RIGHT;
	public static int UP_KEY = KeyEvent.VK_UP;
	public static int DOWN_KEY = KeyEvent.VK_DOWN;
	public static int SPACE_KEY = KeyEvent.VK_SPACE;
	public static int SHIFT_KEY = KeyEvent.VK_SHIFT;
	public static PFont font;
	

	private PlayerCharacter mainCharacter;
	// Start Processing
	public static void main(String args[])
	{
		PApplet.main("ideasPackage.Main");

	}

	public void setup()
	{
		mainClass = this;
		font = loadFont("data\\fonts\\MiniPower.vlw");
		
		DialogManager.initializeDialogManager();
		GUISystem.initialize();
		MusicManager.initialize();
		LevelManager.initializeLevelManager(10,4);
		GlobalBooleanManager.initialize();
		LevelCreator.initialize();
		
		// Set the screen size and title
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setTitle("Use Arrow Keys To Move");
		LevelManager.setActiveLevel("megaSpriteMap.csv", null, -1);
		//LoadMap("megaSpriteMap.csv",22,23);
	}

	public void draw()
	{
		LevelManager.drawActiveLevel();
		GUISystem.draw();
		MusicManager.update();
		
		//FPS counter
		fill(255);
		text((int)(frameRate),10,20);
	}

	public static Main getMainObject()
	{
		return mainClass;
	}


	public static PlayerCharacter getPlayer()
	{
		return Main.getMainObject().mainCharacter;
	}
	
	public static Camera getCamera()
	{
		return camera;
	}
	
	public static void setCamera(Camera camera)
	{
		Main.camera = camera;
	}
	
	public static void setPlayer(PlayerCharacter playerCharacter)
	{
		Main.getMainObject().mainCharacter = playerCharacter;
	}
	
	public static int[] getPixels()
	{
		getMainObject().loadPixels();
		return getMainObject().pixels;

	}

}
