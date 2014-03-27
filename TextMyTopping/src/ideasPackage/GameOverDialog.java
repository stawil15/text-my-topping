package ideasPackage;

import processing.core.PImage;

public class GameOverDialog extends Dialog
{

	protected PImage gameOverImage;
	public GameOverDialog(String[] lines)
	{
		super(lines);
		gameOverImage = Main.getMainObject().loadImage("/data/sprites/GameOver.png");
		nextDialog = this;
	}
	
	@Override
	public void showDialog()
	{
		super.showDialog();
		MusicManager.playSongOnce("Gameover.mp3");
	}
	@Override
	public void drawDialog()
	{
		Main.getMainObject().image(gameOverImage, 0, 0);
		super.drawDialog();
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
