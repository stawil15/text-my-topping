package ideasPackage;

public class PushableOnlyObject extends MoveableObject
{

	private static Dialog cannotPullDialog;
	public PushableOnlyObject(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, boolean addToGrid)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, addToGrid);
		if (cannotPullDialog == null)
		{
			cannotPullDialog = new Dialog(new String[] {"The object is too slippery.\nIt cannot be pulled"});
		}
	}
	
	@Override
	public void doInteract(int interactionId)
	{
		if (interactionId == PlayerCharacter.MAIN_INTERACTION)
		{
			setDirection(Main.getPlayer().getDirection());
			if (canMove())
			{
				updatePlayerDirectionWhenFinishedMoving = false;
				move((Main.getPlayer()).getDirection());
				Main.getPlayer().move(Main.getPlayer().getDirection());
			}
		}
		else if (interactionId == PlayerCharacter.SECONDARY_INTERACTION)
		{
			cannotPullDialog.showDialog();
		}
	}
	
}
