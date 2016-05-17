package plat.config.springmvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * SpringMVC config
 * @author zhangcq
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"plat.testpkg","plat.frame.app","plat.frame.api.controller"})
public class ConfigDispatcher extends WebMvcConfigurerAdapter //WebMvcConfigurationSupport
{
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;
	}
}