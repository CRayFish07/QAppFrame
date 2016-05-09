package plat.security.enc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Properties;

import plat.constant.KBankApp;
import plat.tools.BaseCoder;
import plat.tools.PropertiesReader;
import plat.tools.StringUtil;

public class RSATools
{
	/*************** 证书私钥 bankofchangsha *******************/
	private static String RSAConfigPath = "RSAConfigPath"; //密钥配置文件.
	
	/********* 密钥信息 ************/
	private BigInteger module;		//密码模数（即大数的乘积)
	private BigInteger pubexp;		//公钥指数
	private BigInteger priexp;		//私钥指数.
	
	private boolean support = false;
	
	private RSATools()
	{
		long startTime = new Date().getTime();
		initSec();
		long endTime = new Date().getTime();
		log("ENC-INIT:"+(endTime - startTime));
	}
	
	private static final class SingleHolder
	{
		private static RSATools rsatool = new RSATools();
	}
	
	public static RSATools getInstance()
	{
		return SingleHolder.rsatool;
	}
	
	/**
	 * 解密base64之后的数据.
	 * @param base64data
	 * @return
	 * @throws Exception
	 */
	public byte[] dencryptData( String base64data ) throws Exception
	{
		return RSAEncer.dencryptData( BaseCoder.fromBase64String(base64data),
														RSAEncer.AL_PKCSPADDING,
															RSAUtils.buildRSAPrivateKey(module, priexp));
	}

	/**
	 * 加密明文数据.
	 * @param base64data
	 * @return
	 * @throws Exception
	 */
	public String encryptData( String plainText ) throws Exception
	{
		byte[] encData = RSAEncer.encryptData(	plainText.getBytes("UTF-8"),
													RSAEncer.AL_PKCSPADDING,
														RSAEncer.BLOCKSIZE_PKCSPADDING,
															RSAUtils.buildRSAPublicKey(module, pubexp));
		return BaseCoder.toBase64String(encData);
	}
	
	private void initSec()
	{
		//加载加密配置文件.
		String encFile = PropertiesReader.getString(RSAConfigPath);
		if ( StringUtil.isEmpty(encFile) )
		{
			log(KBankApp.INIT_MARK+"no value for "+RSAConfigPath+" configured,so RSA encrypt is not supported.");
			return;
		}
		Properties encPro = new Properties();
		FileInputStream inStream;
		try {
			inStream = new FileInputStream(encFile);
			encPro.load( inStream );
			inStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//从JSK文件加载密钥信息.
		String loadType = encPro.getProperty("loadType");
		if ( "FILE".equals(loadType) )	//从文件中加载.
		{
			String jksFile = encPro.getProperty("jksFile").trim();
			String storePass = encPro.getProperty("storePassword").trim();
			String keyAlias = encPro.getProperty("keyAlias").trim();
			String keyPass = encPro.getProperty("keyPassword").trim();
			try
			{
				RSAPrivateKey priKey = RSAUtils.getPriKey( jksFile, storePass, keyAlias, keyPass );
				RSAPublicKey pubKey = RSAUtils.getPubkey(jksFile, storePass, keyAlias);
				
				log( KBankApp.INIT_MARK+"load JSK file from "+jksFile);

				module = priKey.getModulus();
				priexp = priKey.getPrivateExponent();
				pubexp = pubKey.getPublicExponent();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		support = true;
	}

	public static void main(String args[]) throws Exception
	{
		String pt = "12345678901234567890";
		String endStr = RSATools.getInstance().encryptData(pt);
		String plainText = new String( RSATools.getInstance().dencryptData(endStr), "utf-8");
		log(plainText);
	}

	public static void log( String lgmsg )
	{
		System.out.println(lgmsg);
	}
}
