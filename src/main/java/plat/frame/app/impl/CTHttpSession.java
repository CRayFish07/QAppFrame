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
	public String getSessToken()
	{
		if ( session != null )
		{
			return session.getId();
		}
		
		return null;
	}
	
	@Override
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
	
	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return (String)getObj(key);
	}

	@Override
	public boolean setValue(String key, String value) {
		// TODO Auto-generated method stub
		return setObj(key,value);
	}
	
	@Override
	public Object getObj(String key) {
		// TODO Auto-generated method stub
		
		if ( session != null )
		{
			return session.getAttribute(key);
		}
		
		return null;
	}
	
	@Override
	public boolean setObj(String key, Object value) {
		// TODO Auto-generated method stub
		if ( session != null )
		{
			session.setAttribute(key, value);
			return true;
		}
		
		return false;
	}
	
	/******
	 * 		 CTHttpSession特有的getter-setters
	 ******/
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

	@Override
	public String getEncKey(String type) {
		// TODO Auto-generated method stub
		return (String)session.getAttribute("_ENCKEY_"+type);
	}

	@Override
	public boolean setEncKey(String type, String encKey) {
		// TODO Auto-generated method stub
		
		session.setAttribute("_ENCKEY_"+type, encKey );
		return true;
	}
}
