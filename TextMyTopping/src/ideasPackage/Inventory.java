

package ideasPackage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import processing.core.*;

public class Inventory extends Dialog implements KeyListener
{
	// class for all items in inventory
	// this class extends dialog to bring up a menu with items in the inventory
	
	private boolean selection; // can be selected
	private int selected; // item selected
	private int line = 0; // which line is displayed
	private static int LINES = 3; // lines of dialog displayed
	
	private ArrayList<String> items;
	private ArrayList<Dialog> nextDialogs;

	public Inventory(String[] lines, ArrayList<String> items, ArrayList<Dialog> nextDialogs) {
		super(lines);
		parent.addKeyListener(this);
		this.items = items;
		this.nextDialogs = nextDialogs;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		
		
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
} // end Inventory class
