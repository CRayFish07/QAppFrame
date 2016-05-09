package plat.constant;

public class KBankApp
{
	//平台启动跟踪符号.
	public static final String INIT_MARK = "__INIT:";

	//密码数组
	public static final int aespos[] = { 18,20,30,22,0,2,4,6,8,10,12,14,16,25,27,31 };
	
	/***** 以下是qconf中配置的key >>>> ****/
	public static final String duplicateMessageDefend = "duplicateMessageDefend";		//报文重复发送检测开关.
	public static final String isTest = "isTest";										//是否是测试环境.
	/******** <<<<< *************/
}
