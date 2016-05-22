package plat.frame.app;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import plat.constant.KResponse;
import plat.frame.api.annonation.DEFEND_TYPE;
import plat.frame.api.annonation.ENC_TYPE;
import plat.frame.api.annonation.SESS_TYPE;
import plat.frame.api.annonation.TransConfig;
import plat.frame.api.meta.QBaseBean;
import plat.frame.app.define.MessageType;
import plat.frame.app.impl.TransContext;
import plat.frame.app.msg.ReqMessageHead;
import plat.tools.PropertiesReader;
import plat.tools.StringUtil;
import plat.tools.net.HttpServletProfessional;

public class HttpServletyReader
{
	private Logger logger = Logger.getLogger(HttpServletyReader.class);
	
	//报文读取工具.
	private HttpServletProfessional httptool = null;
	
	//读取报文的大小 1M
	private int maxpkg = 1024*1024;
	
	/**
	 * 
	 * @param request
	 * @param charset
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	protected String getInputString( HttpServletRequest request, String charset ) throws IOException, InterruptedException
	{
		byte[] indata = getInputStream(request);
		if ( indata != null && indata.length != 0 )
		{
			return new String( indata, charset );
		}
		
		return "";
	}
	
	protected byte[] getInputStream( HttpServletRequest request ) throws IOException, InterruptedException
	{
		//没同步.
		if ( httptool == null )
		{
			httptool = HttpServletProfessional.getInstance().setMaxlen(maxpkg);
		}
		
		return httptool.readInput(request);
	}
}
