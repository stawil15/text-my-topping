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

	//Music
	Minim minim;
	AudioPlayer player;

	// NPC stuff
	private Dialog TestDialog;
	private PlayerCharacter testCharacter;
	@SuppressWarnings("unused")
	private NonPlayerCharacter testNPC;
	@SuppressWarnings("unused")
	private NonPlayerCharacter testNPC2;

	// A tree
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
		// Setup the collision grids first
		readCSV maploader = new readCSV();
		maploader.readDialogueData("redSpriteMap.csv");
		int[][] map = maploader.readMapData("redSpriteMap.csv");


		CollisionGrid collisionGrid = new CollisionGrid(map.length, map[0].length);
		SceneryGrid sceneryGrid = new SceneryGrid(map.length, map[0].length);

		// Create some scenery objects to add to the grid later.
		SceneryObject grass = new SceneryObject(null, "grass", 1, 20,sceneryGrid, false);
		SceneryObject flower = new SceneryObject(null, "flower", 2, 20 + (int) (Math.random() * 8), sceneryGrid, false);
		tree = new StaticObject(null, "tree", collisionGrid, 1, 25, false);
		fastTree = new StaticObject(null,"tree", collisionGrid, 4, 4, false);
		invisibleWall = new StaticObject(null,collisionGrid,false);

		//It worked once. I don't know how I broke it.
		minim = new Minim(this);
		player = minim.loadFile("data/audio/bgm/z1title.mid");
		player.loop();
		
		int xOffset = 0;
		int yOffset = 0;

		int gridWidth = SCREEN_WIDTH/GRID_SIZE;
		int gridHeight = SCREEN_HEIGHT/GRID_SIZE;

		if (gridWidth > map.length)
		{
			xOffset = (gridWidth-map.length)/2;
		}
		if (gridHeight > map[0].length)
		{
			yOffset = (gridHeight-map[0].length)/2;
		}

		for (int x = 0; x < map.length; x++)
		{
			for (int y = 0; y < map[x].length; y++)
			{
				if (map[y][x] == 9)
				{
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), invisibleWall);
				}
				else if (map[y][x] != 2)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),
							grass);
				}
				if (map[y][x] == 2)
				{
					sceneryGrid.addSceneryObject(new GridCoordinate(x+xOffset, y+yOffset),
							flower);
				}
				else if (map[y][x] == 1)
				{
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), tree);
				}
				else if (map[y][x] == 3)
				{
					collisionGrid.addElement(new GridCoordinate(x+xOffset, y+yOffset), fastTree);
					fastTree.setDialog(new Dialog(new String[] {"It's really windy right here.\nIn this exact spot."}));
				}
			}
		}

		String[][] NPC = maploader.readNPCData("NPC" + "redSpriteMap.csv"); //naming convention will allow us to add "NPC" to name variable.
		for (int x = 0; x < NPC.length; x++)
		{
			if (NPC[x][0]!= null && NPC[x][0].equals("NPC"))
			{
				Dialog npcDialog = null;
				if (NPC[x][4]!=null && !NPC[x][4].equals(readCSV.NULL_DIALOG))
				{
					npcDialog = DialogManager.getDialog(NPC[x][4]);
					println("ASSINGNED: " + NPC[x][4]);
				}

				NonPlayerCharacter testNPC = new NonPlayerCharacter(new GridCoordinate(Integer.parseInt(NPC[x][1])+xOffset, Integer.parseInt(NPC[x][2])+yOffset), 2, 1, "npc",collisionGrid, npcDialog, true);
			}
		}

		// Create a tree and add some to the collisionGrid
		//tree = new StaticObject(null, "tree", collisionGrid, 4, 25, false, this);
		//StaticObject fastTree = new StaticObject(null,"tree", collisionGrid, 4, 4, false, this);
		//fastTree.setDialog(new Dialog(new String[] {"It's really windy right here.\nIn this exact spot."}, this));
		//collisionGrid.addElement(new GridCoordinate(10, 10), fastTree);
		//		for (int index = 0; index < 8; index++)
		//		{
		//			collisionGrid.addElement(new GridCoordinate(5, index), tree);
		//			collisionGrid.addElement(new GridCoordinate(7, index), tree);
		//		}

		// Create dialogs
		//			Dialog npcDialog = new Dialog(new String[] { "Hello, I am an NPC!",
		//			"This is a new Page!" });
		//			npcDialog
		//			.setNextDialog(new Dialog(
		//					new String[] { "This shows how dialogs can be stringed\ntogether like linked lists." }));
		//
		//			ArrayList<String> choices = new ArrayList<String>();
		//			choices.add("The first one");
		//			choices.add("The second one");
		//			choices.add("The third one");
		//			choices.add("The fourth one");
		//			choices.add("The fifth one");
		//			choices.add("The sixth one");
		//			choices.add("The seventh one");
		//
		//			ArrayList<Dialog> nextDialogs = new ArrayList<Dialog>();
		//			nextDialogs.add(new Dialog(
		//					new String[] { "You selected  the first choice" }));
		//			nextDialogs.add(new Dialog(
		//					new String[] { "You selected  the second choice" }));
		//			nextDialogs.add(new Dialog(new String[] { "The third choice" }));
		//			nextDialogs.add(new Dialog(new String[] { "The fourth choice" }));
		//			nextDialogs.add(new Dialog(new String[] { "The fifth choice" }));
		//			nextDialogs.add(new Dialog(new String[] { "The sixth choice" }));
		//			nextDialogs.add(new Dialog(new String[] { "The last choice" }));
		//
		//			BranchingDialog branchDialog = new BranchingDialog(
		//					new String[] {
		//							"Here is a branching Dialog",
		//					"You will be presented with choices\nUse the arrows to choose\nAnd press space to select" }, choices, nextDialogs);
		//
		//
		//
		//			// Create npcs
		//			//		testNPC = new NonPlayerCharacter(new GridCoordinate(8+xOffset, 9+yOffset), 2, 1, "npc",
		//			//				collisionGrid, npcDialog, true);
		//			testNPC2 = new NonPlayerCharacter(new GridCoordinate(11+xOffset, 7+yOffset), 0, 1,
		//					"npc", collisionGrid, branchDialog, true);
		//
		//			// Create the player character
		testCharacter = new PlayerCharacter(new GridCoordinate(10+xOffset, 4+yOffset),
				Character.DIRECTION_RIGHT, 8, "player", collisionGrid, true);

		// Create the camera
		camera = new Camera(new GridCoordinate(0, 0), testCharacter);

		// This dialog shows at startup
		TestDialog = new Dialog(
				new String[] { "Hello, World!\nUse arrow keys to move\nPress [Space] To Continue." });
		TestDialog.showDialog();

		// Create the level
		level = new Level(collisionGrid, sceneryGrid, camera,this);
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

	public void LoadMap(String mapName, int charX, int charY, CollisionGrid cGrid, SceneryGrid sGrid)
	{

	}


}
