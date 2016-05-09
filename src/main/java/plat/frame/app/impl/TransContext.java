package plat.frame.app.impl;

import plat.frame.api.QBaseBean;
import plat.frame.app.define.ICTSession;
import plat.frame.app.define.ITransContext;

/**
 * 数据总线.
 * 数据总线也可能没有上线文.
 * 需要屏蔽具体的通讯方式和协议.(ICTSession接口)
 * @author zhangcq
 *
 */
public class TransContext
{
	private String retMsg;
	private String retCode;
	
	private QBaseBean reqData,rspData;
	
	private ICTSession session;
	
	//构造函数.
	public TransContext( ICTSession sess, QBaseBean reqBean )
	{
		session = sess;
		reqData = reqBean;
	}
	
	public TransContext( ICTSession session )
	{
		this.session = session;
	}
	
	public TransContext( QBaseBean reqData )
	{
		this.reqData = reqData;
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
	public QBaseBean getReqData() {
		return reqData;
	}
	
	public void setReqData(QBaseBean reqData) {
		this.reqData = reqData;
	}

	public void setSession(ICTSession session) {
		this.session = session;
	}

	public ICTSession getSession()
	{
		return session;
	}
}
