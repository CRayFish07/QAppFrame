package plat.frame.app.define;

/**
 * 会话对象接口类.
 * @author zhangcq
 *
 */
public interface ICTSession
{
	//保存key-value
	public String getValue( String key );
	public boolean setValue( String key, String value );
	
	public Object getObj( String key );
	public boolean setObj( String key, Object value );
	
	//获取会话ID
	public String getSessId();
	
	//检查会话,是否有效.
	public boolean isValid();
	
	//销毁会话
	public boolean destroySess();
}
