package ideasPackage;

import processing.core.PApplet;

public class NonPlayerCharacter extends Character
{

	private Dialog dialog;

	public NonPlayerCharacter(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, Dialog dialog, boolean addToGrid, PApplet parent)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, addToGrid, parent);
		this.dialog = dialog;

	}

	public void doInteract()
	{
		dialog.showDialog();
	}

}
