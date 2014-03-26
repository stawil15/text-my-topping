package ideasPackage;

import processing.core.PApplet;

public class BooleanDialog extends Dialog
{

	private Dialog falseDialog, trueDialog;
	private String variableName;

	public BooleanDialog(String variableName)
	{
		super(null);
		// TODO Auto-generated constructor stub
		this.variableName = variableName;
	}

	public void setTrueDialog(Dialog d)
	{
		trueDialog = d;
	}

	public void setFalseDialog(Dialog d)
	{
		falseDialog = d;
	}

	@Override
	public void drawDialog()
	{
		// This dialog is not drawn
	}

	@Override
	public void showDialog()
	{
		PApplet.println("SHOWING BOOLEAN DIALOG!");
		if (GlobalBooleanManager.getValue(variableName))
		{
			if (trueDialog != null)
			{
				GUISystem.showDialog(trueDialog);
				trueDialog.showDialog();
			}
		}
		else
		{
			if (falseDialog != null)
			{
				GUISystem.showDialog(falseDialog);
				falseDialog.showDialog();
			}
		}
	}

}
