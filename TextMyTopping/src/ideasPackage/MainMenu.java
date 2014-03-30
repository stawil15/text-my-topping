package ideasPackage;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import processing.core.PImage;

// This class generates a main menu that the user can select
// items from. Choices are handled in the selectChoice method.
public class MainMenu extends BranchingDialog
{
	private PImage image;
	
	public MainMenu(PImage image, ArrayList<String> choices)
	{
		super(new String[] {""}, choices, null);
		this.image = image;
		atSelection=true;
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
			atSelection = true;
			JOptionPane.showMessageDialog(Main.getMainObject(), "Use the arrow keys to move, and press SPACE to perform\n"
															  + "interactions. You can interact with rocks and other nonplayer\n"
															  + "characters. You can press CONTROL to pull some rocks\n"
															  + "backwards. Have fun!","How to play",JOptionPane.INFORMATION_MESSAGE);
			GUISystem.showDialog(this);
			
			
		}
		else if (choice == 2)
		{
			System.exit(0);
		}
	}
	
}
