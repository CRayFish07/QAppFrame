package plat.frame.api.release;

import plat.frame.api.meta.APIRelease;

public class RLogin extends APIRelease {

	public String moduleName = "登录注册模块";
	
	//此URL对格式有严格要求,必须两个一起即:接口简述+地址URL
	public String[] apiInfos = {
			"注册模块",
			"wdt/rlogin/UserRegister/register.api",
			"登录模块",
			"wdt/rlogin/UserLogin/login.api"
		};

}
