package ideasPackage;

import java.util.Hashtable;

import processing.core.PImage;

public class LevelManager
{
	// This class manages levels and transitions between levels
	
	private static Hashtable<String, Level> levelTable;
	private static Level activeLevel;
	private static int charX, charY, direction = -1;
	private static PImage transitionImageCurrentMap, transitionImageNextMap;
	private static float transitionX = 0, transitionY = 0;
	private static float transitionSpeed = 20f;
	private static float fadeTimer = 0;
	private final static int FRAMES_TO_FADE = 30;

	// Initialize our level manager with an initial starting point of the character
	public static void initializeLevelManager(int charX, int charY)
	{
		levelTable = new Hashtable<String, Level>();
		LevelManager.charX = charX;
		LevelManager.charY = charY;
		transitionImageCurrentMap = new PImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		transitionImageNextMap = new PImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
	}

	// Get a level from a string
	public static Level getLevel(String levelId)
	{
		if (levelTable.containsKey(levelId))
		{
			return levelTable.get(levelId);
		} else
		{
			addLevel(new LevelCreator().createFromCsv(levelId), levelId);
			return getLevel(levelId);
		}
	}

	// add a level with a certain string
	public static void addLevel(Level level, String levelID)
	{
		levelTable.put(levelID, level);
	}

	// Set the active level and get the transition images
	public static void setActiveLevel(String levelId, String fromLevel, int direction)
	{
		LevelManager.direction = direction;
		boolean addMainCharacterForFirstTime = false;

		GUISystem.setDoingTransition(true);
		if (activeLevel != null)
		{
			activeLevel.getCollisionGrid().removePlayerFromGrid();
			activeLevel.draw();
		}

		int[] mainPixels = Main.getPixels();
		for (int i = 0; i < transitionImageCurrentMap.pixels.length; i++)
		{
			transitionImageCurrentMap.pixels[i] = mainPixels[i];
		}
		transitionImageCurrentMap.updatePixels();

		if (activeLevel == null)
		{
			addMainCharacterForFirstTime = true;
		}

		activeLevel = (getLevel(levelId));

		if (addMainCharacterForFirstTime)
		{
			Main.setPlayer(new PlayerCharacter(new GridCoordinate(charX, charY), Character.DIRECTION_RIGHT, 8,
					"player", activeLevel.getCollisionGrid(), true));
			Camera camera = new Camera(Main.getPlayer());
			Main.setCamera(camera);
			activeLevel.setCamera(camera);
			activeLevel.getCollisionGrid().resetCollisionGrid();
			activeLevel.draw();
		} else
		{
			activeLevel.getCollisionGrid().setPlayerAtDoorFromLevel(fromLevel);
			activeLevel.setCamera(Main.getCamera());
			activeLevel.getCollisionGrid().removePlayerFromGrid();
			activeLevel.getCollisionGrid().resetCollisionGrid();
			activeLevel.draw();
			activeLevel.getCollisionGrid().setPlayerAtDoorFromLevel(fromLevel);
		}
		mainPixels = Main.getPixels();
		Main.getMainObject().image(transitionImageCurrentMap, 0, 0);
		for (int i = 0; i < transitionImageNextMap.pixels.length; i++)
		{
			transitionImageNextMap.pixels[i] = mainPixels[i];
		}

		transitionImageNextMap.updatePixels();

		MusicManager.playSong(levelId);
	}

	// Draws the active level, and transitions if there is a transition going on. 
	public static void drawActiveLevel()
	{
		if (activeLevel != null)
		{
			activeLevel.draw();
		}

		if (GUISystem.doingTransition())
		{
			// System.out.println("X: " + transitionX + " Y:" + transitionY);
			switch (direction)
			{
			case Character.DIRECTION_DOWN:
				drawTransitionImages(transitionX, transitionY, transitionX, transitionY - Main.SCREEN_HEIGHT);
				transitionY += transitionSpeed * Main.getTimeMultiplier();
				if (transitionY >= Main.SCREEN_HEIGHT)
				{
					GUISystem.setDoingTransition(false);
					transitionX = 0;
					transitionY = 0;

				}
				break;
			case Character.DIRECTION_LEFT:
				drawTransitionImages(transitionX, transitionY, transitionX + Main.SCREEN_WIDTH, transitionY);
				transitionX -= transitionSpeed * Main.getTimeMultiplier();
				if (transitionX <= -Main.SCREEN_WIDTH)
				{
					GUISystem.setDoingTransition(false);
					transitionX = 0;
					transitionY = 0;
				}

				break;
			case Character.DIRECTION_UP:
				drawTransitionImages(transitionX, transitionY, transitionX, transitionY + Main.SCREEN_HEIGHT);
				transitionY -= transitionSpeed * Main.getTimeMultiplier();
				if (transitionY <= -Main.SCREEN_HEIGHT)
				{
					GUISystem.setDoingTransition(false);
					transitionX = 0;
					transitionY = 0;
				}
				break;
			case Character.DIRECTION_RIGHT:
				drawTransitionImages(transitionX, transitionY, transitionX - Main.SCREEN_WIDTH, transitionY);
				transitionX += transitionSpeed * Main.getTimeMultiplier();
				if (transitionX >= Main.SCREEN_WIDTH)
				{
					GUISystem.setDoingTransition(false);
					transitionX = 0;
					transitionY = 0;
				}
				break;
			case Door.FADE_TRANSITION:
				fadeTimer += Main.getTimeMultiplier();
				Main.getMainObject().background(0);
				if (fadeTimer < FRAMES_TO_FADE / 2)
				{
					Main.getMainObject().tint(255, 255 - 255.0f * fadeTimer / (FRAMES_TO_FADE / 2));
					Main.getMainObject().image(transitionImageCurrentMap, 0, 0);
				} else
				{
					Main.getMainObject().tint(255, 510.0f * (fadeTimer) / (FRAMES_TO_FADE) - 255);
					Main.getMainObject().image(transitionImageNextMap, 0, 0);
				}
				if (fadeTimer > FRAMES_TO_FADE)
				{
					Main.getMainObject().tint(255, 255);
					fadeTimer = 0;
					GUISystem.setDoingTransition(false);
				}
				break;
			}

		}
	}

	// Draws the two transition images
	private static void drawTransitionImages(float currentMapX, float currentMapY, float nextMapX, float nextMapY)
	{
		Main.getMainObject().image(transitionImageCurrentMap, currentMapX, currentMapY);
		Main.getMainObject().image(transitionImageNextMap, nextMapX, nextMapY);
	}

}
