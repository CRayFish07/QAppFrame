package plat.security.enc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.lang3.ArrayUtils;

public class RSAUtils
{

	public static int radix_16 = 16;
	public static int radix_10 = 10;
	
	//按照十六进制公钥获取
	public static RSAPublicKey getPubKey(String pubModules,String pubExp )
	{
		return buildRSAPublicKey(new BigInteger(pubModules, radix_16 ), new BigInteger(pubExp, radix_16 ));
	}
	
	//按照进制公钥获取
	public static RSAPublicKey getPubKey(String pubModules,String pubExp, int radix )
	{
		return buildRSAPublicKey(new BigInteger(pubModules, radix ), new BigInteger( pubExp, radix ));
	}
	
	//按照十进制公钥获取
	public static RSAPublicKey getPubKeyDeci(String pubModules,String pubExp )
	{
		return buildRSAPublicKey(new BigInteger(pubModules, radix_10 ), new BigInteger( pubExp, radix_10 ));
	}
	
	//按照十六进制私钥获取
	public static RSAPrivateKey getPriKey(String pubModules, String priExp ) {
		
		return buildRSAPrivateKey(new BigInteger(pubModules, radix_16 ), new BigInteger( priExp, radix_16 ));
	}
	
	//按照进制获取私钥
	public static RSAPrivateKey getPriKey(String pubModules, String priExp, int radix ) {
		
		return buildRSAPrivateKey(new BigInteger(pubModules, radix ), new BigInteger( priExp, radix ));
	}
	
	//按照十进制私钥获取
	public static RSAPrivateKey getPriKeyDeci(String pubModules, String priExp ) {
		
		return buildRSAPrivateKey(new BigInteger(pubModules, radix_10 ), new BigInteger( priExp, radix_10 ));
	}

	private static RSAPublicKey buildRSAPublicKey(BigInteger modulus, BigInteger publicExponent)
	{
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
			return (RSAPublicKey) kf.generatePublic(spec);
		}
		catch (Exception e)
		{
			throw new IllegalStateException(
					"cannot build public key by modulus and exponent", e);
		}
	}
	
	public static RSAPrivateKey buildRSAPrivateKey(BigInteger modulus, BigInteger publicExponent) {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus,
					publicExponent);
			return  (RSAPrivateKey)kf.generatePrivate(spec);
		} catch (Exception e) {
			throw new IllegalStateException(
					"cannot build private key by modulus and exponent", e);
		}
	}
	
	/***********
	 * 通过文件获得私钥
	 * @throws FileNotFoundException 
	 * @throws KeyStoreException 
	 */
	public static RSAPrivateKey getPriKey( String jksfile, String storepwd, String keyalias, String keypwd ) throws Exception
	{
		char[] storep = storepwd.toCharArray(); // 证书库密码
		char[] keyp = keypwd.toCharArray(); // 证书密码
		RSAPrivateKey pk2 = null;
		
		FileInputStream fis2 = new FileInputStream(jksfile);
		KeyStore ks = KeyStore.getInstance("JKS"); // 加载证书库
		ks.load(fis2, storep );
		pk2 = (RSAPrivateKey) ks.getKey(keyalias, keyp ); // 获取证书私钥
		fis2.close();

		return pk2;
	}
	
	public static RSAPublicKey getPubKey( String cerfile ) throws Exception
	{
		CertificateFactory cff = null;
		RSAPublicKey pk1 = null;
		cff = CertificateFactory.getInstance("X.509");
		InputStream in = new FileInputStream(cerfile);
		//			InputStream in = app.getBaseContext().getResources()
		//					.openRawResource(R.raw.zjrcbank); // 证书文件
		Certificate cf = cff.generateCertificate(in);
		pk1 = (RSAPublicKey)cf.getPublicKey(); // 得到证书文件携带的公钥

		in.close();

		return pk1;
	}

	public static byte[] _encryptRsa(byte[] data, Cipher cipher)
			throws Exception {
		int fix = 128;
		byte[] rst = null;
		for (int i = 0; i * fix < data.length; i++) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			byte[] tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix,
					Math.min(data.length, (i + 1) * fix)));
//			System.out.println("HEX=["+HexUtil.byte2HexStr(tmp)+"]");
			rst = ArrayUtils.addAll(rst, tmp);
		}
		return rst;
	}

	public static byte[] _decryptRsa(byte[] data, Cipher cipher)
			throws Exception {
		int fix = 128;
		byte[] rst = null;
		for (int i = 0; i * fix < data.length; i++) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			byte[] tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix,
					Math.min(data.length, (i + 1) * fix)));
//			System.out.println("e"HexUtil.byte2HexStr(tmp));
			rst = ArrayUtils.addAll(rst, tmp);
		}
		return rst;
	}
	
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception { 
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}
}
