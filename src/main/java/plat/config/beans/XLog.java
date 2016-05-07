package plat.config.beans;

public class XLog {
	public static void log( String fmt, Object...args )
	{
		System.out.println( String.format(fmt, args));
	}
}
