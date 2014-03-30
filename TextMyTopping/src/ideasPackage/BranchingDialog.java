package ideasPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class BranchingDialog extends Dialog implements KeyListener
{
	// This type of dialog ends with a choice and allows it to branch
	// to other dialogs.
	
	protected boolean atSelection; // Whether it is time to select a choice
	protected int currentSelection; // The current selection
	private final static int SELECTION_LINES = 3; // How many lines to display while selecting
	private int firstLine = 0; // Keeps track of which line to diplay

	private ArrayList<String> choices;
	private ArrayList<Dialog> nextDialogs;

	public BranchingDialog(String[] lines,ArrayList<String> choices, ArrayList<Dialog> nextDialogs)
	{
		super(lines);
		parent.addKeyListener(this);
		this.choices = choices;
		this.nextDialogs = nextDialogs;
	}

	// Draws the dialog
	@Override
	public void drawDialog()
	{
		// We are in selection mode, display the selections
		if (atSelection)
		{
			// Offset from the edge of the screen
			float offsetX = (parent.width - width) / 2;
			
			// Draw the box
			parent.stroke(borderColor);
			parent.strokeWeight(2);
			parent.fill(backgroundColor);
			parent.rect(offsetX, parent.height - borderY - height, width,
					height);
			
			// Decide on which text to display based on how the 
			// user has selected the text. Make sure to scroll the
			// text down or up if the user is at a selection off
			// the screen.
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
			

			for (int index = firstLine; index < min(firstLine + SELECTION_LINES,choices.size()); index++)
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

			// Display the text
			parent.text(textToDisplay, offsetX + offsetTextX, parent.height
					- borderY - height + offsetTextY);

		} else
		{
			// We are not in selection mode, draw the dialog like normal
			if (lines == null)
			{
				atSelection = true;
			}
			super.drawDialog();
		}
	}

	// Advance the text. This is not really used and was initially added when
	// branching dialogs actually had text associated with them. 
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

	// Actually select the choice
	public void selectChoice(int choice)
	{
		GUISystem.showDialog(null);
		atSelection = false;
		currentSelection = 0;
		firstLine = 0;

		if (choice < nextDialogs.size())
		{

			nextDialogs.get(choice).showDialog();
		}
	}

	// What to do when the user presses a key. Part of the KeyListener implementation.
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
	
	// Show the dialog
	@Override
	public void showDialog()
	{
		super.showDialog();
	}

	// Used for KeyListener implementation
	@Override
	public void keyReleased(KeyEvent event)
	{
	
	}

	
	// Used for KeyListener implementation
	@Override
	public void keyTyped(KeyEvent event)
	{

	}
	
	// Gets the dialogs that may be chosen from
	public ArrayList<Dialog> getNextDialogs()
	{
		return nextDialogs;
	}
	
	// Sets a particular index of to next dialog
	public void setNextDialog(int index, Dialog d)
	{
		nextDialogs.set(index, d);
	}

	// An integer minimum function
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
