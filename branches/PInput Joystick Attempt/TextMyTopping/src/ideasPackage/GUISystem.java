package ideasPackage;

public class GUISystem
{
	private static Dialog currentDialog;
	
	public static boolean showingDialog()
	{
		return (currentDialog!=null);
	}
	
	public static void draw()
	{
		if (showingDialog())
		{
			currentDialog.drawDialog();
		}
	}
	
	public static void showDialog(Dialog d)
	{
		currentDialog = d;
	}
	
	public static void advancedCurrentDialog()
	{
		if (showingDialog())
		{
			currentDialog.advanceText();
		}
	}
	
}
