

import processing.core.*;

public class processingSketch extends PApplet
{
	/**
	 * wtf is this
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String args[])
	{
		PApplet.main(new String[] { "--present", "processingSketch" });
	}

	public void setup()
	{
		size(700, 700);
		//size(displayWidth, displayHeight);
		/*
		size(400, 400);
		if (frame != null)
		{
			frame.setResizable(true);
		}
		*/
		background(0);
	}

	public void draw()
	{
		stroke(255, 255, 255);
		if (mousePressed)
		{
			line(mouseX, mouseY, pmouseX, pmouseY);
		}
	}
}
