package ideasPackage;

import java.util.Hashtable;

public class LevelManager
{
	private static Hashtable<String, Level> levelTable;
	private static Level activeLevel;
	private static int charX, charY;
	
	
	public static void initializeLevelManager(int charX, int charY)
	{
		levelTable = new Hashtable<String, Level>();
		LevelManager.charX = charX;
		LevelManager.charY = charY;
	}
	
	
	public static Level getLevel(String levelId)
	{
		if (levelTable.containsKey(levelId))
		{
			return levelTable.get(levelId);
		}
		else
		{
			addLevel(new LevelCreator().createFromCsv(levelId), levelId);
			return getLevel(levelId);
		}
	}
	
	public static void addLevel(Level level, String levelID)
	{
		levelTable.put(levelID, level);
	}

	
	public static void setActiveLevel(String levelId, String fromLevel)
	{
		
		boolean addMainCharacterForFirstTime = false;
		if (activeLevel!=null)
		{
			activeLevel.getCollisionGrid().removePlayerFromGrid();
			
		}
		else
		{
			addMainCharacterForFirstTime = true;
		}
		
		activeLevel = (getLevel(levelId));
		
		if (addMainCharacterForFirstTime)
		{
			Main.setPlayer(new PlayerCharacter(new GridCoordinate(charX, charY),
					Character.DIRECTION_RIGHT, 8, "player", activeLevel.getCollisionGrid(), true));
			Camera camera = new Camera(Main.getPlayer());
			Main.setCamera(camera);
			activeLevel.setCamera(camera);
		}
		else
		{
			activeLevel.getCollisionGrid().setPlayerAtDoorFromLevel(fromLevel);
			activeLevel.setCamera(Main.getCamera());
		}
	}
	
	public static void drawActiveLevel()
	{
		activeLevel.draw();
	}
	

}
