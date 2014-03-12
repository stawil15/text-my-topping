package ideasPackage;

import java.util.ArrayList;
import java.util.Hashtable;

import processing.core.PApplet;

public class DialogManager
{
	private static ArrayList<DialogWithMissingInformation> dialogsWithMissingInfo;
	private static Hashtable<String, Dialog> dialogs;

	public static void initializeDialogManager()
	{
		dialogs = new Hashtable<String, Dialog>();
		dialogsWithMissingInfo = new ArrayList<DialogWithMissingInformation>();
	}

	public static Dialog getDialog(String id)
	{
		return dialogs.get(id);
	}

	public static Dialog addDialog(Dialog dialog, String id, String[] rawRow)
	{
		dialogs.put(id, dialog);
		if (dialog.getClass() == BranchingDialog.class)
		{
			int numberOfChoices = Integer.parseInt(rawRow[2]);
			for (int index = 0; index < numberOfChoices; index++)
			{
				int location = 3 + numberOfChoices + index;
				if (!dialogs.containsKey(rawRow[location]))
				{
					dialogsWithMissingInfo.add(new DialogWithMissingInformation(dialog, rawRow[location], index));
				}
			}
		}
		checkDialogs();
		return getDialog(id);
	}

	public static void setNextDialog(String nextId, String id)
	{
		if (!dialogs.containsKey(nextId))
		{
			DialogWithMissingInformation missing =  new DialogWithMissingInformation(getDialog(id), nextId, -1);
			dialogsWithMissingInfo.add(missing);

		} else
		{
			dialogs.get(id).setNextDialog(getDialog(nextId));
		}
	}
	
	public static void checkDialogs()
	{
		ArrayList<DialogWithMissingInformation> toRemove = new ArrayList<DialogWithMissingInformation>();
		for (DialogWithMissingInformation dialogMissingInfo: dialogsWithMissingInfo)
		{
			String idToCheck = dialogMissingInfo.getId();
			if (dialogs.containsKey(idToCheck))
			{
				
				if (dialogMissingInfo.getIndex() == -1)
				{
					dialogMissingInfo.getDialog().setNextDialog(dialogs.get(idToCheck));
					toRemove.add(dialogMissingInfo);
				}
				else
				{
					((BranchingDialog)dialogMissingInfo.getDialog()).setNextDialog(dialogMissingInfo.getIndex(), dialogs.get(idToCheck));
					toRemove.add(dialogMissingInfo);					
				}
			}
		}
		
		for (DialogWithMissingInformation remove : toRemove)
		{
			dialogsWithMissingInfo.remove(remove);
		}
	}
	
	
	

}
