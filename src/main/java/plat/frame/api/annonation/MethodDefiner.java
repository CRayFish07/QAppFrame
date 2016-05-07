package plat.frame.api.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * service加密请求加密方式定义注解
 * @author zhangcq
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodDefiner
{
	public String name();										//方法名称
	public String[] desc() default {};							//方法描述
	public Class<?>[] api() default {};							//请求接口名称 ReqUserInfo/RspLogin
}
