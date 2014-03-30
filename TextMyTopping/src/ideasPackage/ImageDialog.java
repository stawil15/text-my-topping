package ideasPackage;

import processing.core.PImage;

public class ImageDialog extends Dialog
{
	// This dialog shows an image over the dialog.
	private PImage image;
	public ImageDialog(String[] lines, PImage image)
	{
		super(lines);
		this.image = image;
	}
	
	@Override
	// Draws the dialog
	public void drawDialog()
	{
		Main.getMainObject().image(image, 0, 0);
		super.drawDialog();
	}

}
