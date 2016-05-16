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
	
}
