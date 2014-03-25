package ideasPackage;

public class LevelCreator
{
	private SceneryObject grass, flower, sand, dflower, snow, sflower, woodFloor;
	private StaticObject tree, fastTree, invisibleWall, cactus, fastCactus, snowTree, fastSnowTree, cabin, woodBlock;

	public LevelCreator()
	{

	}

	public Level createFromCsv(String levelName)
	{

		readCSV maploader = new readCSV();
		int[][] map = maploader.readMapData(levelName);
		maploader.readDialogueData(levelName);
		CollisionGrid collisionGrid = new CollisionGrid(map.length, map[0].length);
		SceneryGrid sceneryGrid = new SceneryGrid(map.length, map[0].length);

		grass = new SceneryObject(null, "forest\\grass", 1, 20, sceneryGrid, false);
		flower = new SceneryObject(null, "forest\\flower", 2, 20, sceneryGrid, false);
		tree = new StaticObject(null, "forest\\tree", collisionGrid, 4, 40, false);
		fastTree = new StaticObject(null, "forest\\tree", collisionGrid, 4, 4, false);
		fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
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

	public void addGameObject(int id, CollisionGrid collisionGrid, SceneryGrid sceneryGrid, int x, int y, int xOffset, int yOffset)
	{
		GridCoordinate position = new GridCoordinate(x + xOffset, y+yOffset);
		if (id == 9)
		{
			collisionGrid.addElement(position, invisibleWall);
			sceneryGrid.addSceneryObject(position, grass);
		}
		else if (id == 1)
		{
			sceneryGrid.addSceneryObject(position, grass);
			collisionGrid.addElement(position, tree);
		}
		else if (id == 2)
		{
			sceneryGrid.addSceneryObject(position, flower);
		}
		else if (id == 3)
		{
			sceneryGrid.addSceneryObject(position, grass);
			collisionGrid.addElement(position, fastTree);

		}
		else if (id == 5)
		{
			new Door(position, collisionGrid, "redSpriteMap.csv", "megaSpriteMap.csv", Character.DIRECTION_LEFT, false);
			sceneryGrid.addSceneryObject(position, grass);
		}
		else if (id == 6)
		{
			new Door(position, collisionGrid, "megaSpriteMap.csv", "redSpriteMap.csv", Character.DIRECTION_RIGHT, false);
			sceneryGrid.addSceneryObject(position, grass);
		}
		else if (id == 7)
		{
			collisionGrid.addElement(position, cabin);
			sceneryGrid.addSceneryObject(position, grass);
		}
		else if (id == 8)
		{
			new Door(position, collisionGrid, "megaSpriteMap.csv","cabin.csv", Character.DIRECTION_DOWN, "talkedToNPCInCabin", false, true, new Dialog(new String[] {"The door is locked."}));
		}
		else if (id == 11) // start desert
		{
			sceneryGrid.addSceneryObject(position, sand);
			collisionGrid.addElement(position, cactus);
		}
		else if (id == 12)
		{
			sceneryGrid.addSceneryObject(position, dflower);
		}
		else if (id == 13)
		{
			sceneryGrid.addSceneryObject(position, sand);
			collisionGrid.addElement(position, fastCactus);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
		}
		else if (id == 21) // start snow
		{
			sceneryGrid.addSceneryObject(position, snow);
			collisionGrid.addElement(position, snowTree);
		}
		else if (id == 22)
		{
			sceneryGrid.addSceneryObject(position, sflower);
		}
		else if (id == 23)
		{
			sceneryGrid.addSceneryObject(position, snow);
			collisionGrid.addElement(position, fastSnowTree);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
		}
		else if (id == 20)
		{
			sceneryGrid.addSceneryObject(position, sand);
			collisionGrid.addElement(position, fastCactus);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
		}
		else if (id == 4)
		{
			new MoveableObject(position, 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(position, grass);
		}
		else if (id == 30)
		{
			sceneryGrid.addSceneryObject(position, woodFloor);
		}
		else if (id == 31)
		{
			new Door(new GridCoordinate(x+xOffset, y+yOffset), collisionGrid, "cabin.csv","megaSpriteMap.csv", Character.DIRECTION_UP, true);
			sceneryGrid.addSceneryObject(position, woodFloor);
		}
		else if (id == 32)
		{
			collisionGrid.addElement(position, woodBlock);

		}
		else
		{
			sceneryGrid.addSceneryObject(position, grass);
		}
	}

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
				if (NPC[x][0].equals("NPC"))
				{
					Dialog npcDialog = null;
					if (NPC[x][4] != null && !NPC[x][4].equals(readCSV.NULL_DIALOG))
					{
						// Creates NPC dialog
						npcDialog = DialogManager.getDialog(NPC[x][4]);
					}
					// Places NPC
					NonPlayerCharacter testNPC = new NonPlayerCharacter(new GridCoordinate(Integer.parseInt(NPC[x][1]) + xOffset, Integer.parseInt(NPC[x][2])
							+ yOffset), 2, 1, "npc", collisionGrid, npcDialog, true);
				}
			}
		}
	}
}
