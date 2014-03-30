package ideasPackage;

import processing.core.PApplet;
// This class contains a collisionGrid, a sceneryGrid, and a camera, and a
// combines them a background music theme to make a functional level.
public class Level
{
	private CollisionGrid collisionGrid;
	private SceneryGrid sceneryGrid;
	private Camera camera;
	PApplet parent;
	private String bgmMusicName;
	
	public Level(CollisionGrid collisionGrid, SceneryGrid sceneryGrid, Camera camera, PApplet parent)
	{
		this.collisionGrid = collisionGrid;
		this.sceneryGrid = sceneryGrid;
		this.camera = camera;
		
		collisionGrid.setCamera(camera);
		sceneryGrid.setCamera(camera);
		this.parent = parent;
		
	}
	
	// Draws the level
	public void draw()
	{
		parent.background(0);
		Main.getCamera().update();
		sceneryGrid.draw();
		collisionGrid.draw();
	}
	
	// Set the camera of the level
	public void setCamera(Camera camera)
	{
		sceneryGrid.setCamera(camera);
		collisionGrid.setCamera(camera);
	}
	
	// get the levels collision grid
	public CollisionGrid getCollisionGrid()
	{
		return collisionGrid;
	}
	
	// Get the background music name
	public String getBgmMusicName()
	{
		return bgmMusicName;
	}
	
	
}
