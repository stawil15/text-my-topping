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
			
			if (getDialog(id).getClass() == ValueSetDialog.class)
			{
				PApplet.println("CANNO FIND NEXT DIALOG OF VALUE SET DIALOG TYPE!");
			}

		} else
		{

			dialogs.get(id).setNextDialog(getDialog(nextId));
		}
	}
	
	public static void setTrueDialog(String trueId, String id)
	{
		if (!dialogs.containsKey(trueId))
		{
			DialogWithMissingInformation missing =  new DialogWithMissingInformation(getDialog(id), trueId, 1);
			dialogsWithMissingInfo.add(missing);
		}
		else
		{
			BooleanDialog dialogToSet = (BooleanDialog)dialogs.get(id);
			dialogToSet.setTrueDialog(getDialog(trueId));
			PApplet.println("Set True Dialog");
		}
	}
	
	public static void setFalseDialog(String falseId, String id)
	{
		if (!dialogs.containsKey(falseId))
		{
			DialogWithMissingInformation missing =  new DialogWithMissingInformation(getDialog(id), falseId, 2);
			dialogsWithMissingInfo.add(missing);
		}
		else
		{
			BooleanDialog dialogToSet = (BooleanDialog)dialogs.get(id);
			dialogToSet.setFalseDialog(getDialog(falseId));
			PApplet.println("Set False Dialog");
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
				else if (dialogMissingInfo.getDialog().getClass() == BranchingDialog.class)
				{
					((BranchingDialog)dialogMissingInfo.getDialog()).setNextDialog(dialogMissingInfo.getIndex(), dialogs.get(idToCheck));
					toRemove.add(dialogMissingInfo);					
				}
				else if (dialogMissingInfo.getDialog().getClass() == BooleanDialog.class)
				{
					if (dialogMissingInfo.getIndex() == 1)
					{
						((BooleanDialog)dialogMissingInfo.getDialog()).setTrueDialog(dialogs.get(idToCheck));
						toRemove.add(dialogMissingInfo);
					}
					else if (dialogMissingInfo.getIndex() == 2)
					{
						((BooleanDialog)dialogMissingInfo.getDialog()).setFalseDialog(dialogs.get(idToCheck));
						toRemove.add(dialogMissingInfo);
					}
				}
			}
		}
		
		for (DialogWithMissingInformation remove : toRemove)
		{
			dialogsWithMissingInfo.remove(remove);
		}
	}
	
	
	

}
