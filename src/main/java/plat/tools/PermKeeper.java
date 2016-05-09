package plat.tools;

import plat.constant.KBankApp;

/**
 * 安全以及全县策略类.
 * @author zhangcq
 *
 */
public class PermKeeper
{
	//防重检测开关
	public static boolean dupDefend()
	{
		return PropertiesReader.getBoolean(KBankApp.duplicateMessageDefend);
	}
	
	//防重检测开关
	public static boolean isTest()
	{
		return PropertiesReader.getBoolean(KBankApp.isTest);
	}
}
