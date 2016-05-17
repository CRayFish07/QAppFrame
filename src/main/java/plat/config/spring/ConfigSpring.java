package plat.config.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import plat.bean.example.CustomerX;
import plat.frame.app.AppContextHolder;

/**
 * Spring config.
 * @author zhangcq
 *
 */
@Configuration
@ComponentScan(basePackages={"plat.frame.component"})
public class ConfigSpring
{
	@Bean
	public CustomerX customerX()
	{
		CustomerX cx = new CustomerX();
		cx.setName("Tom");
		return cx;
	}
	
	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer()
	{
		PropertyPlaceholderConfigurer conf = new PropertyPlaceholderConfigurer();
		ClassPathResource clr = new ClassPathResource("qconf.properties");
		conf.setLocation(clr);
		return conf;
	}
	
	@Bean
	public AppContextHolder getAppContextHolder()
	{
		AppContextHolder holder = new AppContextHolder();
		return holder;
	}
}
