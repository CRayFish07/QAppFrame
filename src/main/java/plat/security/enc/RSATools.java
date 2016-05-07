package plat.security.enc;

import plat.tools.BaseCoder;
import plat.tools.PropertiesReader;

public class RSATools {
	
	/*************** 证书私钥 bankofchangsha *******************/
	private String storepass = "haikou123";
	private String keypass = "haikou123"; // 私钥密码
	private String keyalias = "haikou"; // 私钥别名
	
	private String jksfileName = "keystoreFilePath";
	private String certfileName = "publickeyFilePath";
	private String encFileName = "encryptFileName";//密钥配置文件.
	
	/************ 填充方法 ************************/
	private static String AL_NOPADDING = "RSA/ECB/NoPadding";
	private static String AL_PKCSPADDING = "RSA/ECB/PKCS1Padding";
	
	//密码模数（即大数的乘积)
	private String module = "";
	
	//公钥指数
	private String pubexp = "";
	
	//私钥指数.
	private String priexp = "";
	
	private RSATools()
	{
	}
	
	private static final class SingleHolder
	{
		private static RSATools rsatool = new RSATools();
	}
	
	public static RSATools getInstance()
	{
		return SingleHolder.rsatool;
	}
	
	public byte[] decryptDataNoPadding( String base64data ) throws Exception
	{
//		String jksfile = ConfProperties.getString(jksfileName);
		String jksfile = PropertiesReader.getString(jksfileName);

		byte[] data = BaseCoder.fromBase64String(base64data);
		return RSASecurity.dencryptDataNoPadding(data, jksfile, storepass, keyalias, keypass );
	}
	
	public byte[] decryptDataNoPadding( byte[] encdata ) throws Exception
	{
//		String jksfile = ConfProperties.getString(jksfileName);
		String jksfile = PropertiesReader.getString(jksfileName);
		return RSASecurity.dencryptDataNoPadding(encdata, jksfile, storepass, keyalias, keypass );
	}
	
	public String dencryptDataPKCS1Padding( String base64data ) throws Exception
	{
//		String jksfile = ConfProperties.getString(jksfileName);
		String jksfile = PropertiesReader.getString(jksfileName);
		
		byte[] data = BaseCoder.fromBase64String(base64data);
		
		byte[] decdata = RSASecurity.dencryptDataPKCS1Padding( data, jksfile, storepass, keyalias, keypass );
		return decdata==null?null:new String(decdata,"UTF-8");
	}
	
	public byte[] dencryptDataPKCS1Padding( byte[] encdata ) throws Exception
	{
//		String jksfile = ConfProperties.getString(jksfileName);
		String jksfile = PropertiesReader.getString(jksfileName);
		return RSASecurity.dencryptDataPKCS1Padding(encdata, jksfile, storepass, keyalias, keypass );
	}
	
	public byte[] ecryptDataNoPadding( byte[] encdata ) throws Exception
	{
//		String cerfile = ConfProperties.getString(certfileName);
		String cerfile = PropertiesReader.getString(certfileName);
		return RSASecurity.encryDataNoPadding( encdata, cerfile );
	}
	
	public byte[] encryptDataPKCS1Padding( byte[] encdata ) throws Exception
	{
//		String cerfile = ConfProperties.getString(certfileName);
		String cerfile = PropertiesReader.getString(certfileName);
		return RSASecurity.encryDataPKCS1Padding( encdata, cerfile );
	}

	public static void main(String args[]) throws Exception
	{
/*		//HK=====================>>>
		String hk_module = "8B739B9B94A99AAD82DCCE4F086D5A2D59C91A15E5E4E16E751366090BB6A02834D023AE58243FCB67EB6F92406EA4D553C0BD11720EB1AA7471711A6720DF3CBD7D11557DD2A227E2941555EF267B0E3BFE7B33FE95E423D108AC4DE0BA6FB711C063B4524583D243FBB5441D0805F5373430899F89EBDF71D3FFC6E63E3DE7";

		//私钥指数
		String hk_pri_hex_exp = "DBF76FECE982DEC9F331F8FC117EEC4FBCE0CA94E01D38BAD351D6114EB8B3C8B805D942992614BAF5211ACCC775BFEC004075FB9EBE2EE180EBB04CC57FC49FDD040B3A0743E8D54FAC11DE8EA72502A29D7343DF00F441109CC7F92AF209305F0B0E1289CE7A723C2A0A4B4C749355751D3C0AD53B254F5E91BB7AFFD2031";

		//Uni ==============================>>>
		//模数
		String uni_module_test ="E047C599D037343D63B6F7D4F98D57D99BA97C788B3BB1DA7500C9AB490F34DE5ECF3C63B453685196445ED9455197C62F45BC4858DB22A9BFDF7C88CD4B20BE82595B3F817ADFB012471C9CCD8215690D2FA14208A762B970CC1374936D93E937C8F18EF70454411F8AB5507DF6A9E9CC5FB3715251C6FEF2224BFE05F4F2BD";
		String uni_module_pro = "9FE5E89BC588B612D505CC4E50EE9F5266F7F207F16BDD6D81F80BCC5B18D85AA96E75BA1F11945557CB7E14CC22F9AA669AEE926AAF32891675AA711C4F23C580D0EEF9A0CB98F4F3438FFC0176C9BFA5831CE14FEE7B24C28CE25220296C6A2AC4225B6C72108CDE4D872AD86B72F14F8450B41AA1F7E8D35E55D9314B01DB";

		//公钥指数
		String pub_exp = "10001";

		//私钥指数
		String uni_pri_exp = "0002027075A4E52F929700D3B45A896646A9DC7CFED82D35A37F2CDDC00E37BE933055D5C00E37BE933055D5468591735CF10F18CB0238835AA27308F8E06DD8B17077F9C2DC3DD8AE96C43D92519300B8E00FA9494F8495DDA620A2863DA645D7CF0AB9CE47B202CAB7A9468855C72B8C4B9F428C7FD7E6C8C03A5D69E1015D4DBE2C78B3A149719EC3FE2FBBF438A7939C732D46B3E7034B6D5360AF8E679692047D1AF6CA23A6254C69E8DAD751B9CFC61030E7502D993F2E88D8C029C8A35674F9544D21C6EF077DB3D3CEDC4C1904043BD60711F09C4DDA0152AC9C9594957844822DB1815558228F14A7958F8D8827A3DAC72760506CA6C920D858E57D83767816A979FE14C8584850EFFEBA02DD33ABC8E42633FEB8D285BDE2A6D7DB60EDF5EA78BEDCC259F4449BD704DBCB6AF5D5AB9A878038B441E2302707A6D7725211E10E448310F91EEFF2B062AED28891C62E1671CCBED1E2D824C9C7FA5FFE3FB8C5832556D635AE190BA4DCFE48E99F41A6DC8B0C4F497E7F2B4258175D7B3C3FDE821CF42274DF93BE0C88FC81CD99071239890F9F814803878B248AAF16AF740BB79DEF3967EBB51E1FD8B96CE988733EC0C972E20274806B8B8C119EC6F6C86D361A7B668D602B6B5FA14BF9615CAD38891E8D4C17CA3C3E7826264E5518CE6632B5FDD4B68447698C4824C21B29D2E10B5E8EF5BD1AD4B3B277C617E76D720D8AEB1F8D3F6CC4EB3ADCC70F01A375B69D15A6A28EB0D50D9AC6E6417C72072EA28395C15DFFB6095AC5C70B88C63F80FE01ED861028C822B924E4E2BE79235B989D50A3A5B915F759B8C374C585133B3669DF37EFA8A59FAAD6AF74540C40013CDCC67E5A7B3DE4AF0BF803411C5D50";

		//Uni===============================<<<
		String jksfile = ConfProperties.getString("CSkeystoreFilePath");
		String cerfile = ConfProperties.getString("CSpublickeyFilePath");
		
		String msg = "I9tZlCq4SgVnnQis2KM0b7nqzBarCfRuwUpZRBZEReX/8Cmbx8a9u1/ee/p9V9uLaVpV+pAXN6XQt6IEzhePmrUd2U2FjtRM5Zo+bBjffvHNAX+Yb/kPlfpKiN5xpx0rxuAL1r0NqHswdtNGhzjGda1J+pJe+IEMz6UqtzlDVXU=";
		*/
		
		String plain = "hello rsa";
		RSATools rt = RSATools.getInstance();
		
		//加密
		byte[] data = rt.encryptDataPKCS1Padding(plain.getBytes("utf-8"));
		if ( data == null )
		{
			log("ERROR:rsa enc failed.");
			return;
		}
		
		String ss = BaseCoder.toBase64String(data);
		log("__ENC_BYTES["+ss.substring(0, 60)+"\n\n"+ss.substring(60)+"]"+"size="+data.length);
		
		//解密
		byte[] decplain = rt.dencryptDataPKCS1Padding(data);
		
		log("____PLAIN[" +new String( decplain, "utf-8") +"]");

		return;
	}

	public static void log( String lgmsg )
	{
		System.out.println(lgmsg);
	}
}
