package plat.frame.app;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import plat.frame.app.impl.UrlParseBean;

@Controller
public class AppEntry extends GeneralCallProxy
{
	private Logger logger = Logger.getLogger(AppEntry.class);
	
	/**
	 * 根据模块/类/方法名字进行调用.
	 * 模块只能写在包com.mbank.busi.中.
	 * @param request
	 * @param response
	 * @param model
	 * @param clz
	 * @param mtd
	 * @return
	 */
	@RequestMapping(value="/{transType}/{module}/{clazz}/{method}.mto" /*,method=RequestMethod.POST*/)
//	@ResponseBody
	public void callTargetMethod( HttpServletRequest request, HttpServletResponse response,
						@PathVariable String transType, @PathVariable String module,
						@PathVariable String clazz, @PathVariable String method )
	{
		UrlParseBean urlbean = new UrlParseBean();
		urlbean.setTransType(transType);
		urlbean.setModuleName(module);
		urlbean.setClazzName(clazz);
		urlbean.setMethodName(method);
		
		try
		{
			logger.info("__APP_RECV");
			String rspMsg = (String)callTargetMethod( request, urlbean );
			OutputStream os = response.getOutputStream();
			response.setContentType("text/html");
			os.write(rspMsg.getBytes("utf-8"));
			os.flush();
			os.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		return "fail";
	}
	
	@RequestMapping(value="/tr0/letsStart.do")
	@ResponseBody
	public String letsStart()
	{
		return "Let's Start Now!";
	}
}