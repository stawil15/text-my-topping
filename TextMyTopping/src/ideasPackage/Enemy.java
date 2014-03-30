package ideasPackage;

import processing.core.PApplet;

public class Enemy extends Character
{
	private Dialog dialog;
	public GridCoordinate playerLocation;

	public Enemy(GridCoordinate coordinates, int initialDirection, int animationFrames, String imageName,
			CollisionGrid c, Weapon selectedWeapon, boolean addToGrid, PApplet parent, Path findPath, Attack attack)
	{
		super(coordinates, initialDirection, animationFrames, imageName, c, addToGrid);
	}

	public void doInteract()
	{
		// might want to change this to attacking
		
	}
	
	public void pathFinding(GridCoordinate playerCoordinates)
	{
		//  Use this method to find quickest path to player
		// might want to male a separate class
		// A* would be a start
		// GridCoordinate playerLocation =
		playerCoordinates = getCoordinates();
		//Path newPath;
		//newPath(this.getCoordinates(), playerCoordinates);
		
		//float playerX = PlayerCharacter.getOffsetX();
		
	}

	public void beginHostile()
	{
		//  run two tests for if it is now time to be hostile
			// one for in range of the player
			// one for if the camera is over this particular enemy
		
	}
	
	
}
