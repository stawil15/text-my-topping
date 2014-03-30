package ideasPackage;

import processing.core.PImage;

public class FakeGameWinDialog extends Dialog
{
	
	// This class creates a realistic looking end game screen
	// but it is in fact fake.

	private PImage gameWinImage;
	private Credits credits;
	public FakeGameWinDialog(String[] lines)
	{
		super(lines);
		gameWinImage = Main.getMainObject().loadImage("/data/sprites/FakeGameWin.png");
		credits = new Credits();
	}

	// Show the dialog
		@Override
		public void showDialog()
		{
			MusicManager.playSongOnce("ending.mp3");
			super.showDialog();
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
			else
			{
				MusicManager.loopSong("OnettTheme.mp3");
				if (nextDialog!=null)
				{
					nextDialog.showDialog();
				}
				else
				{
					GUISystem.showDialog(null);
				}
			}
		}
}
