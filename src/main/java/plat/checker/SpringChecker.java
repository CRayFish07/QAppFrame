package plat.checker;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import plat.bean.example.CustomerX;
import plat.config.spring.ConfigSpring;
import plat.frame.component.QConfig;
import plat.tools.XLog;

/**
 * 健康监测.
 * URL-ref:http://www.ibm.com/developerworks/cn/webservices/ws-springjava/
 * @author zhangcq
 *
 */
public class SpringChecker
{
	private static Logger logger = Logger.getLogger(SpringChecker.class);
	
	public static void main(String[] args)
	{
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ConfigSpring.class);
		logger.info("LOG4J worked.");
		
		/**
		*   ApplicationContext ctx = new AnnotationConfigApplicationContext();
  		    ctx.register(AppContext.class)
		*/
		
		testConfig(ctx);
	}

	private static void testBean(ApplicationContext context)
	{
		CustomerX customer = context.getBean(CustomerX.class);
		XLog.log("CustomerX.name="+customer.getName());
	}
	
	private static void testConfig( ApplicationContext context )
	{
		QConfig qconf = context.getBean(QConfig.class);
		XLog.log("%s,%s,%s", qconf.getTransPrefix(),qconf.getRSAConfigPath(),qconf.getAppName().length()+"");
	}
}
