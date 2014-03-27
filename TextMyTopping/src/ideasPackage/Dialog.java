package ideasPackage;


import processing.core.*;

public class Dialog
{
	protected String[] lines;
	private PFont dialogFont;
	protected Main parent;
	//private Dialog nextDialog;
	protected static float dialogSpeed = .5f;
	protected int currentDialogLine = 0;
	protected float impatientPersonCharactersToAdvance = 5f;

	protected float width = 500f;
	protected float height = 80f;
	protected float borderY = 20f;
	protected float offsetTextX = 20f;
	protected float offsetTextY = 25f;
	protected int borderColor;
	protected int backgroundColor;
	protected int textColor;
	protected float currentPositionInText;
	protected boolean finishedDisplayingText;
	protected Dialog nextDialog;
	public Dialog(String[] lines)
	{
		parent = Main.getMainObject();
		this.lines = lines;
		dialogFont = Main.font;
		parent.textFont(dialogFont);
		borderColor = parent.color(0);
		textColor = parent.color(0);
		backgroundColor = parent.color(200, 200, 200, 100);
	}

	public void setBorderColor(int color)
	{
		borderColor = color;
	}

	public void setBackgroundColor(int color)
	{
		backgroundColor = color;
	}

	public void setTextColor(int color)
	{
		textColor = color;
	}

	public static void setDialogSpeed(int speed)
	{
		dialogSpeed = speed;
	}

	public void showDialog()
	{
		PApplet.println("SHOWING REGULAR DIALOG!");
		GUISystem.showDialog(this);
	}

	public void drawDialog()
	{

		if (lines!=null && lines.length > 0)
		{
			float offsetX = (parent.width - width) / 2;
			parent.stroke(borderColor);
			parent.strokeWeight(2);
			parent.fill(backgroundColor);
			parent.rect(offsetX, parent.height - borderY - height, width,
					height);
			parent.fill(textColor);

			if (!finishedDisplayingText)
			{
				currentPositionInText += dialogSpeed;
				GUISystem.playDialogSound();
			}

			int subStringLength = (int) (currentPositionInText);

			if (subStringLength > lines[currentDialogLine].length())
			{
				subStringLength = lines[currentDialogLine].length();
				finishedDisplayingText = true;
			}


			parent.text(lines[currentDialogLine].substring(0, subStringLength),
					offsetX + offsetTextX, parent.height - borderY - height
							+ offsetTextY);
		}
	}

	public static void setDialogSpeed(float speed)
	{
		dialogSpeed = speed;
	}

	public void advanceText()
	{
		if (finishedDisplayingText)
		{
			currentDialogLine++;
			finishedDisplayingText = false;
			currentPositionInText = 0;
			if (currentDialogLine == lines.length)
			{
				GUISystem.showDialog(null);
				currentDialogLine = 0;
				if (nextDialog != null)
				{
					GUISystem.showDialog(nextDialog);
					nextDialog.showDialog();
				}
			}
		} else
		{
			currentPositionInText += impatientPersonCharactersToAdvance;
		}
	}
	
	public void setNextDialog(Dialog d)
	{
		nextDialog = d;
	}

}