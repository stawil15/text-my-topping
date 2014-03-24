package ideasPackage;

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
		if (GlobalBooleanManager.getValue(variableName))
		{
			trueDialog.showDialog();
		}
		else
		{
			falseDialog.showDialog();
		}
	}
	
	

}
