package ideasPackage;

import java.util.Hashtable;

public class DialogManager
{
	private static Hashtable<String, Dialog> dialogs;


	
	public static void initializeDialogManager()
	{
		dialogs = new Hashtable<String, Dialog>();
	}
	
	public static Dialog getDialog(String id)
	{
		return dialogs.get(id);
	}
	
	public static Dialog addDialog(Dialog dialog, String id)
	{
		dialogs.put(id, dialog);
		return getDialog(id);
	}
	
	public static void setNextDialog(Dialog dialog, String id)
	{
		//dialogs.get(id).setNextDialog(dialog);
	}
}
