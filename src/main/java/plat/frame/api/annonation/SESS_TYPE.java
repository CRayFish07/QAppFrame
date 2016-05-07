package plat.frame.api.annonation;

/**
 * 平台使用会话类型.
 * @author zhangcq
 *
 */
public enum SESS_TYPE
{
	HTTP_SESS,		//http session
	RDS_SESS,		//redis session
	NO_SESS,		//no session
	DEFAULT			//no effect,just for init.
}
