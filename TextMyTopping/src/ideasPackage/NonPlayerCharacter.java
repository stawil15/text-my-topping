package ideasPackage;

public class NonPlayerCharacter extends Character
{
	
	// This class adds NPCS that you can have conversations with

	private Dialog dialog;

	public NonPlayerCharacter(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName, CollisionGrid c, Dialog dialog,
			boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, addToGrid);
		this.dialog = dialog;

	}

	// Show the dialog and move the NPC so that they are facing the character
	public void doInteract(int interactionId)
	{
		if (interactionId == PlayerCharacter.MAIN_INTERACTION)
		{
			dialog.showDialog();
		}
	}

}
