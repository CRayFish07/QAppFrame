package plat.frame.app;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContextHolder implements ApplicationContextAware
{
	Logger logger = Logger.getLogger(AppContextHolder.class);
	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		context = arg0;
		
		logger.info("__INIT:AppContextHolder init succeed, context.hashcode="+context.hashCode());
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		AppContextHolder.context = context;
	}
	
}
