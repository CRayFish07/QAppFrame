package plat.frame.app.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import plat.frame.app.define.ICTSession;

/**
 * 会话实现类.
 * @author zhangcq
 *
 */
public class CTHttpSession implements ICTSession
{
	HttpSession session;
	HttpServletRequest request;
	
	public CTHttpSession( HttpServletRequest req, boolean create )
	{
		request = req;
		session = req.getSession(create);
	}
	
	@Override
	public String getSessId()
	{
		if ( session != null )
		{
			return session.getId();
		}
		
		return null;
	}
	
	public boolean isValid()
	{
		if ( session == null || request.getSession( false ) == null )
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean destroySess() {
		// TODO Auto-generated method stub
		session.invalidate();
		return false;
	}
	
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return (String)getObj(key);
	}

	public boolean setValue(String key, String value) {
		// TODO Auto-generated method stub
		return setObj(key,value);
	}

	public Object getObj(String key) {
		// TODO Auto-generated method stub
		
		if ( session != null )
		{
			return session.getAttribute(key);
		}
		
		return null;
	}

	public boolean setObj(String key, Object value) {
		// TODO Auto-generated method stub
		if ( session != null )
		{
			session.setAttribute(key, value);
			return true;
		}
		
		return false;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}
