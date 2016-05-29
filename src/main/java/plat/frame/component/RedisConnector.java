package plat.frame.component;

import java.io.IOException;
import java.util.MissingResourceException;

import plat.constant.KResponse;
import plat.tools.JsonCoder;
import plat.tools.MyPropertiesReader;
import plat.tools.RandomUtil;
import plat.tools.XLog;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class RedisConnector
{
	private JedisPool pool;

	private String proName = "qdbase";

	private RedisConnector()
	{
		//这里还是依赖了Spring哦.
		//		QConfig qconfig = AppContextHolder.getContext().getBean(QConfig.class);
		
		String server = null;
		int port=0,maxTotal=0,minIdle=0, maxWaitMills=0,timeout=0;
		
		try
		{
			MyPropertiesReader proReader = new MyPropertiesReader(proName);
			server = proReader.getString("redis.server","localhost");
			port = proReader.getInt("redis.port",6379);
			maxTotal = proReader.getInt("redis.maxConn",10);
			minIdle = proReader.getInt("redis.minIdle",5);
			maxWaitMills = proReader.getInt("redis.MaxWaitMillis",3000);
			timeout = proReader.getInt("redis.maxTimeOut",5000);
		}
		catch( MissingResourceException e )
		{
			e.printStackTrace();
		}

		JedisPoolConfig config = new JedisPoolConfig();
		//		config.setBlockWhenExhausted(true);
		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMills);

		XLog.log("__INIT:RedisConnector:redis-config=%s", JsonCoder.toJsonString(config));

		//5000ms超时是可以，默认是2000
		pool = new JedisPool(config,server,port,timeout);
	}

	private static final class RedisConnectorHolder
	{
		private static final RedisConnector  connector = new RedisConnector();
	}

	public static RedisConnector getInstance()
	{
		return RedisConnectorHolder.connector;
	}

	public static void main(String[] args)
	{
		String sendMsg = "{\"custNo\":\"00000013162\",\"custName\":\"凌欢\",\"idtype\":\"01\",\"idNo\":\"632324198303056355\",\"level\":\"2\",\"mobileNo\":\"13498741236\",\"gender\":\"M\",\"loginTime\":\"Jul 22, 2016 10:22:28 PM\",\"loginType\":\"M\",\"vipLevel\":\"0\",\"state\":\"0\",\"binddevice\":true,\"secListNum\":\"\",\"cardList\":[{\"cardNo\":\"6214461111000009082\",\"cardType\":\"BCSEBANK\",\"cardId\":\"N8200\",\"bankNo\":\"\",\"bankName\":\"长沙银行e账户\",\"mobileNo\":\"13498741236\",\"prodName\":\"\",\"accountName\":\"凌欢\",\"state\":\"0\",\"mark\":\"0\",\"nickName\":\"\"},{\"cardNo\":\"6223687310873691173\",\"cardType\":\"BCS\",\"cardId\":\"N7301\",\"bankNo\":\"013601\",\"bankName\":\"长沙银行借记卡\",\"mobileNo\":\"13498741236\",\"prodName\":\"\",\"accountName\":\"凌欢\",\"state\":\"0\",\"mark\":\"0\",\"nickName\":\"\"}],\"secToolList\":[]}";

		RedisConnector redis = RedisConnector.getInstance();
		
		for ( int i=0; i < 10; ++i )
		{
			String key = RandomUtil.getRandomSequence(10);
			redis.setValue(key, sendMsg);
		}
	}
	
	//---------------getter-settter-operations--------------
	/**
	 * 设置值并超时时间.
	 * @param key
	 * @param value
	 * @param timeOutSec
	 * @return
	 */
	public boolean setValue( String key, String value, int timeOutSec )
	{
		Jedis jedis = pool.getResource();
		
		Pipeline pl = jedis.pipelined();
		Response<String> response = pl.set(key, value);
		pl.expire(key, timeOutSec );
		pl.sync();
		try
		{
			pl.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if ( !KResponse.RET_OK.equals(response.get()))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 设置值
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setValue( String key, String value )
	{
		Jedis jedis = pool.getResource();
		String retMsg = jedis.set(key, value);
		if ( !KResponse.RET_OK.equals(retMsg))
		{
			return false;
		}
		
		return true;
	}
	
	/**
	 * 返回值并更新超时时间.
	 * @param key
	 * @param value
	 * @param timeOutSec
	 * @return
	 */
	public String getValue( String key, String value, int timeOutSec )
	{
		Jedis jedis = pool.getResource();
		
		Pipeline pl = jedis.pipelined();
		Response<String> response = pl.get(key);
		pl.expire(key, timeOutSec );
		pl.sync();
		try
		{
			pl.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response.get();
	}

	/**
	 * 获取值.
	 * @param key
	 * @param value
	 * @return
	 */
	public String getValue( String key, String value )
	{
		Jedis jedis = pool.getResource();
		String retMsg = jedis.get(key);
		
		return retMsg;
	}
}
