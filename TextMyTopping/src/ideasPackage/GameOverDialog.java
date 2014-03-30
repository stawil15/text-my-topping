package ideasPackage;

import processing.core.PImage;

public class GameOverDialog extends Dialog
{

	// This dialog is shown when the player looses. Sad music plays and an image is overlayed
	// on the top of the screen. 
	protected PImage gameOverImage;
	public GameOverDialog(String[] lines)
	{
		super(lines);
		gameOverImage = Main.getMainObject().loadImage("/data/sprites/GameOver.png");
		nextDialog = this;
	}
	
	// Show the dialog
	@Override
	public void showDialog()
	{
		super.showDialog();
		MusicManager.playSongOnce("Gameover.mp3");
	}
	
	// Draw the dialog
	@Override
	public void drawDialog()
	{
		Main.getMainObject().image(gameOverImage, 0, 0);
		super.drawDialog();
	}
	
	// Advance the text if the user wants to
	@Override
	public void advanceText()
	{
		if (!finishedDisplayingText)
		{
			currentPositionInText += impatientPersonCharactersToAdvance;
		}
	}

}
