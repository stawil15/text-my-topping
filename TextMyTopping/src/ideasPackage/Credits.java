package ideasPackage;

import processing.core.PGraphics;
import processing.core.PImage;

public class Credits
{
	private String credits;
	private static float textSpeed = -.5f;
	private final static int CREDITS_WIDTH = 1024;
	private final static int CREDITS_HEIGHT = 220;
	private float positionY = CREDITS_HEIGHT;
	
	private PGraphics buffer;
	public Credits()
	{
		credits = "Text My Topping\n"
				+ "    ~A Game by~\n"
				+ "Graham Jenkins\n"
				+ "David Clifford\n"
				+ "Saeed Tawil\n"
				+ "Eric Mustee\n\n";
		credits +="\nMusic\n"
				+ "________________________\n";
		credits += "Earthbound - Onett Theme\n";
		credits += "    (c) HAL Laboratory\n";
		credits += "Cave Story - Saftey\n";
		credits += "Cave Story - Mimiga Town\n";
		credits += "Cave Story - Game over\n";
		credits += "    (c) Studio Pixel\n";
		credits += "Super Mario World - Ending Theme\n";
		credits += "    (c) Nintendo";
		buffer = Main.getMainObject().createGraphics(CREDITS_WIDTH, CREDITS_HEIGHT);
	}
	
	public void draw()
	{
		buffer.beginDraw();
		buffer.textFont(Main.font);
		buffer.textSize(56);
		buffer.background(buffer.color(1,1,1,196));
		buffer.text(credits,350,positionY);
		positionY += textSpeed;
		buffer.endDraw();
		buffer.loadPixels();
		Main.getMainObject().image(buffer, 0, 430);
		
		if (positionY < -450)
		{
			positionY = CREDITS_HEIGHT+30;
		}
	}
	
}
