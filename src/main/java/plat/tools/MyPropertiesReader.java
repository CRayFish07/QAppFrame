package plat.tools;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MyPropertiesReader
{
	private ResourceBundle proBundle;
	
	public MyPropertiesReader( String proName )
	{
		proBundle = ResourceBundle.getBundle(proName);
	}

	public String getString(String key)
	{
		try
		{
			return proBundle.getString(key);
		}
		catch (MissingResourceException e)
		{
			XLog.log("WARN:MissingResourceException encountered,key property[%s] not found.", key);
			throw e;
		}
	}
	
	public String getString(String key, String defval )
	{
		String retVal = getString(key);
		if ( StringUtil.isEmpty(retVal) )
		{
			return defval;
		}
		
		return retVal;
	}
	
	public int getInt(String key)
	{
		return Integer.parseInt(getString(key));
	}
	
	public int getInt(String key,int defval )
	{
		int retval = getInt(key);
		if (retval==0)
		{
			return defval;
		}
		
		return retval;
	}
	
	public boolean getBoolean(String key)
	{
		String value = getString(key);
		if ( value != null )
		{
			value = value.trim();
		}
		
		return "true".equalsIgnoreCase(value);
	}
}