package ideasPackage;

import java.util.ArrayList;

import processing.core.PImage;

public class MainMenu extends BranchingDialog
{
	private PImage image;
	
	public MainMenu(PImage image, ArrayList<String> choices)
	{
		super(new String[] {""}, choices, null);
		this.image = image;
		atSelection=true;
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void drawDialog()
	{
		Main.getMainObject().image(image, 0, 0);
		super.drawDialog();
	}
	
	@Override
	public void selectChoice(int choice)
	{
		atSelection = false;
		if (choice == 0)
		{
			GUISystem.showDialog(null);
		}
		else if (choice == 1)
		{
			System.exit(0);
		}
	}
	
}
