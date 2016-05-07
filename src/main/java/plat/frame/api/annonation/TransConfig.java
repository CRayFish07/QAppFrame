package plat.frame.api.annonation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 运行时配置.
 * @author zhangcq
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TransConfig
{
	public ENC_TYPE encrypt() default ENC_TYPE.DEFAULT;			//加密方式
	public SESS_TYPE session() default SESS_TYPE.DEFAULT;		//会话类型
	public DEFEND_TYPE defend() default  DEFEND_TYPE.DEFAULT;	//是否防重
}
