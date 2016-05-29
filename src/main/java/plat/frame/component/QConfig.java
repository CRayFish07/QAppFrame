package plat.frame.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QConfig
{
	@Value("${transPrefix:trans}")
	private String transPrefix;
	
	@Value("${appName:}")
	private String appName;
	
	@Value("${RSAConfigPath}")
	private String RSAConfigPath;
	
	@Value("${DEBUG.apiPackage:plat.frame.api.release}")
	private String apiPackage;
	
	@Value("${DEBUG.apiHostURL:http://localhost:8080/QAppFrame}")
	private String apiHostURL;
	
	@Value("${jdbc.driver}")
	private String jdbcDriver;
	
	@Value("${redis.server:localhost}")
	private String redisServer;
	
	@Value("${redis.port:6379}")
	private int redisPort;
	
	//客户端连接数
	@Value("${redis.maxConn:12}")
	private int redisMaxConn;
	
	//等待可用连接的最大超时时间.
	@Value("${redis.maxTimeOut:5000}")
	private	int redisTimeOut;
	
	//最大等待时间
	@Value("${redis.MaxWaitMillis:8000}")
	private int redisMaxAwaitMillSec;

	public String getTransPrefix() {
		return transPrefix;
	}

	public void setTransPrefix(String transPrefix) {
		this.transPrefix = transPrefix;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRSAConfigPath() {
		return RSAConfigPath;
	}

	public void setRSAConfigPath(String rSAConfigPath) {
		RSAConfigPath = rSAConfigPath;
	}

	public String getApiPackage() {
		return apiPackage;
	}

	public void setApiPackage(String apiPackage) {
		this.apiPackage = apiPackage;
	}

	public String getApiHostURL() {
		return apiHostURL;
	}

	public void setApiHostURL(String apiHostURL) {
		this.apiHostURL = apiHostURL;
	}

	public String getJdbcDriver() {
		return jdbcDriver;
	}

	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
	}

	public String getRedisServer() {
		return redisServer;
	}

	public void setRedisServer(String redisServer) {
		this.redisServer = redisServer;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public int getRedisMaxConn() {
		return redisMaxConn;
	}

	public void setRedisMaxConn(int redisMaxConn) {
		this.redisMaxConn = redisMaxConn;
	}

	public int getRedisTimeOut() {
		return redisTimeOut;
	}

	public void setRedisTimeOut(int redisTimeOut) {
		this.redisTimeOut = redisTimeOut;
	}

	public int getRedisMaxAwaitMillSec() {
		return redisMaxAwaitMillSec;
	}

	public void setRedisMaxAwaitMillSec(int redisMaxAwaitMillSec) {
		this.redisMaxAwaitMillSec = redisMaxAwaitMillSec;
	}
}
