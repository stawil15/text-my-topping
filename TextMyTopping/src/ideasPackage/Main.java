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
	private static PlayerCharacter mainCharacter;
	@SuppressWarnings("unused")
	private NonPlayerCharacter testNPC;
	@SuppressWarnings("unused")
	private NonPlayerCharacter testNPC2;

	// Stuff stuff
	private StaticObject tree;
	private StaticObject fastTree;
	private StaticObject invisibleWall;
	private StaticObject cactus;
	private StaticObject fastCactus;
	private StaticObject snowTree;
	private StaticObject fastSnowTree;

	// Keyboard controls
	public static int LEFT_KEY = KeyEvent.VK_LEFT;
	public static int RIGHT_KEY = KeyEvent.VK_RIGHT;
	public static int UP_KEY = KeyEvent.VK_UP;
	public static int DOWN_KEY = KeyEvent.VK_DOWN;
	public static int SPACE_KEY = KeyEvent.VK_SPACE;
	
//	// stuff for start screen
//	int buttonX, buttonY;
//	int buttonWidth, buttonHeight;
//	boolean buttonOver;
//	PApplet parent;

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
		
		//FPS counter
		fill(255);
		text((int)(frameRate),10,20);
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
		maploader.readDialogueData(mapName);
		int[][] map = maploader.readMapData(mapName);
		
		//Maps
		collisionGrid = new CollisionGrid(map.length, map[0].length);//What blocks your path
		sceneryGrid = new SceneryGrid(map.length, map[0].length);//Floor tiles

		// Create some scenery objects to add to the grid later.
		// forest
		SceneryObject grass = new SceneryObject(null, "forest\\grass", 1, 20,sceneryGrid, false);
		SceneryObject flower = new SceneryObject(null, "forest\\flower", 2, 20, sceneryGrid, false);
		tree = new StaticObject(null, "forest\\tree", collisionGrid, 4, 40, false);
		fastTree = new StaticObject(null,"forest\\tree", collisionGrid, 4, 4, false);
		invisibleWall = new StaticObject(null,collisionGrid,false);
		// desert
		cactus = new StaticObject(null, "desert\\tree", collisionGrid, 4, 40, false);
		SceneryObject sand = new SceneryObject(null, "desert\\grass", 1, 20,sceneryGrid, false);
		SceneryObject dflower = new SceneryObject(null, "desert\\flower", 2, 20, sceneryGrid, false);
		// snow
		snowTree = new StaticObject(null, "snow\\tree", collisionGrid, 4, 40, false);
		SceneryObject snow = new SceneryObject(null, "snow\\grass", 1, 20,sceneryGrid, false);
		SceneryObject sflower = new SceneryObject(null, "snow\\flower", 2, 20, sceneryGrid, false);
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
				else if (map[x][y] == 1)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),grass);
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), tree);
				}
				else if (map[x][y] == 2)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),flower);
				}
				else if (map[x][y] == 3)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),grass);
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), fastTree);
					fastTree.setDialog(new Dialog(new String[] {"It's really windy right here.\nIn this exact spot."}));
				}
				else if (map[x][y] == 11) // start desert
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),sand);
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), cactus);
				}
				else if (map[x][y] == 12)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),dflower);
				}
				else if (map[x][y] == 13)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),sand);
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), fastCactus);
					fastTree.setDialog(new Dialog(new String[] {"It's really windy right here.\nIn this exact spot."}));
				}
				else if (map[x][y] == 21) // start snow
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),snow);
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), snowTree);
				}
				else if (map[x][y] == 22)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),sflower);
				}
				else if (map[x][y] == 23)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),snow);
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), fastSnowTree);
					fastTree.setDialog(new Dialog(new String[] {"It's really windy right here.\nIn this exact spot."}));
				}
				else if (map[x][y] == 20)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),sand);
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), fastCactus);
					fastTree.setDialog(new Dialog(new String[] {"It's really windy right here.\nIn this exact spot."}));
				}
				else
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),grass);
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

	public static PlayerCharacter getPlayer()
	{
		return mainCharacter;
	}

}
