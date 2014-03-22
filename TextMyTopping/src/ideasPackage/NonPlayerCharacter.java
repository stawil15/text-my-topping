package ideasPackage;

public class NonPlayerCharacter extends Character
{

	private Dialog dialog;

	public NonPlayerCharacter(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, Dialog dialog, boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, addToGrid);
		this.dialog = dialog;

	}

	public void doInteract(int interactionId)
	{
		dialog.showDialog();
	}

}
