package plat.security.enc;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES Coder<br/>
 * secret key length: 128bit, default: 128 bit<br/>
 * mode: ECB/CBC/PCBC/CTR/CTS/CFB/CFB8 to CFB128/OFB/OBF8 to OFB128<br/>
 * padding: Nopadding/PKCS5Padding/ISO10126Padding/
 * 
 * @author Aub
 * 
 */
public class AESEncer
{
	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";
	private static String IV = "PCNXSJYHJSNXSJYH";
	
	//填充规则
	public static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

	/**
	 * 
	 * @param data
	 * @param keyStr
	 * @param cipherAlgorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt( byte[] data, String keyStr, String cipherAlgorithm )
			throws Exception
	{
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		Key key =new SecretKeySpec(keyStr.getBytes("UTF-8"), KEY_ALGORITHM);
		
		// 使用密钥初始化，设置为加密模式
		//cipher.init(Cipher.ENCRYPT_MODE, key);
		cipher.init(Cipher.ENCRYPT_MODE, key,
				new IvParameterSpec(IV.getBytes("UTF-8")));
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * @param data
	 * @param keyStr
	 * @param cipherAlgorithm
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String keyStr, String cipherAlgorithm)
			throws Exception
	{
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		Key key =new SecretKeySpec(keyStr.getBytes("UTF-8"), KEY_ALGORITHM);
		
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key,
				new IvParameterSpec(IV.getBytes("UTF-8")));
		// 执行操作
		return cipher.doFinal(data);
	}

	public static void main(String[] args) throws Exception
	{
		String text = "hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.";
		String keyStr = "0123456789123456";
		byte[] encData = encrypt(text.getBytes(),keyStr,DEFAULT_CIPHER_ALGORITHM);
		
		text = new String( decrypt(encData, keyStr, DEFAULT_CIPHER_ALGORITHM),"utf-8");
		
		System.out.println("R::"+text);
	}
}