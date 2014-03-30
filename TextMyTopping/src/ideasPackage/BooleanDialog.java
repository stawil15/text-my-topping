package ideasPackage;

/* 
 * This class creates a dialog that goes to one of two dialogs based on a
 * global boolean value in the GlobalBooleanManager
 */


public class BooleanDialog extends Dialog
{

	private Dialog falseDialog, trueDialog;
	private String variableName;

	// There is no text associated with the dialog
	public BooleanDialog(String variableName)
	{
		super(null);
		// TODO Auto-generated constructor stub
		this.variableName = variableName;
	}

	// Sets which dialog to go to when true
	public void setTrueDialog(Dialog d)
	{
		trueDialog = d;
	}

	// Sets which dialog to go to when false
	public void setFalseDialog(Dialog d)
	{
		falseDialog = d;
	}

	// There is nothing to draw
	@Override
	public void drawDialog()
	{
		// This dialog is not drawn
	}

	// Branch to either the true dialog or the false dialog when this
	// dialog is shown. 
	@Override
	public void showDialog()
	{
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
