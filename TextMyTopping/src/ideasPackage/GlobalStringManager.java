package ideasPackage;

import java.util.Hashtable;

public class GlobalStringManager
{
	private static Hashtable<String, String> strings;
	
	public static void initialize()
	{
		strings = new Hashtable<String,String>();
	}
	
	public static void addString(String key, String value)
	{
		strings.put(key, value);
	}
	
	public static String getString(String key)
	{
		if (key == null)
		{
			return null;
		}
		
		return strings.get(key);
	}
}
