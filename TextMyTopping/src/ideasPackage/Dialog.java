package ideasPackage;

import processing.core.*;
public class Dialog
{
	protected String[] lines;
	private PFont dialogFont;
	protected PApplet parent;
	
	public Dialog(String[] lines, PApplet parent)
	{
		this.lines = lines;
		dialogFont = parent.loadFont("data\\fonts\\MiniPower.vlw");
		this.parent = parent;
	}
	
	public void showDialog()
	{
		
	}
}
