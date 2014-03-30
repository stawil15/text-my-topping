package ideasPackage;

import java.util.Hashtable;

// This class keeps track of all the strings that can be created dynamically
// through csv files. 
public class GlobalStringManager
{
	private static Hashtable<String, String> strings;
	
	// Initialize our Hashtable
	public static void initialize()
	{
		strings = new Hashtable<String,String>();
	}
	
	// Set the value of a variable
	public static void addString(String key, String value)
	{
		strings.put(key, value);
	}
	
	// Get the value of a specified variable. If it has not been created yet,
	// then the value is null.
	public static String getString(String key)
	{
		if (key == null)
		{
			return null;
		}
		
		return strings.get(key);
	}
}
