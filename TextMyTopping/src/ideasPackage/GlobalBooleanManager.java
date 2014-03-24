package ideasPackage;

import java.util.Hashtable;

public class GlobalBooleanManager
{
	private static Hashtable<String, Boolean> globalBooleans;
	
	public static void initialize()
	{
		globalBooleans = new Hashtable<String, Boolean>();
	}
	
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
	
	public static void setValue(String variable, boolean value)
	{
		globalBooleans.put(variable, value);
	}
	
}
