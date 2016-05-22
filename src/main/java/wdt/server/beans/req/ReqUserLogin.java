package wdt.server.beans.req;

import plat.frame.api.annonation.APIDefiner;
import plat.frame.api.annonation.FieldDefiner;
import plat.frame.api.meta.QBaseBean;
@APIDefiner(name="登录接口",	version="1.0",
updates={
		"2016-05-17:新建接口.",
		"2016-05-18:修改手机号字段.",
		""
}
)
public class ReqUserLogin extends QBaseBean
{
	@FieldDefiner(name="手机号",required=true)
	private String mobileNo;
	
	@FieldDefiner(name="密码",required=true)
	private String pin;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}
	
}
