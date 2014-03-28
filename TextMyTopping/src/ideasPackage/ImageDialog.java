package ideasPackage;

import processing.core.PImage;

public class ImageDialog extends Dialog
{

	private PImage image;
	public ImageDialog(String[] lines, PImage image)
	{
		super(lines);
		this.image = image;
	}
	
	@Override
	public void drawDialog()
	{
		Main.getMainObject().image(image, 0, 0);
		super.drawDialog();
	}

}
