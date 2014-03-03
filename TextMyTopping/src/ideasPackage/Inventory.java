

package ideasPackage;

import processing.core.*;

public class Inventory extends PApplet
{
	// class for all items in inventory

	private InventoryItem item;

	protected int width = 725;
	protected int height = 525;
	protected int borderColor;
	protected int backgroundColor;
	protected int textColor;
	private PFont inventoryFont;
	protected Main parent;

	protected int itemSpaceOffset = 25;
	protected int itemSpaceWidth = 75;
	protected int offAndWidth = itemSpaceOffset + itemSpaceWidth;
	protected int equipOffset = 200;

	public Inventory(InventoryItem item, Main parent)
	{
		this.item = item;
		inventoryFont = parent.loadFont("data\\fonts\\MiniPower.vlw");
		this.parent = parent;
		parent.textFont(inventoryFont);
		borderColor = parent.color(0);
		textColor = parent.color(0);
		backgroundColor = parent.color(200, 200, 200, 100);
	} // end Inventory

	public void setBorderColor(int color)
	{
		borderColor = color;
	}

	public void setBackgroundColor(int color)
	{
		backgroundColor = color;
	}

	public void setTextColor(int color)
	{
		textColor = color;
	}

	public void drawInventory()
	{
		for (int index = 0; index < width; index = index + offAndWidth)
		{
			for (int jndex = 0; jndex < height; jndex = jndex + offAndWidth)
			{
				rect(index + itemSpaceOffset + equipOffset, jndex + itemSpaceOffset, itemSpaceWidth, itemSpaceWidth);
			}
		}
	} // end drawInventory
} // end Inventory class
