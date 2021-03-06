package ideasPackage;

import java.util.Hashtable;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class MusicManager
{
	// This class manages all the music in the game, and either plays or loops songs
	private static Hashtable<String, String> songs;
	private static Minim minim;
	private static AudioPlayer player;
	private static String currentSong;
	private final static int FRAMES_TO_FADE = 120;
	private final static int SMALLEST_GAIN = -40;
	private static float fadeTimer = FRAMES_TO_FADE;
	private static String newLevel = "";

	// Initialize our hashtable
	public static void initialize()
	{
		songs = new Hashtable<String, String>();
	}

	// Plays the song associated with a levelId
	public static void playSong(String levelId)
	{
		if (player != null && player.isPlaying())
		{
			if (!currentSong.equals(songs.get(levelId)))
			{
				fadeTimer = 0;
				currentSong = songs.get(levelId);
				newLevel = levelId;
			}
		} else
		{
			currentSong = songs.get(levelId);
			minim = new Minim(Main.getMainObject());
			player = minim.loadFile("data/audio/bgm/" + songs.get(levelId));
			player.loop();

		}

	}

	// Add a song to associate with a levelId
	public static void addSong(String levelId, String songName)
	{
		songs.put(levelId, songName);
	}

	// Update is used for fading music when switching levels
	public static void update()
	{

		boolean doSwitch = false;
		if (fadeTimer < FRAMES_TO_FADE)
		{
			fadeTimer += Main.getTimeMultiplier();
			player.setGain((SMALLEST_GAIN * 1.0f) / (FRAMES_TO_FADE) * fadeTimer);
			if (fadeTimer >= FRAMES_TO_FADE)
			{
				doSwitch = true;
			}
		}

		if (doSwitch)
		{

			player.close();
			player = minim.loadFile("data/audio/bgm/" + songs.get(newLevel));
			player.loop();
			doSwitch = false;
			fadeTimer = FRAMES_TO_FADE;
			player.setGain(0);
		}
	}

	// Play a song exactly once. The song is not associated with any level. 
	public static void playSongOnce(String song)
	{
		if (player.isPlaying())
		{
			player.close();
		}
		player = minim.loadFile("data/audio/bgm/" + song);
		player.play();
	}

	// Loop a song that is not associated with a level.
	public static void loopSong(String song)
	{
		if (player.isPlaying())
		{
			player.close();
		}
		player = minim.loadFile("data/audio/bgm/" + song);
		player.loop();
	}
}
