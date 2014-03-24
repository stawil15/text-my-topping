package ideasPackage;

public class LevelCreator
{
	private SceneryObject grass, flower, sand, dflower, snow, sflower;
	private StaticObject tree, fastTree, invisibleWall, cactus, fastCactus, snowTree, fastSnowTree;

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
		invisibleWall = new StaticObject(null, collisionGrid, false);
		cactus = new StaticObject(null, "desert\\tree", collisionGrid, 4, 40, false);
		sand = new SceneryObject(null, "desert\\grass", 1, 20, sceneryGrid, false);
		dflower = new SceneryObject(null, "desert\\flower", 2, 20, sceneryGrid, false);
		snowTree = new StaticObject(null, "snow\\tree", collisionGrid, 4, 40, false);
		snow = new SceneryObject(null, "snow\\grass", 1, 20, sceneryGrid, false);
		sflower = new SceneryObject(null, "snow\\flower", 2, 20, sceneryGrid, false);

		int gridWidth = Main.SCREEN_WIDTH / Main.GRID_SIZE;
		int gridHeight = Main.SCREEN_HEIGHT / Main.GRID_SIZE;

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
		if (id == 9)
		{
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), invisibleWall);
		}
		else if (id == 1)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), grass);
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), tree);
		}
		else if (id == 2)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), flower);
		}
		else if (id == 3)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), grass);
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), fastTree);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));

		}
		else if (id == 5)
		{
			new Door(new GridCoordinate(x + xOffset, y + yOffset), collisionGrid, "redSpriteMap.csv", "megaSpriteMap.csv", Character.DIRECTION_LEFT);
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y+yOffset), grass);
		}
		else if (id == 6)
		{
			new Door(new GridCoordinate(x + xOffset, y + yOffset), collisionGrid, "megaSpriteMap.csv", "redSpriteMap.csv", Character.DIRECTION_RIGHT);
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y+yOffset), grass);
		}
		else if (id == 11) // start desert
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), sand);
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), cactus);
		}
		else if (id == 12)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), dflower);
		}
		else if (id == 13)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), sand);
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), fastCactus);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
		}
		else if (id == 21) // start snow
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), snow);
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), snowTree);
		}
		else if (id == 22)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), sflower);
		}
		else if (id == 23)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), snow);
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), fastSnowTree);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
		}
		else if (id == 20)
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), sand);
			collisionGrid.addElement(new GridCoordinate(x + xOffset, y + yOffset), fastCactus);
			fastTree.setDialog(new Dialog(new String[] { "It's really windy right here.\nIn this exact spot." }));
		}
		else if (id == 4)
		{
			new MoveableObject(new GridCoordinate(x + xOffset, y + yOffset), 0, 1, "rock", collisionGrid, true);
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), grass);
		}
		else
		{
			sceneryGrid.addSceneryObject(new GridCoordinate(x + xOffset, y + yOffset), grass);
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
