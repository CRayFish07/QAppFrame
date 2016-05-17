package plat.frame.app.impl;

import plat.tools.XLog;

public class URLMapper
{
	public static String urlTail = "gmt";

	private String appName;
	private String transPrefix;
	
	private String moduleName;		//package-path.
	private String clazzName;
	private String methodName;
	
	public URLMapper( String appName_, String transPrefix_ )
	{
		appName = appName_;
		transPrefix = transPrefix_;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getClazzName() {
		return clazzName;
	}

	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * parse the URL to find the target the method,
	 * so we need package-path,class-name,method-name.
	 * parse rules as below:
	 * http://www.example.com/webapp0/login/login.gmt
	 * 			---URI--->/webapp0/login/login.gmt
	 * 				---map2method--> trans.LoginTrans.login(...)
	 * @param uri
	 */
	public void doParse( String uri )
	{
		XLog.log("URI=%s", uri);
		//ignore the app_name if have one.
		int startIndex = 1 + (appName.length()==0?0:appName.length()+1);
		String[] parts = uri.substring(startIndex, uri.length()-4).split("/");
		
		methodName=parts[parts.length-1];
		clazzName=parts[parts.length-2];
		
		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append(transPrefix).append(".");
		for ( int i = 0; i < parts.length-2;++i )
		{
			XLog.log("parts[%d][%s]", i,parts[i]);
			sbuffer.append(parts[i]).append(".");
		}
		
		moduleName = sbuffer.toString();
		XLog.log("moduleName=%s", moduleName);
	}
}
