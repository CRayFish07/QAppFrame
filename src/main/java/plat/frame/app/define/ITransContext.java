package plat.frame.app.define;

import plat.frame.api.meta.QBaseBean;
import plat.frame.app.session.ICTSession;

public interface ITransContext
{
	//获取请求对象.
	public QBaseBean getReqData();
	
	//保存请求对象.
	public void setReqData( QBaseBean reqData);
	
	//获取会话对象
	public ICTSession getSession();
	
	//操作会话对象的接口  >>>>
	public String getSessValue( String key );
	public boolean setSessValue( String key, String value );
	
	public Object getSessObj( String key );
	public boolean setSessObj( String key, Object value );
	//<<<<<
}
