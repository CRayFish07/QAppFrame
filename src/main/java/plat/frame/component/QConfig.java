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
}
