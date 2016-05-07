package plat.tools;

import java.util.Properties;

import plat.constant.KBankApp;

//配置文件类
//用于根据不同的配置文件进行操作.
public class PermKeeper
{
	//防重检测开关
	public static boolean checkRepeat()
	{
		return PropertiesReader.getBoolean(KBankApp.CHECK_REPEAT);
	}
	
	//新账户体系开关
	public static boolean isNewAcc()
	{
		return PropertiesReader.getBoolean(KBankApp.NEW_ACC);
	}
	
	//日志开关
	public static boolean canLog()
	{
		return PropertiesReader.getBoolean(KBankApp.LOG_ON);
	}
	
	//是否开启测试模式 返回相应的测试数据到报文中.
	public static boolean isTest()
	{
		return PropertiesReader.getBoolean(KBankApp.IS_TEST);
	}
	
	//是否进行拦截
	public static boolean isIntercept()
	{
		return PropertiesReader.getBoolean(KBankApp.IS_INTCP);
	}
}
