package plat.frame.app.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import plat.frame.api.meta.QBaseBean;
import plat.frame.app.define.ICTSession;
import plat.frame.app.define.ITransContext;
import plat.frame.app.msg.ReqMessageHead;
import plat.frame.app.msg.RspMessageHead;

/**
 * 应用上下文.
 * 需要屏蔽具体的通讯方式和协议.(ICTSession接口).
 * @author zhangcq
 *
 */
public class TransContext
{
	private ReqMessageHead reqHead;
	private RspMessageHead rspHead;
	private QBaseBean reqBody,rspBody;
	
	private ICTSession session;
	
	private Map<String,Object> dataMap = new ConcurrentHashMap<String,Object>();
	
	public TransContext()
	{
		rspHead = new RspMessageHead();
	}
	
	//transdata access;
	public Object getTransValue( String key ) {
		return dataMap.get(key);
	}

	public TransContext saveTransValue( String key, String value ) {
		dataMap.put(key, value );
		return this;
	}
	
	//session dos
	public String getSessValue(String key) {
		// TODO Auto-generated method stub
		if ( session != null )
		{
			return session.getValue(key);
		}
		
		return null;
	}

	public boolean setSessValue(String key, String value) {
		// TODO Auto-generated method stub
		if ( session != null )
		{
			session.setValue(key,value);
			return true;
		}
		
		return false;
	}

	public Object getSessObj(String key) {
		// TODO Auto-generated method stub
		if ( session != null )
		{
			return session.getObj(key);
		}
		return null;
	}

	public boolean setSessObj(String key, Object value) {
		// TODO Auto-generated method stub
		if ( session != null )
		{
			return session.setObj(key,value);
		}
		return false;
	}

	//getters-setters
	public TransContext setSession(ICTSession session) {
		this.session = session;
		return this;
	}

	public QBaseBean getReqBody() {
		return reqBody;
	}

	public void setReqBody(QBaseBean reqBody) {
		this.reqBody = reqBody;
	}

	public QBaseBean getRspBody() {
		return rspBody;
	}

	public void setRspBody(QBaseBean rspBody) {
		this.rspBody = rspBody;
	}

	public ICTSession getSession()
	{
		return session;
	}

	public String getRetMsg() {
		return rspHead.getRetMsg();
	}

	public TransContext setRetMsg(String retMsg) {
		this.rspHead.setRetMsg(retMsg);
		return this;
	}

	public String getRetCode() {
		return rspHead.getRetCode();
	}

	public TransContext setRetCode(String retCode) {
		this.rspHead.setRetCode(retCode);
		return this;
	}

	public ReqMessageHead getReqHead() {
		return reqHead;
	}

	public TransContext setReqHead(ReqMessageHead reqHead) {
		this.reqHead = reqHead;
		return this;
	}

	public RspMessageHead getRspHead() {
		return rspHead;
	}

	public TransContext setRspHead(RspMessageHead rspHead) {
		this.rspHead = rspHead;
		return this;
	}
}
