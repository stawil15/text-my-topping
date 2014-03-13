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

	// Grids containing objects of the game
	private Level level;

	// The camera is positioned in the top left corner
	private Camera camera;

	//Music stuff. Uses Minim Library from Processing.
	Minim minim;
	AudioPlayer player;
	
	//Grid stuff
	private CollisionGrid collisionGrid;
	private SceneryGrid sceneryGrid;

	// NPC stuff
	private Dialog TestDialog;
	private PlayerCharacter mainCharacter;
	@SuppressWarnings("unused")
	private NonPlayerCharacter testNPC;
	@SuppressWarnings("unused")
	private NonPlayerCharacter testNPC2;

	// Stuff stuff
	private StaticObject tree;
	private StaticObject fastTree;
	private StaticObject invisibleWall;

	// Keyboard controls
	public static int LEFT_KEY = KeyEvent.VK_LEFT;
	public static int RIGHT_KEY = KeyEvent.VK_RIGHT;
	public static int UP_KEY = KeyEvent.VK_UP;
	public static int DOWN_KEY = KeyEvent.VK_DOWN;
	public static int SPACE_KEY = KeyEvent.VK_SPACE;

	// Start Processing
	public static void main(String args[])
	{
		PApplet.main("ideasPackage.Main");

	}

	public void setup()
	{

		mainClass = this;
		DialogManager.initializeDialogManager();
		// Set the screen size and title
		size(SCREEN_WIDTH, SCREEN_HEIGHT);
		frame.setTitle("Use Arrow Keys To Move");
		LoadMap("megaSpriteMap.csv",10,4);
	}

	public void draw()
	{
		level.draw();
		GUISystem.draw();
	}

	public static Main getMainObject()
	{
		return mainClass;
	}

	public void LoadMap(String mapName, int charX, int charY)
	{
		//Stuff will get moved here later.

		// Setup the collision grids first
		readCSV maploader = new readCSV();
		//maploader.readDialogueData(mapName);
		int[][] map = maploader.readMapData(mapName);
		
		//Maps
		collisionGrid = new CollisionGrid(map.length, map[0].length);//What blocks your path
		sceneryGrid = new SceneryGrid(map.length, map[0].length);//Floor tiles

		// Create some scenery objects to add to the grid later.
		SceneryObject grass = new SceneryObject(null, "grass", 1, 20,sceneryGrid, false);
		SceneryObject flower = new SceneryObject(null, "flower", 2, 20, sceneryGrid, false);
		tree = new StaticObject(null, "tree", collisionGrid, 4, 40, false);
		fastTree = new StaticObject(null,"tree", collisionGrid, 4, 4, false);
		invisibleWall = new StaticObject(null,collisionGrid,false);

		//Can't get .mid working again. MP3 works fine
		minim = new Minim(this);
		player = minim.loadFile("data/audio/bgm/OnettTheme.mp3");
		player.loop();

		//If map is smaller than screen view, offset will be used to center map on screen
		int xOffset = 0;
		int yOffset = 0;
		//calculate grid size on screen
		int gridWidth = SCREEN_WIDTH/GRID_SIZE;
		int gridHeight = SCREEN_HEIGHT/GRID_SIZE;
		//calculate correct offset (if any)
		if (gridWidth > map.length)
		{
			xOffset = (gridWidth-map.length)/2;
		}
		if (gridHeight > map[0].length)
		{
			yOffset = (gridHeight-map[0].length)/2;
		}

		//Builds map based on CSV contents
		for (int x = 0; x < map.length; x++)
		{
			for (int y = 0; y < map[0].length; y++)
			{
				if (map[x][y] == 9)
				{
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), invisibleWall);
				}
				else if (map[x][y] != 2)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),
							grass);
				}
				if (map[x][y] == 2)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),
							flower);
				}
				else if (map[x][y] == 1)
				{
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), tree);
				}
				else if (map[x][y] == 3)
				{
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), fastTree);
					fastTree.setDialog(new Dialog(new String[] {"It's really windy right here.\nIn this exact spot."}));
				}
				else if (map[x][y] == 5)
				{
					//5 == Door
				}
			}
		}

		String[][] NPC = maploader.readNPCData("NPC" + mapName); //naming convention will allow us to add "NPC" to name variable.
		for (int x = 0; x < NPC.length; x++)
		{
			if (NPC[x][0]!= null)
			{
				if (NPC[x][0].equals("NPC"))
				{
					Dialog npcDialog = null;
					if (NPC[x][4]!=null && !NPC[x][4].equals(readCSV.NULL_DIALOG))
					{
						//Creates NPC dialog
						npcDialog = DialogManager.getDialog(NPC[x][4]);
						println("ASSINGNED: " + NPC[x][4]);
					}
					//Places NPC
					NonPlayerCharacter testNPC = new NonPlayerCharacter(new GridCoordinate(Integer.parseInt(NPC[x][1])+xOffset, Integer.parseInt(NPC[x][2])+yOffset), 2, 1, "npc",collisionGrid, npcDialog, true);
				}
				else if (NPC[x][0].equals("enemy"))
				{
					//stuff goes here.
				}
			}
		}

		//Creates main character
		mainCharacter = new PlayerCharacter(new GridCoordinate(charX+xOffset, charY+yOffset),
				Character.DIRECTION_RIGHT, 8, "player", collisionGrid, true);
		camera = new Camera(new GridCoordinate(0, 0), mainCharacter);

		// This dialog shows at startup
		TestDialog = new Dialog(
				new String[] { "Hello, World!\nUse arrow keys to move\nPress [Space] To Continue." });
		TestDialog.showDialog();

		// Create the level
		level = new Level(collisionGrid, sceneryGrid, camera,this);
		
	}


}
