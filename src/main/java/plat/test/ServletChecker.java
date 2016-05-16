package plat.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/***
 * 健康监测Servlet Tomcat
 * @author zhangcq
 *
 */
@WebServlet(name="Health",urlPatterns="/hello")
public class ServletChecker extends HttpServlet
{
	 public void doGet( HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException {
		 
		 HttpSession session = request.getSession();
		 
		 session.setMaxInactiveInterval(10);
		 session.setAttribute("flag", "s3demo");
		 
		 System.out.println("session:"+session.getId());
		 
	     response.getWriter().write("Qc Boot succeed, sir.");
	 }

	 public void doPost(HttpServletRequest request, HttpServletResponse response)
	         throws ServletException, IOException {
	     this.doGet(request, response);
	 }
}
