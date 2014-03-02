package ideasPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import processing.core.PApplet;

public class BranchingDialog extends Dialog implements KeyListener
{
	// This type of dialog ends with a choice and allows it to branch
	// to other dialogs.
	private boolean atSelection;
	private int currentSelection;
	private final static int SELECTION_LINES = 3;
	private int firstLine = 0;
	private int lastLine = SELECTION_LINES-1;

	private ArrayList<String> choices;
	private ArrayList<Dialog> nextDialogs;

	public BranchingDialog(String[] lines, Main parent,
			ArrayList<String> choices, ArrayList<Dialog> nextDialogs)
	{
		super(lines, parent);
		parent.addKeyListener(this);
		this.choices = choices;
		this.nextDialogs = nextDialogs;
	}

	@Override
	public void drawDialog()
	{
		if (atSelection)
		{
			float offsetX = (parent.width - width) / 2;
			parent.stroke(borderColor);
			parent.strokeWeight(2);
			parent.fill(backgroundColor);
			parent.rect(offsetX, parent.height - borderY - height, width,
					height);
			parent.fill(textColor);

			String textToDisplay = "";


			if (currentSelection < firstLine)
			{
				firstLine--;
			}
			
			if (currentSelection > firstLine+SELECTION_LINES-1)
			{
				firstLine++;
			}
			

			for (int index = firstLine; index < firstLine + SELECTION_LINES; index++)
			{
				if (index == currentSelection)
				{
					textToDisplay += ">" + (index + 1) + ". "
							+ choices.get(index) + "\n";
				} else
				{
					textToDisplay += " " + (index + 1) + ". "
							+ choices.get(index) + "\n";
				}
			}

			parent.text(textToDisplay, offsetX + offsetTextX, parent.height
					- borderY - height + offsetTextY);

		} else
		{
			super.drawDialog();
		}
	}

	@Override
	public void advanceText()
	{
		if (!atSelection)
		{
			if (finishedDisplayingText)
			{
				currentDialogLine++;
				finishedDisplayingText = false;
				currentPositionInText = 0;
				if (currentDialogLine == lines.length)
				{
					atSelection = true;
					currentDialogLine = 0;
				}
			} else
			{
				currentPositionInText += impatientPersonCharactersToAdvance;
			}
		}
	}

	public void selectChoice(int choice)
	{
		parent.showDialog(null);
		atSelection = false;
		currentSelection = 0;
		firstLine = 0;

		if (choice < nextDialogs.size())
		{

			nextDialogs.get(choice).showDialog();
		}
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		if (atSelection)
		{
			if (event.getKeyCode() == Main.UP_KEY)
			{
				currentSelection--;
				if (currentSelection < 0)
				{
					currentSelection = 0;
				}
			}

			if (event.getKeyCode() == Main.DOWN_KEY)
			{
				currentSelection++;
				if (currentSelection >= choices.size())
				{
					currentSelection = choices.size() - 1;
				}
			}
			if (event.getKeyCode() == KeyEvent.VK_SPACE)
			{
				selectChoice(currentSelection);

			}
		}

	}

	@Override
	public void keyReleased(KeyEvent event)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent event)
	{
		// TODO Auto-generated method stub

	}

	private int min(int a, int b)
	{
		if (a < b)
		{
			return a;
		} else
		{
			return b;
		}
	}

}
