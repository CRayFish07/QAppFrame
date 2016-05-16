package plat.test;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import plat.tools.XLog;

@Controller
public class MVCChecker
{
	@RequestMapping(name="/hellomvc.do")
	private void helloMVC( HttpServletRequest request, HttpServletResponse response )
	{
		String retMsg = "Hello SpringMVC!";
		
		XLog.log("%s", retMsg);
		response.setContentType("text/html");
		
		OutputStream ous;
		try {
			ous = response.getOutputStream();
			ous.write(retMsg.getBytes());
			ous.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
