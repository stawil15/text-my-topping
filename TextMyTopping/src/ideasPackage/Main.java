package ideasPackage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import processing.core.*;
import ddf.minim.*;

/* Eric Mustee, Saeed Tawil, Graham Jenkins, David Clifford 
 * 2/28/2014 - 3/30/2014
 * Text My Topping is a game of dexterity where the player
 * must complete certain puzzles and talk to the correct
 * people to win. The goal of the game is to get a stolen
 * cell phone back that your worst enemy has taken from 
 * you. 
 */

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
	public static int CONTROL_KEY = KeyEvent.VK_CONTROL;
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

	// Setup everything
	public void setup()
	{
		mainClass = this;
		font = loadFont("data\\fonts\\MiniPower.vlw");
		
		DialogManager.initializeDialogManager();
		GUISystem.initialize();
		MusicManager.initialize();
		LevelManager.initializeLevelManager(20,4);
		GlobalBooleanManager.initialize();
		GlobalStringManager.initialize();
		LevelCreator.initialize();
		
		// Used for debugging/testing
		//GlobalStringManager.addString("yourName", "Your Name");
		//GlobalStringManager.addString("enemyName", "Enemy Name");
		//GlobalBooleanManager.setValue("hasWindAxe", true);
		//GlobalBooleanManager.setValue("hadGnomeTalk", true);
		
		// Set the screen size and title
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setTitle("Text My Topping");
		frameRate(60);
		
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Start Game");
		choices.add("Help");
		choices.add("Exit");
		MainMenu menu = new MainMenu(loadImage("/data/sprites/MainMenu.png"),choices);
		
		enterName = new Dialog(new String[] {"Hello good sir or madam! \n(Press spacebar to continue)", "What a lovely day to be on a butterfly \ncatching expedition!", "What is your name?"});
		
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
		
		explenationDialog.setNextDialog(new Dialog(new String[] {"Use the arrow keys to move and press space\nto interact with things! Use CTRL to pull \nitems!"}));
		namePrompt.setNextDialog(welcomeDialog);
		menu.showDialog();
	}

	// draw everything
	public void draw()
	{		
		if (!loaded && !GUISystem.showingDialog())
		{
			loaded = true;
			GUISystem.setDoingTransition(true);
			LevelManager.setActiveLevel("megaSpriteMap.csv", null, Character.DIRECTION_UP);
			enterName.showDialog();
			
		}
		else
		{
			LevelManager.drawActiveLevel();
		}
		GUISystem.draw();
		MusicManager.update();
		//FPS counter
		fill(255);
		timeMultiplier = 60f/frameRate;
	}

	// get the main object 
	public static Main getMainObject()
	{
		return mainClass;
	}


	// Get the player character
	public static PlayerCharacter getPlayer()
	{
		return Main.getMainObject().mainCharacter;
	}
	
	// Get the camera
	public static Camera getCamera()
	{
		return camera;
	}
	
	// Set the camera
	public static void setCamera(Camera camera)
	{
		Main.camera = camera;
	}
	
	// Set the player
	public static void setPlayer(PlayerCharacter playerCharacter)
	{
		Main.getMainObject().mainCharacter = playerCharacter;
	}
	
	// get the pixels that are currently being drawn to screen
	public static int[] getPixels()
	{
		getMainObject().loadPixels();
		return getMainObject().pixels;

	}
	
	// Returns the value the animation speed and movement speed should be
	// multiplied by to ensure that everything moves at the same speed
	// regardless of the framerate. 
	public static float getTimeMultiplier()
	{
		return timeMultiplier;
	}

}
