

package ideasPackage;

import processing.core.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InventoryOpen extends Inventory implements KeyListener
{
	// probably dont need this class because of new Inventory dialog
	
	public InventoryOpen(String[] lines, ArrayList<String> items, ArrayList<Dialog> nextDialogs)
	{
		super(lines, items, nextDialogs);
		// TODO Auto-generated constructor stub
	}	
} // end InventoryOpen class
