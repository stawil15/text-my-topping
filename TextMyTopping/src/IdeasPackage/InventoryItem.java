package IdeasPackage;

import processing.core.*;

public class InventoryItem
{
	// This class contains an item that can be stored in an inventory
	
	private PImage sprite;
	private String name;
	
	public InventoryItem(PImage sprite, String name)
	{
		this.sprite = sprite;
		this.name = name;
	}
}
