package plat.security.enc;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 1. RSA通过cer文件加密
 * 2. RSA通过指数模数加密.
 * @author zhangcq
 *
 */
public class RSASecurity
{
	//加密算法定义.
	private static String AL_NOPADDING = "RSA/ECB/NoPadding";
	private static String AL_PKCSPADDING = "RSA/ECB/PKCS1Padding";
	
	/**********
	 * 
	 * @param data
	 * @param cerfile
	 * @return
	 * @throws Exception
	 * 
	 * 0. PKCS1Padding 的最大加密块大小是117字节.
	 * 1. 加密后密文长度仍旧为128字节.
	 * 2. 会自动按照特定结构进行填充，其中11字节包含了填充结构信息.
	 * 3. 如果不满117字节，那么会进行随机填充。所以每次加密密文都不一样.
	 * 4. 解密后的有效明文是原明文.
	 * 
	 */
	public static byte[] encryDataPKCS1Padding( byte[] data, String cerfile ) throws Exception
	{
		return encryData( data, AL_PKCSPADDING, 100, cerfile );
	}

	/******
	 * 
	 * @param data
	 * @param cerfile
	 * @return
	 * @throws Exception
	 * 
	 * 1. NoPadding 的最大加密块大小是128字节.
	 * 2. 不满128字节的数据会在前面补充相应字节数的0x00;
	 * 3. 所以如果数据中携带了0x00开头的数据，那么该部分数据就难以区分.
	 * 	    需要知道长度或者信息.
	 * 4. 解密后仍为填充后的128字节,所以无法区分出原有效明文.
	 * 5. 建议自行填充到128字节.
	 * 
	 * 所以相同明文加密出来的明文都是一样的.
	 */
	public static byte[] encryDataNoPadding( byte[] data, String cerfile ) throws Exception
	{
		return encryData( data, AL_NOPADDING, 128, cerfile );
	}

	/* 
	 * 1024bits RSA密钥加密
	 * @data 加密数据
	 * @module 模数
	 * @exp 指数 
	 */
	public static byte[] encryDataPKCS1Padding( byte[] data, String module, String exp ) throws Exception
	{
		return encryData( data, AL_PKCSPADDING, 100, module, exp );
	}

	/* 
	 * 1024bits RSA密钥加密
	 * @data 加密数据
	 * @module 模数
	 * @exp 指数 
	 */
	public static byte[] encryDataNoPadding( byte[] data, String module, String exp ) throws Exception
	{
		return encryData( data, AL_NOPADDING, 128, module, exp );
	}

	/*@data 加密数据
	 *@al 加密算法
	 *@blocksize 分组大小
	 *@module 模数
	 *@exp 指数 
	 */
	private static byte[] encryData(byte[] data, String al, int blocksize, String module, String exp ) throws Exception
	{
		//获取公钥
		RSAPublicKey pubKey = RSAUtils.getPubKey(module,exp);

		//获得加密类
		Cipher cipher = Cipher.getInstance(al);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		//进行分组加密
		int fix = blocksize;
		byte[] cipherdata = null,tmp = null;
		for (int i = 0; i * fix < data.length; i++ )
		{
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix, Math.min(data.length, (i + 1) * fix )));
			cipherdata = ArrayUtils.addAll(cipherdata, tmp);
		}

		return cipherdata;
	}

	/************
	 * 
	 * @param data
	 * @param al
	 * @param blocksize
	 * @param cerfile
	 * @return
	 * @throws Exception
	 */
	private static byte[] encryData(byte[] data, String al, int blocksize, String cerfile ) throws Exception
	{
		//获取公钥
		RSAPublicKey pubKey = RSAUtils.getPubKey(cerfile);

		//获得加密类
		Cipher cipher = Cipher.getInstance(al);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		//进行分组加密
		int fix = blocksize;
		byte[] cipherdata = null,tmp = null;
		for (int i = 0; i * fix < data.length; i++ )
		{
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix, Math.min(data.length, (i + 1) * fix )));
			cipherdata = ArrayUtils.addAll(cipherdata, tmp);
		}

		return cipherdata;
	}
	
	public static byte[] dencryptDataNoPadding(byte[] data,String jksfile, String storepwd, String keyalias, String keypwd ) throws Exception
	{
		return dencryptData( data, AL_NOPADDING, jksfile, storepwd, keyalias, keypwd );
	}

	public static byte[] dencryptDataPKCS1Padding( byte[] data, String jksfile, String storepwd, String keyalias, String keypwd ) throws Exception
	{
		return dencryptData( data, AL_PKCSPADDING, jksfile, storepwd, keyalias, keypwd );
	}

	public static byte[] dencryptDataPKCS1Padding(byte[] data, String module, String exp ) throws Exception
	{
		return dencryptData( data, AL_PKCSPADDING, module, exp );
	}

	public static byte[] dencryptDataNoPadding(byte[] data, String module, String exp ) throws Exception
	{
		return dencryptData( data, AL_NOPADDING, module, exp );
	}

	private static byte[] dencryptData(byte[] data, String al, String module, String exp ) throws Exception
	{

		Cipher cipher = Cipher.getInstance(al);

		RSAPrivateKey priKey = RSAUtils.getPriKey(module,exp );
		cipher.init(Cipher.DECRYPT_MODE, priKey );

		// 1024bits密钥加密出来的都是128byte的密文.
		// 所以和加密的分组是不同的。无论加密分组大小是1或者128字节，
		// 加密密文都是128字节
		int fix = 128;
		byte[] plain  = null, tmp = null;

		for (int i = 0; i * fix < data.length; i++) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix, Math.min(data.length, (i + 1) * fix)));
			plain = ArrayUtils.addAll(plain, tmp);
		}

		return plain;
	}

	private static byte[] dencryptData(byte[] data, String al,String jksfile, String storepwd, String keyalias, String keypwd ) throws Exception
	{
		Cipher cipher = Cipher.getInstance(al);

		RSAPrivateKey priKey = RSAUtils.getPriKey(jksfile, storepwd, keyalias, keypwd);
		cipher.init(Cipher.DECRYPT_MODE, priKey );

		/***
		 * 1024bits密钥加密出来的都是128byte的密文.
		 * 所以和加密的分组是不同的。无论加密分组大小是1或者128字节，
		 * 加密密文都是128字节
		 */
		int fix = 128;
		byte[] plain  = null, tmp = null;

		for (int i = 0; i * fix < data.length; i++) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix, Math.min(data.length, (i + 1) * fix)));
			plain = ArrayUtils.addAll(plain, tmp);
		}

		return plain;
	}

	/**
	 * 将16进制明文转换成byte[]
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;

	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * 六位数字密码加密算法
	 * 
	 * @param result
	 * @return
	 */
	public static String EncryptToNumber(String result) {
		int pwLen = result.length();

		String addLenPw = "0" + pwLen + result;
		System.out.println(addLenPw);
		StringBuffer sb = new StringBuffer(); // 最终的字符串

		char c = 0; // 每个字符串
		String byteStr = "";
		for (int i = 0; i < addLenPw.length(); i++) {
			c = addLenPw.charAt(i);
			String st = String.valueOf(c);
			byte[] bytes = st.getBytes();
			for (int j = 0; j < bytes.length; j++) {
				byteStr = Integer.toHexString(bytes[j] & 0xff);
				sb.append(byteStr);
			}
		}
		/* 尾部填充0 */
		int length = sb.length();
		// for (int i = result.length(); i < 1408 / 8 * 2 - byteStr.length();
		// i++) {
		for (int i = 0; i < 1408 / 8 * 2 - length; i++) {
			sb.append("0");
		}
		result = sb.toString();
		return result;
	}

	/**
	 * 四至十六位字符密码填充方式
	 * 
	 * @param result
	 * @return
	 */
	public static String EncryptToString(String result) {
		StringBuffer sb = new StringBuffer(); // 最终的字符串

		char c = 0; // 每个字符串
		String byteStr = "";
		for (int i = 0; i < result.length(); i++) {
			c = result.charAt(i);
			String st = String.valueOf(c);
			byte[] bytes = st.getBytes();
			for (int j = 0; j < bytes.length; j++) {
				byteStr = Integer.toHexString(bytes[j] & 0xff);
				sb.append(byteStr);
			}
		}
		/** 前面填充0 */
		String num = "";
		for (int i = 0; i < 1408 / 8 * 2 - sb.length(); i++) {
			num += "0";
		}
		result = num + sb.toString().toUpperCase();
		return result;
	}

	/**
	 * 六位数字的信用卡查询密码加密算法
	 * 
	 * @param result
	 * @return
	 */
	public static String EncryptToLetter(String result) {

		String addLenPw = "FFFFFF" + result;
		System.out.println(addLenPw);
		StringBuffer sb = new StringBuffer(); // 最终的字符串

		char c = 0; // 每个字符串
		String byteStr = "";
		for (int i = 0; i < addLenPw.length(); i++) {
			c = addLenPw.charAt(i);
			String st = String.valueOf(c);
			byte[] bytes = st.getBytes();
			for (int j = 0; j < bytes.length; j++) {
				byteStr = Integer.toHexString(bytes[j] & 0xff);
				sb.append(byteStr);
			}
		}
		/** 前面填充0 */
		String num = "";
		for (int i = 0; i < 1408 / 8 * 2 - sb.length(); i++) {
			num += "0";
		}
		result = num + sb.toString().toUpperCase();
		return result;
	}
}
