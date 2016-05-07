package plat.frame.app;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import plat.tools.net.HttpServletProfessional;

public class CallProxy
{
	//报文读取工具.
	private HttpServletProfessional httptool = null;
	
	//读取报文的大小 1M
	private int maxpkg = 1024*1024;
	
	public Object callMethod( String tarClazz, String tarMethod, Class[] args )
	{
		try
		{
			Class<?> clz = Class.forName(tarClazz);
			Method method = clz.getMethod( tarMethod, args );
			return method.invoke( clz.newInstance(), args );
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param request
	 * @param charset
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	protected String getInputString( HttpServletRequest request, String charset ) throws IOException, InterruptedException
	{
		byte[] indata = getInputStream(request);
		if ( indata != null && indata.length != 0 )
		{
			return new String( indata, charset );
		}
		
		return "";
	}
	
	protected byte[] getInputStream( HttpServletRequest request ) throws IOException, InterruptedException
	{
		if ( httptool == null )
		{
			httptool = HttpServletProfessional.getInstance().setMaxlen(maxpkg);
		}
		
		return httptool.readInput(request);
	}
}
