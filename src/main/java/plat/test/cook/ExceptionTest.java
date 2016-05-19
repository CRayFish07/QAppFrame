package plat.test.cook;

import java.lang.reflect.Method;

public class ExceptionTest
{
	public void causeEx()
	{
		double x = 7/0;
	}

	public void reflectEx()
	{
		Class<?> clazz = ExceptionTest.class;
		try {
			Method mtd = clazz.getMethod("causeEx");
			mtd.invoke(clazz.newInstance());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
//			InvocationTargetException  inEx = (InvocationTargetException)e;
			e.printStackTrace();
			System.out.println(getExceptionLog(e.getCause()));
//			e.printStackTrace();
		}
	}

	public static void main( String args[] )
	{
		ExceptionTest et = new ExceptionTest();
		//		et.causeEx();
		et.reflectEx();
	}
	
	public static String getExceptionLog( Throwable ex )
	{
		StackTraceElement[] stackTraceElements = ex.getStackTrace();
		StringBuffer exLogStr = new StringBuffer();
		if(stackTraceElements!=null)
		{
			for(int i =0;i<stackTraceElements.length;i++)
			{
				exLogStr.append( stackTraceElements[i].getMethodName());
				exLogStr.append(" ["+stackTraceElements[i].getFileName());
				exLogStr.append(":"+stackTraceElements[i].getLineNumber()+"]");
				exLogStr.append("\n");
			}
		}
		return  ex.toString()+ex.getMessage()+"\tCALL_TRACE>>>>:\n"+exLogStr.toString()+"<<<<";
	}
}
