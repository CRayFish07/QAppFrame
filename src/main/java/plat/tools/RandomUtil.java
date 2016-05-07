package plat.tools;

import java.util.Random;

/**
 * 
 * @author iven
 * 
 */
public class RandomUtil {

	private static int[] primes = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
			41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107,
			109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179,
			181, 191, 193, 197, 199 };

	/**
	 * 获取步长
	 * 
	 * @return
	 */
	public static int getPrimes() {
		return primes[(int) (Math.random() * (primes.length))];
	}

	/**
	 * 获取基准数
	 * 
	 * @return
	 */
	public static int getBaseNum() {
		return (int) Math.round(Math.random() * 8999 + 1000);
	}
	
	/**
	 * 
	 * 随机生成length位随机数，包含数字和字母
	 */
	public static String getRandomSequence(int length) {
//		char[] charSequences = { '1', '2', '3', '4', '5', '6', '7', '8', '9','0','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char[] charSequences = "0QwErTyUiOpAsDfGhJkLzXcVbNm12345678qWeRtYuIoPaSdFgHjKlZxCvBnM9".toCharArray();
		StringBuffer randomCode = new StringBuffer();
		
		//需要增加种子的随机性
		Random random = new Random();
//		random.setSeed(System.currentTimeMillis()+12615);
		for (int i = 0; i < length; i++) {
			String strRand = String.valueOf(charSequences[random.nextInt(charSequences.length)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
	
	/**
	 * 
	 * 随机生成length位随机数，包含数字和字母
	 * 并发情况下在Linux上的时间无法接受.(暂时弃用)
	 */
	public static String getSecRandomSequence(int length)
	{
		int len0 = length/2;
		int len1 = length - len0;
		return getRandomSequence( len1 ) + getRandomSequence( len0 );
		/*
		long stime = new Date().getTime();
//		char[] charSequences = { '1', '2', '3', '4', '5', '6', '7', '8', '9','0','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char[] charSequences = "QwErTyUiOpAsDfGhJkLzXcVbNm01234567890qWeRtYuIoPaSdFgHjKlZxCvBnM-".toCharArray();
		StringBuffer randomCode = new StringBuffer();
		SecureRandom random = new SecureRandom();
		long stime1 = new Date().getTime();
		byte[] seedBts = random.generateSeed(8);
		long etime2 = new Date().getTime();
		long t1 = etime2 - stime1;
		random.setSeed(seedBts);
		for (int i = 0; i < length; i++)
		{
			String strRand = String.valueOf(charSequences[random.nextInt(charSequences.length)]);
			randomCode.append(strRand);
		}
		long etime = new Date().getTime();
		System.out.println("__SECRANDOM_TIME="+(etime-stime)+"t1="+t1+":"+randomCode.toString());
		
		return randomCode.toString();
	*/}
	/**
	 * 
	 * 随机生成length位数纯数字新密码
	 */
	public static String randomInt(int length) {
		char[] charSequences = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'0' };
		StringBuffer randomCode = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			String strRand = String.valueOf(charSequences[random.nextInt(10)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
	
	public static void main( String args[] )
	{
		int max = 500;
		if ( args.length > 1 )
		{
			max = Integer.parseInt(args[0]);
		}
		
		for ( int i = 0; i < max; ++i )
		{
			new Thread( new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
//					getSecRandomSequence(32);
				}
			}).start();
		}
		return;
	}
}
