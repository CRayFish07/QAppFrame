package plat.frame.api.release;

import java.util.List;

import plat.frame.api.APIRelease;
import plat.frame.api.APIReleaseInfo;

/**
 * 此类用于API接口发布类名即模块分组名.
 * 继承APIRelease类时,应严格按照此格式书写
 * 按照格式修改urls信息即可.
 * @author zhangcq
 *
 */
public class APIReleaseExample extends APIRelease
{
	//此URL对格式有严格要求,必须两个一起即:接口简述+地址URL
	private String[] urls = {
			"理财模块(接口示例)",							//模块信息介绍
			"ns0/api/all.do?module=APIExample",			//首页地址.
			"查询产品信息0",
			"ns0/billAsserts/API4Example/queryCustInfo.api",
			"查询产品信息1",
			"ns0/billAsserts/API4Example/queryCustInfo.api",
			"查询产品信息3",
			"ns0/billAsserts/API4Example/queryCustInfo.api",
			"查询产品信息4",
			"ns0/billAsserts/API4Example/queryCustInfo.api",
			""
	};
	
	public List<APIReleaseInfo> queryAPIs()
	{
		return queryAllAPIs(urls);
	}
}
