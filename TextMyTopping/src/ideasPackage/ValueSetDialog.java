package ideasPackage;

import processing.core.PApplet;

public class ValueSetDialog extends Dialog
{

	private String variableName;
	private boolean value;
	public ValueSetDialog(String variableName, boolean value)
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
			PApplet.println("SHOWING NEXT DIALOG!");
		}
		else
		{
			PApplet.println("NULL DIALOG!");
			GUISystem.showDialog(null);
		}
	}
	
}
