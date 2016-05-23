package plat.frame.app;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import plat.frame.app.impl.URLMapper;
import plat.frame.component.QConfig;

@Controller
public class AppEntry extends HttpInvokeProxy
{
	private Logger logger = Logger.getLogger(AppEntry.class);
	
	@Autowired
	private QConfig qconf;
	
	/**
	 * test, get rid of it when on release.
	 * @return
	 */
	@RequestMapping(value="/helloq.gmt",method=RequestMethod.GET)
	@ResponseBody
	public String letsStart()
	{
		return "Let's do the left!";
	}
	
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
	@RequestMapping(value="/**/*.gmt",method=RequestMethod.POST)
//	@ResponseBody
	public void appProxy( HttpServletRequest request, HttpServletResponse response )
	{
		logger.info("__APP_RECV_gmt");

		//URL解析
		URLMapper urlMapper = new URLMapper(qconf.getAppName(), qconf.getTransPrefix());
		urlMapper.doParse(request.getRequestURI());

		OutputStream os = null;
		try
		{
			String rspMsg = (String)callTargetMethod( request, urlMapper );
			os = response.getOutputStream();
			response.setContentType("text/html");
			os.write(rspMsg.getBytes("utf-8"));
			os.flush();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				os.close();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		return "fail";
	}
}