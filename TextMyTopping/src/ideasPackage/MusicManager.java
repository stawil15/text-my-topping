package ideasPackage;

import java.awt.print.Paper;
import java.util.Hashtable;

import processing.core.PApplet;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;

public class MusicManager
{
	private static Hashtable<String, String> songs;
	private static Minim minim;
	private static AudioPlayer player;
	private static String currentSong;
	private final static int FRAMES_TO_FADE = 120;
	private final static int SMALLEST_GAIN = -40;
	private static int fadeTimer = FRAMES_TO_FADE;
	private static String newLevel = "";

	public static void initialize()
	{
		songs = new Hashtable<String, String>();
	}

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
		}
		else
		{
			currentSong = songs.get(levelId);
			minim = new Minim(Main.getMainObject());
			player = minim.loadFile("data/audio/bgm/" + songs.get(levelId));
			player.loop();

		}

	}

	public static void addSong(String levelId, String songName)
	{
		songs.put(levelId, songName);
	}

	public static void update()
	{

		boolean doSwitch = false;
		if (fadeTimer < FRAMES_TO_FADE)
		{
			fadeTimer++;
			player.setGain((SMALLEST_GAIN*1.0f)/(FRAMES_TO_FADE)*fadeTimer);
			if (fadeTimer == FRAMES_TO_FADE)
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
	
	public static void playSongOnce(String song)
	{
		player.close();
		player = minim.loadFile("data/audio/bgm/" + song);
		player.play();
	}
}
