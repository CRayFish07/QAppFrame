package plat.tools;

public class XLog {
	public static void log( String fmt, Object...args )
	{
		System.out.println( String.format(fmt, args));
	}
	
	public static void loginit( String fmt, Object...args )
	{
		System.out.println( String.format("__INIT:"+fmt, args));
	}
	
	public static void logcinit( String fmt, Object...args )
	{
		System.out.println( String.format("__CINIT:"+fmt, args));
	}
}
