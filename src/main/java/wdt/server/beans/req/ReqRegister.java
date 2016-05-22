package wdt.server.beans.req;

import plat.frame.api.annonation.FieldDefiner;
import plat.frame.api.meta.QBaseBean;

public class ReqRegister extends QBaseBean
{
	@FieldDefiner(name="注册手机号",required=true)
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
