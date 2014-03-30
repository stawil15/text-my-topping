package ideasPackage;

public class DestroyCollidableDialog extends Dialog
{
	/*
	 * This dialog type destroys the object in front of the player
	 * when it is shown
	 */
	public DestroyCollidableDialog()
	{
		super(null);
		// TODO Auto-generated constructor stub
	}

	// Destroy the object
	@Override
	public void showDialog()
	{
		CollisionGrid collisionGrid = Main.getPlayer().getCollisionGrid();
		GridCoordinate nextGridCoordinate = CollisionGrid.getNextCoordinate(Main.getPlayer());
		Main.score +=1000;
		if (nextGridCoordinate != null && collisionGrid.isValidPosition(nextGridCoordinate))
		{
			collisionGrid.removeEntitytAt(nextGridCoordinate);
		}
		if (nextDialog != null)
		{
			nextDialog.showDialog();
			System.out.println("HERE"); 
		}
	}

}
