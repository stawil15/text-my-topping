package ideasPackage;

import processing.core.PImage;

/*
 * This class shows the game win scene where the credits are rolled and happy
 * music plays
 */
public class GameWinDialog extends Dialog
{
	protected PImage gameWinImage;
	private Credits credits;
	public GameWinDialog(String[] lines)
	{
		super(lines);
		nextDialog = this;
		gameWinImage = Main.getMainObject().loadImage("/data/sprites/GameWin.png");
		credits = new Credits();
	}


	
	// Show the dialog
	@Override
	public void showDialog()
	{
		super.showDialog();
		MusicManager.playSongOnce("ending.mp3");
	}
	
	// Draw the dialog
	@Override
	public void drawDialog()
	{
		Main.getMainObject().image(gameWinImage, 0, 0);
		super.drawDialog();
		credits.draw();
	}
	
	// Advance the text
	@Override
	public void advanceText()
	{
		if (!finishedDisplayingText)
		{
			currentPositionInText += impatientPersonCharactersToAdvance;
		}
	}

}
