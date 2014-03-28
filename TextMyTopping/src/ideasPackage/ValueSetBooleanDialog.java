package ideasPackage;

import processing.core.PApplet;

public class ValueSetBooleanDialog extends Dialog
{

	private String variableName;
	private boolean value;
	public ValueSetBooleanDialog(String variableName, boolean value)
	{
		super(null);
		this.variableName = variableName;
		this.value = value;
	}
	
	
	@Override
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
