package IdeasPackage;

import processing.core.PApplet;

public class BranchingDialog extends Dialog
{
	// This type of dialog ends with a choice and allows it to branch
	// to other dialogs. 
	
	public BranchingDialog(String[] lines, PApplet parent)
	{
		super(lines, parent);
	}
}
