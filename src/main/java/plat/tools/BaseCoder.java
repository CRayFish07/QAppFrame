
package plat.tools;

import org.apache.commons.codec.binary.Base64;

public class BaseCoder
{
	//encode raw bytes to base64 String;
	public static String toBase64String( byte[] data )
	{
		if ( data == null )
		{
			return null;
		}
		return Base64.encodeBase64String(data);
	}
	
	//decode base64 String to raw bytes.
	public static byte[] fromBase64String( String base64 )
	{
		return Base64.decodeBase64(base64);
	}
}
