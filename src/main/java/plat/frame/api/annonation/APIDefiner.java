package plat.frame.api.annonation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 生成环境需要变更RetentionPolicy为SOURCE
 * 该注解可用于方法和接口bean中.
 * @author zhangcq
 *
 */
//@Target(ElementType.TYPE)
//@Retention(RetentionPolicy.SOURCE) // 建议生产的时候源码级别
@Retention(RetentionPolicy.RUNTIME)
public @interface APIDefiner
{
	public String name();
	public String version();
	public String[] desc() default {};		//接口说明.
	public String[] updates() default {};
}
