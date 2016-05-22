package wdt.server.beans.rsp;

import plat.frame.api.annonation.FieldDefiner;
import plat.frame.api.meta.QBaseBean;

public class RspRegister extends QBaseBean
{
	@FieldDefiner(name="令牌",required=true,desc="用于放入通讯头中.")
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
