package plat.frame.app.msg;

import plat.constant.KResponse;

/***
 * 交易结果类
 * @author zhangcq
 *
 */
public class RspMessageHead
{
	private String retCode;
	private String retMsg;
	private String adtCode;
	private String adtMsg;
	private String adtField;
	
	public RspMessageHead()
	{
		retCode = KResponse.SUCCESS;
		retMsg = KResponse.MSG_SUCCESS;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getAdtCode() {
		return adtCode;
	}

	public void setAdtCode(String adtCode) {
		this.adtCode = adtCode;
	}

	public String getAdtMsg() {
		return adtMsg;
	}

	public void setAdtMsg(String adtMsg) {
		this.adtMsg = adtMsg;
	}

	public String getAdtField() {
		return adtField;
	}

	public void setAdtField(String adtField) {
		this.adtField = adtField;
	}
}
