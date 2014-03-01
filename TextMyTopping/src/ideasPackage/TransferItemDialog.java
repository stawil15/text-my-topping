package ideasPackage;

import processing.core.PApplet;

// This dialog ends in a transaction of an item between two characters.
public class TransferItemDialog extends Dialog
{
	private Character fromCharacter;
	private Character toCharacter;
	private InventoryItem item;
	
	public TransferItemDialog(String[] lines, Character fromCharacter, Character toCharacter, InventoryItem item, Main parent)
	{
		super(lines, parent);
		this.fromCharacter = fromCharacter;
		this.toCharacter = toCharacter;
		this.item = item;
	}
	
	private void doTransaction()
	{
		InventoryItem itemToTransact = fromCharacter.takeAwayItem(item);
		
		if (itemToTransact!=null)
		{
			toCharacter.giveItem(itemToTransact);
			// Say fromCharacter.name gave item.name to toCharacter.name.
		}
		else
		{
			// Say fromCharacter.name does not have item.name!
		}
	}
}
