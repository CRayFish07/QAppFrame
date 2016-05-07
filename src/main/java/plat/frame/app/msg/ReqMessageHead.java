package plat.frame.app.msg;

/***
 * App发送到服务端的请求报文解析.
 * @author zhangcq
 *
 */
public class ReqMessageHead
{
	//报文头编号
	private String headVersion;	
	
	//渠道代号
	private String channelCode;
	
	//系统类型
	private String osType;
	
	//系统类型
	private String appVersion;
	
	//token
	private String token;
	
	//设备唯一标示
	private String devKey;
	
	//设备唯一标示
	private String devLogId;
	
	//防重域
	private String rocket;
	
	////返回报文加密密钥.
	private String ck;
	
	public String getHeadVersion() {
		return headVersion;
	}
	public void setHeadVersion(String headVersion) {
		this.headVersion = headVersion;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDevKey() {
		return devKey;
	}
	public void setDevKey(String devKey) {
		this.devKey = devKey;
	}
	public String getDevLogId() {
		return devLogId;
	}
	public void setDevLogId(String devLogId) {
		this.devLogId = devLogId;
	}
	public String getRocket() {
		return rocket;
	}
	public void setRocket(String rocket) {
		this.rocket = rocket;
	}
	public String getCk() {
		return ck;
	}
	public void setCk(String ck) {
		this.ck = ck;
	}
}
