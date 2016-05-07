package plat.frame.api.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 接口类型基本信息定义注解
 * @author zhangcq
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ClazzDefiner
{
	public String desc();		//描述.
	public Class<?> api();		//用于描述接口.
	public ENC_TYPE encrypt() default ENC_TYPE.DEFAULT;		//加密方式.
	public SESS_TYPE session() default SESS_TYPE.DEFAULT;	//会话类型
	public String group() default "";						//方法分组.
}
