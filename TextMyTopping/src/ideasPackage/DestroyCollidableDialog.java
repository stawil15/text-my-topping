package ideasPackage;

public class DestroyCollidableDialog extends Dialog
{

	public DestroyCollidableDialog()
	{
		super(null);
		// TODO Auto-generated constructor stub
	}

	public void showDialog()
	{
		CollisionGrid collisionGrid = Main.getPlayer().getCollisionGrid();
		GridCoordinate nextGridCoordinate = collisionGrid.getNextCoordinate(Main.getPlayer());
		if (nextGridCoordinate != null && collisionGrid.isValidPosition(nextGridCoordinate))
		{
			collisionGrid.removeElementAt(nextGridCoordinate);
		}
		if (nextDialog != null)
		{
			nextDialog.showDialog();
		}
	}

}
