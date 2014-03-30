package ideasPackage;

import java.util.Hashtable;

// This class keeps track of all the booleans that can be created dynamically
// through csv files. 
public class GlobalBooleanManager
{
	private static Hashtable<String, Boolean> globalBooleans;
	
	// Initialize our Hashtable
	public static void initialize()
	{
		globalBooleans = new Hashtable<String, Boolean>();
	}

	// Get the value of a specified variable. If it has not been created yet,
	// then the value is false
	public static boolean getValue(String variable)
	{
		if (variable != null && globalBooleans.containsKey(variable))
		{
			return globalBooleans.get(variable);
		}
		else
		{
			return false;
		}
	}
	
	// Set the value of a variable
	public static void setValue(String variable, boolean value)
	{
		globalBooleans.put(variable, value);
	}
	
}
