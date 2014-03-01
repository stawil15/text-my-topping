package ideasPackage;

import processing.core.*;

public class Dialog
{
	protected String[] lines;
	private PFont dialogFont;
	protected Main parent;
	private Dialog nextDialog;
	private static float dialogSpeed = .5f;
	private int currentDialogLine = 0;
	private float impatientPersonCharactersToAdvance = 5f;

	protected float width = 500f;
	protected float height = 80f;
	protected float borderY = 20f;
	protected float offsetTextX = 20f;
	protected float offsetTextY = 25f;
	private int borderColor;
	private int backgroundColor;
	private int textColor;
	private float currentPositionInText;
	private boolean finishedDisplayingText;

	public Dialog(String[] lines, Main parent)
	{
		this.lines = lines;
		dialogFont = parent.loadFont("data\\fonts\\MiniPower.vlw");
		this.parent = parent;
		parent.textFont(dialogFont);
		borderColor = parent.color(0);
		textColor = parent.color(0);
		backgroundColor = parent.color(200,200,200,100);
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

	public void setDialogSpeed(int dialogSpeed)
	{
		this.dialogSpeed = dialogSpeed;
	}

	public void setNextDialog(Dialog nextDialog)
	{
		this.nextDialog = nextDialog;
	}

	public void showDialog()
	{
		parent.showDialog(this);
	}

	public void drawDialog()
	{

		if (lines.length > 0)
		{
		float offsetX = (parent.width - width) / 2;
		parent.stroke(borderColor);
		parent.strokeWeight(2);
		parent.fill(backgroundColor);
		parent.rect(offsetX, parent.height - borderY - height, width, height);
		parent.fill(textColor);

		if (!finishedDisplayingText)
			currentPositionInText += dialogSpeed;

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
				parent.showDialog(null);
				currentDialogLine = 0;
				if (nextDialog != null)
				{
					parent.showDialog(nextDialog);
				}
			}
		}
		else
		{
			currentPositionInText += impatientPersonCharactersToAdvance;
		}
	}
}
