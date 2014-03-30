package ideasPackage;

import javax.swing.JOptionPane;

public class ValueSetStringDialog extends Dialog
{

	// This type of dialog gets a piece of information from the user as a string.
	// This is used when setting the name of the player and enemy
	private String keyToAdd;
	private String prompt;
	private String windowTitle;

	public ValueSetStringDialog(String keyToAdd, String prompt, String windowTitle)
	{
		super(null);
		this.keyToAdd = keyToAdd;
		this.prompt = prompt;
		this.windowTitle = windowTitle;
	}

	@Override
	// Gets the information from the user
	public void showDialog()
	{

		boolean valueIsValid = false;
		String stringToAdd = "";
		while (!valueIsValid)
		{
			stringToAdd = JOptionPane.showInputDialog(Main.getMainObject(), prompt, windowTitle,
					JOptionPane.QUESTION_MESSAGE);

			// Values must not be null, and must be between 1 and 11 characters, and must not contain
			// escape characters or they may be parsed incorrectly
			if (stringToAdd == null)
			{
				JOptionPane.showMessageDialog(Main.getMainObject(), "You must enter something into the textbox.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else if (!(stringToAdd.length() > 0 && stringToAdd.length() <= 11))
			{
				JOptionPane.showMessageDialog(Main.getMainObject(), "Enter a length between 1 and 11 characters.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else if (stringToAdd.contains("\\"))
			{
				JOptionPane.showMessageDialog(Main.getMainObject(), "No using escape characters!",
						"There is no escape.", JOptionPane.ERROR_MESSAGE);
			} else
			{
				valueIsValid = true;
			}
		}

		GlobalStringManager.addString(keyToAdd, stringToAdd);

		GUISystem.showDialog(null);
		currentDialogLine = 0;
		if (nextDialog != null)
		{
			GUISystem.showDialog(nextDialog);
			nextDialog.showDialog();
		}

	}
}
