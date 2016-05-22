package plat.config.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import plat.config.springmvc.ConfigDispatcher;
import plat.tools.XLog;

public class MyWebAppInitializer implements WebApplicationInitializer
{
	//init SpringFrame.
    public void onStartup( ServletContext container)
    {
    	XLog.log("MyWebAppInitializer init start...");
/*        // Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext rootContext =
          new AnnotationConfigWebApplicationContext();
        rootContext.register(ConfigSpring.class);			//configure the Spring beans here.
*/
        XmlWebApplicationContext rootContext = new XmlWebApplicationContext();
        rootContext.setConfigLocation("classpath:spring-mybatis.xml");
        // Manage the lifecycle of the root application context
        ContextLoaderListener listener = new ContextLoaderListener(rootContext);
        container.addListener(listener);

        // Create the dispatcher servlet's Spring application context
        //including the basepgk-scan and so on.
        AnnotationConfigWebApplicationContext dispatcherContext =
          new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(ConfigDispatcher.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcherApp =
          container.addServlet( "AppDispatcher", new DispatcherServlet(dispatcherContext));
        dispatcherApp.setLoadOnStartup(1);
        dispatcherApp.addMapping("*.gmt");	//go transaction.
        dispatcherApp.addMapping("*.api");
        dispatcherApp.addMapping("*.java");
        dispatcherApp.addMapping("*.h");
      }
}
