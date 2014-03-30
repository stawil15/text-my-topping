package ideasPackage;

import processing.core.PApplet;
import processing.core.PImage;

public class Hole extends StaticObject implements Collidable
{
	protected CollisionGrid collisionGrid;
	public Hole(GridCoordinate coordinates, String imageName, CollisionGrid collisionGrid, boolean addToGrid)
	{
		super(coordinates, imageName, collisionGrid, addToGrid);
		this.collisionGrid = collisionGrid;
		setDialog(new Dialog(new String[] {"This hole looks deep. Perhaps it can be \ncrossed if it is filled with a rock..."}));
	}
	
	public void fillWithMovableObject(MoveableObject object)
	{
		SceneryGrid sceneryGrid = collisionGrid.getCorrespondingSceneryGrid();
		
		PImage sceneryImage = sceneryGrid.getObjectAt(getCoordinates()).getImage();		
		SceneryHole sceneryHole = new SceneryHole(getCoordinates(), sceneryImage, sceneryGrid, true);
		sceneryHole.setHoleImage(getImage());
		object.setDestroyOnMove(sceneryHole);

	}

}
