package plat.frame.app.session;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import plat.frame.component.RedisConnector;

/**
 * Redis会话实现类,只有在认证通过之后才建立会话.
 * @author zhangcq
 *
 */
public class CTRedisSession implements ICTSession
{
	private Logger logger = Logger.getLogger(CTRedisSession.class);
	
	private String sessToken;				//有效token
	
	private int timeout=300;				//会话超时时间.
	private boolean isNew = false;			//是否有效.
	private boolean isValid = true;			//是否可用.
	
	private static long REDIS_SETOK_0 = 0;
	private static long REDIS_SETOK_1 = 1;
	
	private static String __BASE_INFO__ = "__BASE_InFO__";

	private ConcurrentHashMap<String, Object> dataMap;

	private RedisConnector connector = RedisConnector.getInstance();

	public CTRedisSession()
	{
		//create
		if ( sessToken == null )
		{
			isNew = true;
			UUID uuid = UUID.randomUUID();
			sessToken = uuid.toString().replace("-", "");
		}
		//else get
	}
	
	public CTRedisSession( String token )
	{
		sessToken = token;
	}
	
	public boolean init()
	{
		//如果是已有会话则监测是否超时
		//并重置超时时间.
		if ( !isNew )
		{
			String retmsg = connector.get(__BASE_INFO__);
			if ( retmsg == null )
			{
				logger.error("ERROR:无法获取会话基本信息.");
				return false;
			}
		}

		return true;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return connector.hget(sessToken,key,timeout);
	}

	@Override
	public boolean setValue(String key, String value) {
		// TODO Auto-generated method stub
		long retl = connector.hset(sessToken,key,value,timeout);
		return retl==REDIS_SETOK_0||retl==REDIS_SETOK_1?true:false;
	}

	@Override
	public Object getObj(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setObj(String key, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSessToken() {
		// TODO Auto-generated method stub
		return sessToken;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return isValid;
	}

	@Override
	public boolean destroySess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getEncKey(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setEncKey(String type, String encKey) {
		// TODO Auto-generated method stub
		return false;
	}
}
