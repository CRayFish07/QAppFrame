package plat.frame.app.impl;

import javax.servlet.http.HttpServletRequest;

import plat.frame.api.annonation.SESS_TYPE;
import plat.frame.app.define.ICTSession;
import plat.frame.app.define.ITransContext;

/**
 * 上线文工厂，包括会话管理.
 * @author zhangcq
 *
 */
public class CTSessionFactory
{
	//登录会话令牌.
	private String sessToken;
	
	//会话类型,表示登录成功后,建立的会话类型.
	private SESS_TYPE sessType = SESS_TYPE.DEFAULT;
	
	//是否建立新会话，理论上这个只在login的时候为true,此时会新建一个会话.;
	private boolean createSess;					
	
	private HttpServletRequest request;
	
	public ICTSession createSession()
	{
		switch ( sessType )
		{
			case HTTP_SESS:
				return new CTHttpSession( request, createSess );
			case RDS_SESS:
				break;
			default:
				break;
		}
		
		return null;
	}
	
	//setters; >>>>>>>
	public CTSessionFactory setRequset( HttpServletRequest arg0 )
	{
		request = arg0;
		return this;
	}

	public CTSessionFactory setSessToken( String arg0 )
	{
		sessToken = arg0;
		return this;
	}

	public CTSessionFactory setSessType( SESS_TYPE arg0 )
	{
		sessType = arg0;
		return this;
	}
	
	public CTSessionFactory setCreateSess( boolean arg0 )
	{
		createSess = arg0;
		return this;
	} 
}
