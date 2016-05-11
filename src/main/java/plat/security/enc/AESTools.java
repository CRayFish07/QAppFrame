package plat.security.enc;

import plat.constant.KResponse;
import plat.frame.app.AppException;
import plat.tools.BaseCoder;
import plat.tools.StringUtil;

public class AESTools
{
	/**
	 * 
	 * @param plaintext
	 * @param keystr
	 * @return base64编码后的密文.
	 * @throws Exception
	 */
	public static String encrypt( String plaintext, String keystr ) throws Exception
	{
		if ( StringUtil.isEmpty(keystr) )
		{
			throw new Exception("keystr is empty.");
		}
		
		byte[] encryptData = null;
		try
		{
			encryptData = AESEncer.encrypt( plaintext.getBytes("utf-8"), keystr,AESEncer.DEFAULT_CIPHER_ALGORITHM );
			return BaseCoder.toBase64String(encryptData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * AES解密函数.
	 * @param base64data
	 * @param keyStr
	 * @return utf8解码后的字符串.
	 * @throws Exception
	 */
	public static String decrypt( String base64data, String keyStr ) throws Exception
	{
		if ( StringUtil.isEmpty(keyStr) )
		{
			throw new AppException(KResponse.INPUT_ERROR,"AES keystr is empty.");
		}
		
		// 交易报文解密
		byte[] dataArr = null ;
		byte[] dencryptData = null;
		try
		{
			dataArr = BaseCoder.fromBase64String(base64data);
			dencryptData = AESEncer.decrypt(dataArr, keyStr, AESEncer.DEFAULT_CIPHER_ALGORITHM );
			return new String( dencryptData, "utf-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public static void log( String lgmsg )
	{
		Thread currTh = Thread.currentThread();
		System.out.println( ""+currTh.getId()+"-"+currTh.getName()+":"+lgmsg );
	}
	
	public static void main(String args[] ) throws Exception
	{
		String text = "有中文hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.";
		String keyStr = "0123456789123456";
		
		String encStr = encrypt(text,keyStr);
		
		text = decrypt(encStr, keyStr);
		
		System.out.println("R1::"+text);
	}
}
