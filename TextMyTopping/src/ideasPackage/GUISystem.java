package ideasPackage;

import ddf.minim.Minim;
import ddf.minim.AudioPlayer;

public class GUISystem
{
	private static Dialog currentDialog;

	private static AudioPlayer audioPlayer;
	private static Minim minim;
	private final static int FRAMES_BETWEEN_SOUND = 2;
	private static int framesNotPlayed = 0;
	private static boolean doingTransition = false;
	public static void initialize()

	
	{
		minim = new Minim(Main.getMainObject());
		audioPlayer = minim.loadFile("data/audio/textbloop.mp3");
	}

	public static boolean allowMovement()
	{
		return (currentDialog == null && !doingTransition);
	}

	public static void  draw()
	{
		if (currentDialog != null)
		{
			currentDialog.drawDialog();
		}
	}

	public static void showDialog(Dialog d)
	{
		currentDialog = d;
	}

	public static void advancedCurrentDialog()
	{
		if (showingDialog())
		{
			currentDialog.advanceText();
		}
	}
	
	public static boolean showingDialog()
	{
		return (currentDialog != null);
	}

	public static void playDialogSound()
	{
		framesNotPlayed++;
		if (framesNotPlayed == FRAMES_BETWEEN_SOUND)
		{
			framesNotPlayed = 0;
		}
		
		if (!audioPlayer.isPlaying() && framesNotPlayed == 0)
		{
			audioPlayer.play();
		}
	}
	
	public static void setDoingTransition(boolean state)
	{
		doingTransition = state;
	}
	
	public static boolean doingTransition()
	{
		return doingTransition;
	}

}
