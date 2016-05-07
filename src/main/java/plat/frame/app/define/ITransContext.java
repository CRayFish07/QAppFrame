package plat.frame.app.define;

import plat.frame.api.BaseRequestBean;

public interface ITransContext
{
	//获取请求对象.
	public Object getReqData();
	
	//保存请求对象.
	public void setReqData(BaseRequestBean reqData);
	
	//获取会话对象
	public ICTSession getSession();
	
	//操作会话对象的接口  >>>>
	public String getSessValue( String key );
	public boolean setSessValue( String key, String value );
	
	public Object getSessObj( String key );
	public boolean setSessObj( String key, Object value );
	//<<<<<
}
