package ideasPackage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

	// more stuff stuff
	
	
	// Keyboard controls
	public static int LEFT_KEY = KeyEvent.VK_LEFT;
	public static int RIGHT_KEY = KeyEvent.VK_RIGHT;
	public static int UP_KEY = KeyEvent.VK_UP;
	public static int DOWN_KEY = KeyEvent.VK_DOWN;
	public static int SPACE_KEY = KeyEvent.VK_SPACE;
	public static int SHIFT_KEY = KeyEvent.VK_SHIFT;
	public static PFont font;
	private static float timeMultiplier = 1f;
	private boolean loaded = false;
	private Dialog enterName;


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
		LevelManager.initializeLevelManager(4,4);
		GlobalBooleanManager.initialize();
		GlobalStringManager.initialize();
		LevelCreator.initialize();
		
		// Set the screen size and title
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setTitle("Text My Topping");
		
		//LoadMap("megaSpriteMap.csv",22,23);
		frameRate(60);
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Start Game");
		choices.add("Exit");
		MainMenu menu = new MainMenu(loadImage("/data/sprites/MainMenu.png"),choices);
		
		enterName = new Dialog(new String[] {"Hello good sir or madam! \n(Press spacebar to continue)", "Wait a lovely day to be on a butterfly \ncatching expedition!", "What is your name?"});
		
		ValueSetStringDialog namePrompt = new ValueSetStringDialog("yourName", "What is your name?", "Enter a name.");
		enterName.setNextDialog(namePrompt);
		
		Dialog welcomeDialog = new Dialog(new String[] {"Welcome, \\NAME!","Now, what is the name of your worst enemy?"});
		ValueSetStringDialog enemyPrompt = new ValueSetStringDialog("enemyName", "What is the name of your worst enemy?", "Enter another name.");
		welcomeDialog.setNextDialog(enemyPrompt);
		Dialog explenationDialog = new Dialog(new String[] {"Well, as it turns out, \\ENEMY stole your\ncell phone."
				+ " This happened as you were texting \nyour roommate your favorite pizza topping.", "You need to get it back before you return \nhome!", 
				"your roommate has terrible choice in toppings. \nHe cannot be allowed to make such a decision \non his own!", "Now, if you do not get your cell phone \nback, you may not get the topping \nyou wanted.", "It is your duty to"
				+ " secure your \ncell phone and teach \\ENEMY who's boss."});
		enemyPrompt.setNextDialog(explenationDialog);
		
		explenationDialog.setNextDialog(new Dialog(new String[] {"Use the arrow keys to move and press space\nto interact with things! Use shift to use special items!"}));
		namePrompt.setNextDialog(welcomeDialog);
		menu.showDialog();
	}

	public void draw()
	{		
		if (!loaded && !GUISystem.showingDialog())
		{
			loaded = true;
			GUISystem.setDoingTransition(true);
			LevelManager.setActiveLevel("megaSpriteMap.csv", null, Character.DIRECTION_UP);
			//enterName.showDialog();
			
		}
		else
		{
			LevelManager.drawActiveLevel();
		}
		GUISystem.draw();
		MusicManager.update();
		//FPS counter
		fill(255);
		//text((int)(frameRate),10,20);
		timeMultiplier = 60f/frameRate;
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
	
	public static float getTimeMultiplier()
	{
		return timeMultiplier;
	}

}
