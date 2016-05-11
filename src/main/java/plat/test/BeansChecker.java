package plat.test;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import plat.bean.example.CustomerX;
import plat.config.beans.BeansConfig;
import plat.config.beans.XLog;

/**
 * 健康监测.
 * URL-ref:http://www.ibm.com/developerworks/cn/webservices/ws-springjava/
 * @author zhangcq
 *
 */
public class BeansChecker
{
	private static Logger logger = Logger.getLogger(BeansChecker.class);
	
	public static void main(String[] args)
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(BeansConfig.class);
		CustomerX customer = ctx.getBean(CustomerX.class);
		System.out.println("CustomerX.name="+customer.getName());
		
		logger.info("LOG4J worked.");
		
		/**
		*   ApplicationContext ctx = new AnnotationConfigApplicationContext();
  		    ctx.register(AppContext.class)
		*/
	}
}
