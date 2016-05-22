package plat.frame.app;

import plat.constant.KResponse;
import plat.tools.PermKeeper;

public class AppException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errMsg;
	private boolean canThrowOut = true;
	
	public AppException( String retCode, String retMsg )
	{
		this.errCode = retCode;
		this.errMsg = retMsg;
	}
	
	public AppException( String retCode, String retMsg, boolean canThrowOut )
	{
		this.errCode = retCode;
		this.errMsg = retMsg;
		this.canThrowOut = canThrowOut;
	}
	
	public String getErrInfo()
	{
		//如果不能对外暴露则直接返回同一错误
		//只在调试的时候暴露错误.
		if ( PermKeeper.isTest() || canThrowOut )
		{
			return String.format("%s::%s", errCode, errMsg );
		}

		return String.format("%s:%s", KResponse.FAIL, KResponse.MSG_FAIL );
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
