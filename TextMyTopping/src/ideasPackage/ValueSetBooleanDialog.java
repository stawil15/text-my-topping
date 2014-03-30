package ideasPackage;

public class ValueSetBooleanDialog extends Dialog
{
	// This dialog type sets a global boolean to a sepcified value

	private String variableName;
	private boolean value;
	public ValueSetBooleanDialog(String variableName, boolean value)
	{
		super(null);
		this.variableName = variableName;
		this.value = value;
	}
	
	
	@Override
	// Shows the dialog and sets the boolean
	public void showDialog()
	{
		super.showDialog();
		GlobalBooleanManager.setValue(variableName, value);
		if (nextDialog != null)
		{
			nextDialog.showDialog();
		}
		else
		{
			GUISystem.showDialog(null);
		}
	}
	
}
