package plat.tools;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesReader
{
	private static boolean canlog = false;

	private static final String CONFIG = "qconf";
	private static ResourceBundle CONFIG_RES = ResourceBundle.getBundle(CONFIG);
	private PropertiesReader()
	{
	}

	public static String getString(String key)
	{
		try
		{
			return CONFIG_RES.getString(key);
		}
		catch (MissingResourceException e)
		{
			log(String.format("WARN:MissingResourceException encountered,key property[%s] not found.", key));
		}
		return "";
	}
	
	public static boolean getBoolean(String key)
	{
		String value = getString(key);
		if ( value != null )
		{
			value = value.trim();
		}
		
		return "true".equalsIgnoreCase(value);
	}

	public static void log( String lgmsg )
	{
		if ( !canlog )
		{
			return;
		}

		Thread currTh = Thread.currentThread();
		System.out.println( ""+currTh.getId()+"-"+currTh.getName()+":"+lgmsg );
	}
}