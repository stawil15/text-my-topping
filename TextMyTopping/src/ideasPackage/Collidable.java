package ideasPackage;

public interface Collidable
{;
	public GridCoordinate getCoordinates();
	public void draw(float cameraOffsetX, float cameraOffsetY);
	public int getDirection();
	public void doInteract();
}
