package plat.frame.api.release;

import plat.frame.api.meta.APIRelease;

/**
 * 此类用于API接口发布类名即模块分组名.
 * 继承APIRelease类时,应严格按照此格式书写
 * 按照格式修改urls信息即可.
 * @author zhangcq
 *
 */
public class APIExample extends APIRelease
{
	public String moduleName = "登录模块(接口示例)";
	
	//此URL对格式有严格要求,必须两个一起即:接口简述+地址URL
	public String[] apiInfos = {
			"注册模块",									//模块信息介绍
			"wdt/rlogin/UserLogin/login.api",			//首页地址.
			"登录模块",
			"wdt/rlogin/UserLogin/login.api"
		};
}
