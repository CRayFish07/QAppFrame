package plat.frame.api.annonation;

/**
 * encryption type.
 * @author zhangcq
 *
 */
public enum ENC_TYPE {
	AES_SS,			//协商密钥AES加密
	RSA_NS,			//公钥加密
	PLAIN,			//明文
	DEFAULT			//该值无实际意义,仅仅用于注解初始化.
}
