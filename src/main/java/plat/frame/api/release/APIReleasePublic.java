package plat.frame.api.release;

import java.util.List;

import plat.frame.api.APIRelease;
import plat.frame.api.APIReleaseInfo;

/**
 * 公共模块
 * @author zhangcq
 *
 */
public class APIReleasePublic extends APIRelease
{
	//此URL对格式有严格要求,必须两个一起即:接口简述+地址URL
	private String[] urls =
		{
			"公共模块",										//公共模块
			"/ns0/api/list.do?module=APIReleasePublic",		//首页地址
			"短信验证码发送",									
			"/pns0/sms/Sms/sendVSms.api",
			"验证短信验证码",									
			"/pns0/sms/Sms/verifySMSCode.api"
		};

	public List<APIReleaseInfo> queryAPIs()
	{
		return queryAllAPIs(urls);
	}
}
