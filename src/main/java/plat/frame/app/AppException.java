package plat.frame.app;

import plat.constant.KResponse;
import plat.tools.PermKeeper;

public class AppException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	private String errCode;
	private String errMsg;
	
	public AppException( String retCode, String retMsg )
	{
		super(retMsg);
		this.errCode = retCode;
		this.errMsg = retMsg;
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
