package plat.config.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import plat.bean.example.CustomerX;

/**
 * ����Spring��beans.
 * @author zhangcq
 *
 */
@Configuration
public class BeansConfig
{
	@Bean
	public CustomerX customerX()
	{
		CustomerX cx = new CustomerX();
		cx.setName("Tom");
		return cx;
	}
}
