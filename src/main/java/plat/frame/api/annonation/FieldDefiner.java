package plat.frame.api.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段接口基本信息定义注解
 * @author zhangcq
 *
 */
@Target(ElementType.FIELD)
//@Retention(RetentionPolicy.SOURCE)  //建议生产的时候源码级别
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldDefiner
{
	public String name();						//中文名称
	public boolean required()	default false;	//是否必输
	public String enums()		default "" ;	//枚举值
	public String desc()		default "";		//描述
	public Class<?>[] classT() default {};		//用于泛型指定,如List<CardInfo> carlist;则需要标注CardInfo.class
}
