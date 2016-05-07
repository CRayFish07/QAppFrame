package plat.tools;

import com.google.gson.Gson;

public class JsonCoder
{
	/**
	 * Object to A String.
	 * @param obj
	 * @return
	 */
	public static String toJsonString( Object obj )
	{
		return new Gson().toJson(obj);
	}
	
	/**
	 * Convert json to Object.
	 * @param json
	 * @param classz
	 * @return
	 */
	public static <T> T fromJsonString( String json, Class<T> classz )
	{
		return new Gson().fromJson(json, classz);
	}
}
