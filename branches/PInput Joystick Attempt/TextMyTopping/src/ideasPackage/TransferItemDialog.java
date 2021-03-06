package ideasPackage;


// This dialog ends in a transaction of an item between two characters.
public class TransferItemDialog extends Dialog
{
	private Character fromCharacter;
	private Character toCharacter;
	private InventoryItem item;
	
	public TransferItemDialog(String[] lines, Character fromCharacter, Character toCharacter, InventoryItem item)
	{
		super(lines);
		this.fromCharacter = fromCharacter;
		this.toCharacter = toCharacter;
		this.item = item;
	}
	
	public void doTransaction()
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
