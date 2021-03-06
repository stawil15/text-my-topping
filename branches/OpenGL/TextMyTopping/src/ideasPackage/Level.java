package ideasPackage;

import processing.core.PApplet;

public class Level
{
	private CollisionGrid collisionGrid;
	private SceneryGrid sceneryGrid;
	private Camera camera;
	PApplet parent;
	
	public Level(CollisionGrid collisionGrid, SceneryGrid sceneryGrid, Camera camera, PApplet parent)
	{
		super();
		this.collisionGrid = collisionGrid;
		this.sceneryGrid = sceneryGrid;
		this.camera = camera;
		
		collisionGrid.setCamera(camera);
		sceneryGrid.setCamera(camera);
		this.parent = parent;
		
	}
	
	public void draw()
	{
		parent.background(0);
		camera.update();
		sceneryGrid.draw();
		collisionGrid.draw();
	}
	
	public void setCamera(Camera camera)
	{
		sceneryGrid.setCamera(camera);
		collisionGrid.setCamera(camera);
	}
	
	
}
