package trans.std.wklogin;

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
	@FieldDefiner(name="手机号",required=true,desc="用作登录的账号")
	private String mobileNo;
	
	private String userName;
	@FieldDefiner(name="证件类型",required=true,desc="00-身份证,01-军人证")
	private String idType;
	private String idNo;
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
	
}
