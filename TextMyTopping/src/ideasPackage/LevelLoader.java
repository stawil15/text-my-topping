package ideasPackage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

import processing.core.PApplet;

public class LevelLoader
{
	private final static String LEVEL_LIST_LOCATION="data\\level\\levellist.txt";
	private Hashtable<String, Level> levels;
	private Hashtable<String, PlayerCharacter> playerCharacters;
	private Hashtable<String, NonPlayerCharacter> nonPlayerCharacters;
	private Hashtable<String, StaticObject> staticObjects;
	private Hashtable<String, SceneryObject> sceneryObjects;
	private Hashtable<String, Dialog> dialogues;
	private PApplet parent;
	
	public LevelLoader(PApplet parent)
	{
		this.parent = parent;
		levels = new Hashtable<String, Level>();
		playerCharacters = new Hashtable<String, PlayerCharacter>();
		nonPlayerCharacters = new Hashtable<String, NonPlayerCharacter>();
		staticObjects = new Hashtable<String, StaticObject>();
		sceneryObjects = new Hashtable<String, SceneryObject>();
		dialogues = new Hashtable<String, Dialog>();
		loadLevels(getLevelNames());
	}
	
	public ArrayList<String> getLevelNames()
	{
		String levels = getFileAsString(LEVEL_LIST_LOCATION);
		Scanner levelScanner = new Scanner(levels);
		ArrayList<String> levelNames = new ArrayList<String>();
		while (levelScanner.hasNext())
		{
			levelNames.add(levelScanner.nextLine());
		}
		
		levelScanner.close();
		
		return levelNames;
	}
	
	public void loadLevels(ArrayList<String> levelNames)
	{
		
	}
	
	public CollisionGrid getCollisionGridFromText(String text, Hashtable<String,Collidable> collidableDictionary, int xEntities, int yEntities)
	{
		CollisionGrid collisionGrid = new CollisionGrid(xEntities, yEntities);
		
		
		return collisionGrid;
	}
	
	public SceneryGrid getSceneryGridFromText(String text, Hashtable<String,SceneryObject> sceneryDictionary, int xEntities, int yEntities)
	{
		SceneryGrid sceneryGrid = new SceneryGrid(xEntities, yEntities);
		return sceneryGrid;
	}
	public void loadLevel(String name)
	{
		int xEntities = 0;
		int yEntities = 0;
		Hashtable<String, Collidable> collidableDictionary = new Hashtable<String,Collidable>();
		Hashtable<String, SceneryObject> sceneryDictionary = new Hashtable<String,SceneryObject>();
		String levelText = getFileAsString("data\\level\\" + name + ".txt");
		CollisionGrid collisionGrid = getCollisionGridFromText(levelText, collidableDictionary, xEntities, yEntities);
		SceneryGrid sceneryGrid = getSceneryGridFromText(levelText, sceneryDictionary, xEntities, yEntities);
		levels.put(name, new Level(collisionGrid, sceneryGrid, null, parent));
	}
	
	public Level getLevel(String name)
	{
		return levels.get(name);
	}
	
	public Level getCurrentLevel()
	{
		return null;
	}
	
	private String getFileAsString(String path)
	{
		try
		{
			byte[] fileAsBytes = Files.readAllBytes(Paths.get(path));
			return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(fileAsBytes)).toString();
		}
		catch (IOException ioEx)
		{
			return null;
		}
	}
}
