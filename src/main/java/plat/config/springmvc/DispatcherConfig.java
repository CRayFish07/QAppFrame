package plat.config.springmvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SpringMVC config
 * @author zhangcq
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"plat.testpkg","plat.frame.app"})
public class DispatcherConfig extends WebMvcConfigurerAdapter //WebMvcConfigurationSupport
{
}