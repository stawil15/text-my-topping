package ideasPackage;

// This interface dictates properties all collidable objects must have
public interface Collidable extends Drawable
{
	public GridCoordinate getCoordinates();
	public void draw(float cameraOffsetX, float cameraOffsetY);
	public int getDirection();
	public void doInteract(int interactionId);
}
