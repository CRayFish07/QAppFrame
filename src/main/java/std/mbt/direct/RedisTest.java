package std.mbt.direct;

import java.io.IOException;
import java.util.Date;

import plat.tools.RandomUtil;
import plat.tools.XLog;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

public class RedisTest extends Thread
{
	public static String redisServerIp = "119.29.198.112";
	public static JedisPool pool = null;
	
	public RedisTest()
	{
		if ( pool == null )
		{
			JedisPoolConfig config = new JedisPoolConfig();
//			config.setBlockWhenExhausted(true);
			config.setMaxTotal(20);
			config.setMinIdle(10);
			config.setMaxWaitMillis(10);
			
			//5000ms超时是可以，默认是2000
			pool = new JedisPool(config,redisServerIp,60002,5000);
		}
	}
	
	String sendMsg = "{\"custNo\":\"00000013162\",\"custName\":\"凌欢\",\"idtype\":\"01\",\"idNo\":\"632324198303056355\",\"level\":\"2\",\"mobileNo\":\"13498741236\",\"gender\":\"M\",\"loginTime\":\"Jul 22, 2016 10:22:28 PM\",\"loginType\":\"M\",\"vipLevel\":\"0\",\"state\":\"0\",\"binddevice\":true,\"secListNum\":\"\",\"cardList\":[{\"cardNo\":\"6214461111000009082\",\"cardType\":\"BCSEBANK\",\"cardId\":\"N8200\",\"bankNo\":\"\",\"bankName\":\"长沙银行e账户\",\"mobileNo\":\"13498741236\",\"prodName\":\"\",\"accountName\":\"凌欢\",\"state\":\"0\",\"mark\":\"0\",\"nickName\":\"\"},{\"cardNo\":\"6223687310873691173\",\"cardType\":\"BCS\",\"cardId\":\"N7301\",\"bankNo\":\"013601\",\"bankName\":\"长沙银行借记卡\",\"mobileNo\":\"13498741236\",\"prodName\":\"\",\"accountName\":\"凌欢\",\"state\":\"0\",\"mark\":\"0\",\"nickName\":\"\"}],\"secToolList\":[]}"
	+"{\"custNo\":\"00000013162\",\"custName\":\"凌欢\",\"idtype\":\"01\",\"idNo\":\"632324198303056355\",\"level\":\"2\",\"mobileNo\":\"13498741236\",\"gender\":\"M\",\"loginTime\":\"Jul 22, 2016 10:22:28 PM\",\"loginType\":\"M\",\"vipLevel\":\"0\",\"state\":\"0\",\"binddevice\":true,\"secListNum\":\"\",\"cardList\":[{\"cardNo\":\"6214461111000009082\",\"cardType\":\"BCSEBANK\",\"cardId\":\"N8200\",\"bankNo\":\"\",\"bankName\":\"长沙银行e账户\",\"mobileNo\":\"13498741236\",\"prodName\":\"\",\"accountName\":\"凌欢\",\"state\":\"0\",\"mark\":\"0\",\"nickName\":\"\"},{\"cardNo\":\"6223687310873691173\",\"cardType\":\"BCS\",\"cardId\":\"N7301\",\"bankNo\":\"013601\",\"bankName\":\"长沙银行借记卡\",\"mobileNo\":\"13498741236\",\"prodName\":\"\",\"accountName\":\"凌欢\",\"state\":\"0\",\"mark\":\"0\",\"nickName\":\"\"}],\"secToolList\":[]}";
	
	public static void main(String[] args)
	{
		for ( int i = 0; i < 10; ++i )
		{
			RedisTest redis = new RedisTest();
//			XLog.log("__ID=%d", redis.pool.hashCode());
			redis.start();
		}
/*		XLog.log("-------------");
		for ( int i = 0; i < 10; ++i )
		{
			RedisTest redis = new RedisTest();
			XLog.log("__ID=%d", redis.pool.hashCode());
			redis.start();
		}*/
	}
	
	public void doRedisTask() throws IOException
	{

//		XLog.log("__CONFIG:%s", JsonCoder.toJsonString(config));
		Jedis jedis = null;
		try
		{
			Date time0 = new Date();
			jedis = pool.getResource();
			/// ... do stuff here ... for example
			Date time1 = new Date();
			
			XLog.log("%s:%d", "getResource",time1.getTime()-time0.getTime());
			
			String key = RandomUtil.getRandomSequence(20);
			
			Pipeline pl = jedis.pipelined();
			pl.set(key, sendMsg);
			pl.expire(key, 300);
			pl.sync();
			pl.close();
			
			Date time2 = new Date();
			XLog.log("%s:%d", "setKey-expire",time2.getTime()-time1.getTime());

			pl = jedis.pipelined();
			pl.expire(key, 300);
			pl.sync();
			Response<String> reponse = pl.get(key);
			pl.close();
			
			String retMsg = reponse.get();
			Date time3 = new Date();
			XLog.log("%s:%d", "getKey-expire",time3.getTime()-time2.getTime());
/*			jedis.zadd("sose", 0, "car");
			jedis.zadd("sose", 0, "bike"); 
			Set<String> sose = jedis.zrange("sose", 0, -1);*/
		}
		finally
		{
			if (jedis != null) {
				jedis.close();
			}
		}
		
		/// ... when closing your application:
		pool.destroy();
	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			doRedisTask();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
