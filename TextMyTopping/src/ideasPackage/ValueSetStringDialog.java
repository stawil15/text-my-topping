package ideasPackage;

import javax.swing.JOptionPane;

public class ValueSetStringDialog extends Dialog
{

	private String keyToAdd;
	private String prompt;
	private String windowTitle;

	public ValueSetStringDialog(String keyToAdd, String prompt, String windowTitle)
	{
		super(null);
		this.keyToAdd = keyToAdd;
		this.prompt = prompt;
		this.windowTitle = windowTitle;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void showDialog()
	{

		boolean valueIsValid = false;
		String stringToAdd = "";
		while (!valueIsValid)
		{
			stringToAdd = JOptionPane.showInputDialog(Main.getMainObject(), prompt, windowTitle,
					JOptionPane.QUESTION_MESSAGE);

			if (stringToAdd == null)
			{
				JOptionPane.showMessageDialog(Main.getMainObject(), "You must enter something into the textbox.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} else if (!(stringToAdd.length() > 0 && stringToAdd.length() < 10))
			{
				JOptionPane.showMessageDialog(Main.getMainObject(), "Enter a length between 1 and 10 characters.",
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
