

package ideasPackage;

import processing.core.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InventoryOpen extends Inventory implements KeyListener
{
	// class for open inventory
	// press 'I' to open inventory
	
	public InventoryOpen(InventoryItem item, Main parent)
	{
		super(item, parent);
		// TODO Auto-generated constructor stub
	}
	
	public void selectItem(InventoryItem item)
	{
		
	}
	
	// implement navigate inventory
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
	}
} // end InventoryOpen class
