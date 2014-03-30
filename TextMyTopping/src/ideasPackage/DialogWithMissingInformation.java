package ideasPackage;

/*
 * This class is not actually a dialog, but rather contains information for a dialog
 * that was loaded into the dialog manager that did not have a next dialog that could
 * be found
 */
public class DialogWithMissingInformation
{

	private Dialog dialogMissingInformation;
	private String idToMatch;
	private int index;

	public DialogWithMissingInformation(Dialog d, String id, int index)
	{
		this.index = index;
		this.idToMatch = id;
		this.dialogMissingInformation = d;
	}
	

	public String getId()
	{
		return idToMatch;
	}
	
	// Return the dialog missing a next dialog
	public Dialog getDialog()
	{
		return dialogMissingInformation;
	}
	
	// Get the id. For boolean dialogs, the missing false dialog represents 1, and the 
	// missing true dialog represents 2. For branching dialogs, the index represents which
	// the index of the nextDdialogs that was missing in the branching dialog. 
	public int getIndex()
	{
		return index;
	}

}
