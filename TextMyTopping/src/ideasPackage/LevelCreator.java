package ideasPackage;

import java.util.ArrayList;

public class LevelCreator
{
	// sets all the scenery for the game for the three different biomes the are
	// in the game
	private SceneryObject grass, flower, sand, dflower, snow, sflower, woodFloor, road;
	private StaticObject tree, fastTree, invisibleWall, cactus, fastCactus, snowTree, fastSnowTree, cabin, woodBlock,
			intenseTree, lamp, sign, buildings;
	private static BooleanDialog destroyTree;

	// allows player to get an axe to chop down certain trees
	public static void initialize()
	{
		destroyTree = new BooleanDialog("hasWindAxe");
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("Yes.");
		choices.add("No.");
		ArrayList<Dialog> nextDialogs = new ArrayList<Dialog>();
		DestroyCollidableDialog reallyDestroyTree = new DestroyCollidableDialog();
		reallyDestroyTree.setNextDialog(new Dialog(new String[] { "You used the axe of the wind!" }));
		nextDialogs.add(reallyDestroyTree);
		nextDialogs.add(new Dialog(new String[] { "You put the axe away." }));
		BranchingDialog askToDestroyTree = new BranchingDialog(
				new String[] { "Would you like to use the axe of the wind?" }, choices, nextDialogs);
		destroyTree.setTrueDialog(askToDestroyTree);
		destroyTree.setFalseDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
	}

	public LevelCreator()
	{

	}

	// starts to read from a .csv file to load new maps
	// also sets up all scenery with an ID for calling the images from the
	// correct folder
	public Level createFromCsv(String levelName)
	{

		readCSV maploader = new readCSV();
		int[][] map = maploader.readMapData(levelName);
		maploader.readDialogueData(levelName);
		SceneryGrid sceneryGrid = new SceneryGrid(map.length, map[0].length);
		CollisionGrid collisionGrid = new CollisionGrid(map.length, map[0].length, sceneryGrid);

		grass = new SceneryObject(null, "forest\\grass", 1, 20, sceneryGrid, false);
		flower = new SceneryObject(null, "forest\\flower", 2, 20, sceneryGrid, false);
		tree = new StaticObject(null, "forest\\tree", collisionGrid, 4, 40, false);
		fastTree = new StaticObject(null, "forest\\tree", collisionGrid, 4, 4, false);
		fastTree.setDialog(destroyTree);
		intenseTree = new StaticObject(null, "forest\\tree", collisionGrid, 4, 4, false);
		invisibleWall = new StaticObject(null, collisionGrid, false);
		cactus = new StaticObject(null, "desert\\tree", collisionGrid, 4, 40, false);
		sand = new SceneryObject(null, "desert\\grass", 1, 20, sceneryGrid, false);
		dflower = new SceneryObject(null, "desert\\flower", 2, 20, sceneryGrid, false);
		snowTree = new StaticObject(null, "snow\\tree", collisionGrid, 4, 40, false);
		snow = new SceneryObject(null, "snow\\grass", 1, 20, sceneryGrid, false);
		sflower = new SceneryObject(null, "snow\\flower", 2, 20, sceneryGrid, false);
		cabin = new StaticObject(null, "forest\\cabin", collisionGrid, 1, 20, false);
		woodFloor = new SceneryObject(null, "forest\\woodFloor", 1, 20, sceneryGrid, false);
		woodBlock = new StaticObject(null, "forest\\woodBlock", collisionGrid, 1, 20, false);
		road = new SceneryObject(null, "city\\road", 1, 20, sceneryGrid, false);
		lamp = new StaticObject(null, "city\\lamp", collisionGrid, 1, 20, false);
		buildings = new StaticObject(null, "city\\buildings", collisionGrid, 1, 20, false);
		sign = new StaticObject(null, "city\\sign", collisionGrid, 1, 20, false);

		for (int x = 0; x < map.length; x++)
		{
			for (int y = 0; y < map[0].length; y++)
			{
				addGameObject(map[x][y], collisionGrid, sceneryGrid, x, y, 0, 0);
			}
		}

		Level createdLevel = new Level(collisionGrid, sceneryGrid, Main.getCamera(), Main.getMainObject());
		addNPCs(maploader, levelName, 0, 0, collisionGrid);

		return createdLevel;
	}

	// uses id to put the correct object in the correct position
	public void addGameObject(int id, CollisionGrid collisionGrid, SceneryGrid sceneryGrid, int x, int y, int xOffset,
			int yOffset)
	{
		GridCoordinate position = new GridCoordinate(x + xOffset, y + yOffset);

		if (id == 1)
		{
			sceneryGrid.addSceneryObject(position, grass);
			collisionGrid.addEntity(position, tree);
		} else if (id == 2)
		{
			sceneryGrid.addSceneryObject(position, flower);
		} else if (id == 3)
		{
			sceneryGrid.addSceneryObject(position, grass);
			collisionGrid.addEntity(position, fastTree);

		} else if (id == 4)
		{
			new MoveableObject(position, 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 5)
		{
			new Door(position, collisionGrid, "redSpriteMap.csv", "megaSpriteMap.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 6)
		{
			new Door(position, collisionGrid, "megaSpriteMap.csv", "redSpriteMap.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 7)
		{
			collisionGrid.addEntity(position, cabin);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 8)
		{
			new Door(position, collisionGrid, "megaSpriteMap.csv", "cabin.csv", Character.DIRECTION_DOWN,
					"talkedToNPCInCabin", false, true, new Dialog(new String[] { "The door is locked." }));
		} else if (id == 9)
		{
			collisionGrid.addEntity(position, invisibleWall);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 10)
		{
			sceneryGrid.addSceneryObject(position, grass);
			collisionGrid.addEntity(position, intenseTree);
			intenseTree.setDialog(new Dialog(new String[] { "[Tree intensifies]" }));
		}

		else if (id == 11) // start desert
		{
			sceneryGrid.addSceneryObject(position, sand);
			collisionGrid.addEntity(position, cactus);
		} else if (id == 12)
		{
			sceneryGrid.addSceneryObject(position, dflower);
		} else if (id == 13)
		{
			sceneryGrid.addSceneryObject(position, sand);
			collisionGrid.addEntity(position, fastCactus);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
		} else if (id == 14)
		{
			new MoveableObject(position, 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(position, sand);
		} else if (id == 15)
		{
			new Door(position, collisionGrid, "redSpriteMap.csv", "rockPuzzel.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 16)
		{
			new Door(position, collisionGrid, "rockPuzzel.csv", "redSpriteMap.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 17)
		{
			new Door(position, collisionGrid, "rockPuzzel.csv", "forestpassage.csv", Character.DIRECTION_DOWN, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 18)
		{
			new Door(position, collisionGrid, "forestpassage.csv", "rockPuzzel.csv", Character.DIRECTION_UP, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 19)
		{
			new Door(position, collisionGrid, "forestpassage.csv", "puzzleOne.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 20)
		{
			sceneryGrid.addSceneryObject(position, sand);
			collisionGrid.addEntity(position, fastCactus);
			fastTree.setDialog(destroyTree);
		} else if (id == 21) // start snow
		{
			sceneryGrid.addSceneryObject(position, snow);
			collisionGrid.addEntity(position, snowTree);
		} else if (id == 22)
		{
			sceneryGrid.addSceneryObject(position, sflower);
		} else if (id == 23)
		{
			sceneryGrid.addSceneryObject(position, snow);
			collisionGrid.addEntity(position, fastSnowTree);
			fastTree.setDialog(destroyTree);
		} else if (id == 24)
		{
			new MoveableObject(position, 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(position, snow);
		} else if (id == 25)
		{
			sceneryGrid.addSceneryObject(position, sand);
		}
		else if (id == 26)
		{
			new PushableOnlyObject(position, 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(position, grass);
		}
		else if (id == 27)
		{
			new PushableOnlyObject(position, 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(position, sand);
		}
		else if (id == 28)
		{
			new PushableOnlyObject(position, 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(position, snow);
		}
		else if (id == 30)
		{
			sceneryGrid.addSceneryObject(position, woodFloor);
		} else if (id == 31)
		{
			new Door(new GridCoordinate(x + xOffset, y + yOffset), collisionGrid, "cabin.csv", "megaSpriteMap.csv",
					Character.DIRECTION_UP, true);
			sceneryGrid.addSceneryObject(position, woodFloor);
		} else if (id == 32)
		{
			collisionGrid.addEntity(position, woodBlock);

		} 
		else if (id == 33)
		{
			sceneryGrid.addSceneryObject(position, grass);
			collisionGrid.addEntity(position, sign);
			sign.setDialog(new Dialog(new String[] { "GNOMES MOVE THE ROCKS BACK WHEN \nYOU AREN'T LOOKING" }));
		}
		else if (id == 34)
		{
			sceneryGrid.addSceneryObject(position, sand);
			collisionGrid.addEntity(position, sign);
			sign.setDialog(new Dialog(new String[] { "Welcome to Cleveland." }));
			
		} 
		else if (id == 35)
		{
			sceneryGrid.addSceneryObject(position, snow);
			collisionGrid.addEntity(position, sign);
		} 
		else if (id == 40) // start city
		{
			sceneryGrid.addSceneryObject(position, road);
		} else if (id == 41)
		{
			sceneryGrid.addSceneryObject(position, road);
			collisionGrid.addEntity(position, lamp);
		} else if (id == 42)
		{
			collisionGrid.addEntity(position, buildings);
		} else if (id == 43)
		{
			collisionGrid.addEntity(position, invisibleWall);
			sceneryGrid.addSceneryObject(position, road);
		} else if (id == 44)
		{
			sceneryGrid.addSceneryObject(position, road);
			collisionGrid.addEntity(position, sign);
		} 
		else if (id == 50)
		{
			new Door(position, collisionGrid, "puzzleOne.csv", "forestpassage.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 50)
		{
			new Door(position, collisionGrid, "puzzleOne.csv", "forestpassage.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 51)
		{
			new Door(position, collisionGrid, "puzzleOne.csv", "puzzleTwo.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 52)
		{
			new Door(position, collisionGrid, "puzzleTwo.csv", "puzzleOne.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, sand);
		} else if (id == 53)
		{
			new Door(position, collisionGrid, "puzzleTwo.csv", "puzzleThree.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 54)
		{
			new Door(position, collisionGrid, "puzzleThree.csv", "puzzleTwo.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 55)
		{
			new Door(position, collisionGrid, "puzzleThree.csv", "puzzleFour.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 56)
		{
			new Door(position, collisionGrid, "puzzleFour.csv", "puzzleThree.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 57)
		{
			new Door(position, collisionGrid, "puzzleFour.csv", "puzzleFive.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == 58)
		{
			new Door(position, collisionGrid, "puzzleFive.csv", "puzzleFour.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		} else if (id == -1)
		{
			sceneryGrid.addSceneryObject(position, grass);
			new Hole(position, "forest\\hole", collisionGrid, true);
		} else
		{
			sceneryGrid.addSceneryObject(position, grass);
		}
	}

	// gets the NPCs location from a .csv file and adds them to the correct
	// location and gets the dialog
	public void addNPCs(readCSV maploader, String mapName, int xOffset, int yOffset, CollisionGrid collisionGrid)
	{
		String[][] NPC = maploader.readNPCData("NPC" + mapName); // naming
																	// convention
																	// will
																	// allow us
																	// to add
																	// "NPC" to
																	// name
																	// variable.
		for (int x = 0; x < NPC.length; x++)
		{
			if (NPC[x][0] != null)
			{
				if (NPC[x][0] != null)
				{
					Dialog npcDialog = null;
					if (NPC[x][4] != null && !NPC[x][4].equals(readCSV.NULL_DIALOG))
					{
						// Creates NPC dialog
						npcDialog = DialogManager.getDialog(NPC[x][4]);
					}
					// Places NPC
					NonPlayerCharacter testNPC = new NonPlayerCharacter(new GridCoordinate(Integer.parseInt(NPC[x][1])
							+ xOffset, Integer.parseInt(NPC[x][2]) + yOffset), 2, 1, NPC[x][0], collisionGrid,
							npcDialog, true);
				}
			}
		}
	}
}
