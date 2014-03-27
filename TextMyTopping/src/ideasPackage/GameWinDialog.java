package ideasPackage;

import processing.core.PImage;

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


	
	@Override
	public void showDialog()
	{
		super.showDialog();
		MusicManager.playSongOnce("ending.mp3");
	}
	@Override
	public void drawDialog()
	{
		Main.getMainObject().image(gameWinImage, 0, 0);
		super.drawDialog();
		credits.draw();
	}
	
	@Override
	public void advanceText()
	{
		if (!finishedDisplayingText)
		{
			currentPositionInText += impatientPersonCharactersToAdvance;
		}
	}

}
