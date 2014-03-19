package ideasPackage;

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
	
	public Dialog getDialog()
	{
		return dialogMissingInformation;
	}
	
	public int getIndex()
	{
		return index;
	}

}
