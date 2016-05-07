package plat.frame.app.define;

import plat.frame.api.annonation.DEFEND_TYPE;
import plat.frame.api.annonation.ENC_TYPE;
import plat.frame.api.annonation.SESS_TYPE;

/**
 * App和服务的的消息类型.
 * 存放请求响应的消息类型格式.
 * @author zhangcq
 *
 */
public class MessageType
{
	public boolean forcePlain;		//用于测试,强制明文.
	public String msgFmt;			//报文格式.
	public Class<?> reqBodyClz;		//请求报文体格式.
	public Class<?> rspBodyClz;		//响应报文体格式.
	
	public ENC_TYPE encType;		//加密类型.
	public SESS_TYPE sessType;		//会话类型.
	public DEFEND_TYPE defendType;	//防重检测类型.
	
	public MessageType( ENC_TYPE encType, SESS_TYPE sessType, DEFEND_TYPE defendType )
	{
		this.encType = encType;
		this.sessType = sessType;
		this.defendType = defendType;
	}

	public ENC_TYPE getEncType() {
		return encType;
	}

	public void setEncType(ENC_TYPE encType) {
		this.encType = encType;
	}

	public SESS_TYPE getSessType() {
		return sessType;
	}

	public void setSessType(SESS_TYPE sessType) {
		this.sessType = sessType;
	}

	public DEFEND_TYPE getDefendType() {
		return defendType;
	}

	public void setDefendType(DEFEND_TYPE defendType) {
		this.defendType = defendType;
	}
	
	public String getMsgFmt() {
		return msgFmt;
	}

	public void setMsgFmt(String msgFmt) {
		this.msgFmt = msgFmt;
	}
	

	public Class<?> getReqBodyClz() {
		return reqBodyClz;
	}

	public void setReqBodyClz(Class<?> reqBodyClz) {
		this.reqBodyClz = reqBodyClz;
	}

	public Class<?> getRspBodyClz() {
		return rspBodyClz;
	}

	public void setRspBodyClz(Class<?> rspBodyClz) {
		this.rspBodyClz = rspBodyClz;
	}

	public String toString()
	{
		return String.format("enctype=%s,sessType=%s,defendType=%s", ""+encType,""+sessType,""+defendType)
				+ String.format(",msgFmt=%s,reqBodyClz=%s,rspBodyClz", 
						msgFmt,reqBodyClz==null?"null":reqBodyClz.getName(),
								rspBodyClz==null?"null":rspBodyClz.getName());
	}
}
