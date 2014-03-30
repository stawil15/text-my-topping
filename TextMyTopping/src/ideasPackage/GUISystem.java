package ideasPackage;

import ddf.minim.Minim;
import ddf.minim.AudioPlayer;

// This class shows the dialogs and plays the dialog blips that are heard.
public class GUISystem
{
	private static Dialog currentDialog;
	private static AudioPlayer audioPlayer;
	private static Minim minim;
	private final static int FRAMES_BETWEEN_SOUND = 2;
	private static int framesNotPlayed = 0;
	private static boolean doingTransition = false;
	
	// Load in our file for the textbloop
	public static void initialize()
	{
		minim = new Minim(Main.getMainObject());
		audioPlayer = minim.loadFile("data/audio/textbloop.mp3");
	}

	// Return whether we should allow the player to move
	public static boolean allowMovement()
	{
		return (currentDialog == null && !doingTransition);
	}

	// draw the current dialog if there is one
	public static void  draw()
	{
		if (currentDialog != null)
		{
			currentDialog.drawDialog();
		}
	}

	// Set the current dialog
	public static void showDialog(Dialog d)
	{
		currentDialog = d;
	}

	// advance the current dialog text
	public static void advancedCurrentDialog()
	{
		if (showingDialog())
		{
			currentDialog.advanceText();
		}
	}
	
	// Returns whether a dialog is being shown
	public static boolean showingDialog()
	{
		return (currentDialog != null);
	}

	// Plays the bloop
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
	
	// Set whether a transition is being done or not
	public static void setDoingTransition(boolean state)
	{
		doingTransition = state;
	}
	
	// return whether a transition is being done or not
	public static boolean doingTransition()
	{
		return doingTransition;
	}

}
