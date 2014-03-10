package ideasPackage;

import processing.core.PApplet;

public class LevelLoaderTest extends PApplet
{
	public static void main(String args[])
	{
		PApplet.main("ideasPackage.LevelLoaderTest");
	}
	
	public void setup()
	{
		new LevelLoader(this);
	}
	
	public void draw()
	{
		
	}
	
}
