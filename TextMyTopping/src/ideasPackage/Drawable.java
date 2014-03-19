package ideasPackage;

public interface Drawable
{
	public void draw(float cameraOffsetX, float cameraOffsetY);
	public void drawAtExactly(float x, float y, boolean updateAnimation);
}
