package wdt.server.beans.rsp;

import plat.frame.api.annonation.FieldDefiner;
import plat.frame.api.meta.QBaseBean;

public class RspUserLogin  extends QBaseBean
{
	@FieldDefiner(name="令牌")
	private String token;
	
	@FieldDefiner(name="密码错误次数")
	private int errCnt;
	
	@FieldDefiner(name="密码剩余次数")
	private int leftCnt;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getErrCnt() {
		return errCnt;
	}

	public void setErrCnt(int errCnt) {
		this.errCnt = errCnt;
	}

	public int getLeftCnt() {
		return leftCnt;
	}

	public void setLeftCnt(int leftCnt) {
		this.leftCnt = leftCnt;
	}
	
}
