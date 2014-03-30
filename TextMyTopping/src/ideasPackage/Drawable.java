package ideasPackage;

// This interface dictates what every drawable object must have
public interface Drawable
{
	public void draw(float cameraOffsetX, float cameraOffsetY);
	public void drawAtExactly(float x, float y, boolean updateAnimation);
}
