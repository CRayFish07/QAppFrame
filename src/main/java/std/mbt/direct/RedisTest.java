package std.mbt.direct;

import java.util.Set;

import plat.tools.JsonCoder;
import plat.tools.XLog;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest
{
	public static String redisServerIp = "119.29.198.112";
	public static void main(String[] args)
	{}
	
	public void doRedisTask()
	{

		JedisPoolConfig config = new JedisPoolConfig();
		JedisPool pool = new JedisPool(config,redisServerIp,60002);
		
		XLog.log("__CONFIG:%s", JsonCoder.toJsonString(config));

		Jedis jedis = null;
		try
		{
			jedis = pool.getResource();
			/// ... do stuff here ... for example
			jedis.set("foo", "bar11111");
			String foobar = jedis.get("foo");
			jedis.zadd("sose", 0, "car");
			jedis.zadd("sose", 0, "bike"); 
			Set<String> sose = jedis.zrange("sose", 0, -1);
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
}
