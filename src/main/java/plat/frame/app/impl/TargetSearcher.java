package plat.frame.app.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import plat.frame.api.annonation.MethodDefiner;
/**
 * 此类用于根据不同请求类型来搜索到目标类的包
 * 然后拼接类名称，搜索到类方法.
 * 以及用于API搜寻策略的返回.
 * @author zhangcq
 *
 */
public class TargetSearcher
{
	private Logger logger = Logger.getLogger(TargetSearcher.class);

	private static Map<String,String> pathMap;

	private String kmerchantNS = "mns0";
	private String kmerchantSS = "mtr0";

	private String kcommonNS = "ns0";
	private String kcommonSS = "tr0";
	
	private String kPublicNS = "pns0";
	private String kPublicSS = "ptr0";

	private TargetSearcher()
	{
		pathMap = new HashMap<String,String>();

		//E钱庄商户版
		String merchantPath = "com.emerchant.trans";
		pathMap.put(kmerchantNS, merchantPath);		//会话检查.
		pathMap.put(kmerchantSS, merchantPath);		//无会话检查.

		//普通版本.
		String commPath = "com.yitong.mbank.controller";
		pathMap.put(kcommonNS, commPath);		//会话检查.
		pathMap.put(kcommonSS, commPath);		//无会话检查.
		
		//通用模块路径
		String pubPath = "com.csbank.publics.trans";
		pathMap.put(kPublicNS, pubPath );		//会话检查.
		pathMap.put(kPublicSS, pubPath );		//无会话检查.
	}

	/**
	 * 将URL映射为全路径.
	 * @param module
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public Class<?> findTargetClass( URLMapper mapper ) throws ClassNotFoundException
	{
		String className =  mapper.getModuleName()+mapper.getClazzName()+"Trans";
		return Class.forName(className);
	}

	public Class<?>[] parseTargetParas( Method method )
	{
		//商户版本的在出参和入参中.
		Class<?>[] allParas = method.getParameterTypes();
		if ( allParas.length >= 2 )
		{
			Class<?>[] retParas = new Class<?>[allParas.length-1];
			System.arraycopy(allParas, 1, retParas, 0, allParas.length-1);
			return retParas;
		}

		/*		MethodDefiner mtdef = method.getAnnotation(MethodDefiner.class);
		if ( mtdef != null )
		{
			if ( mtdef.api().length == 0 )
			{
				return null;
			}

			return mtdef.api();
		}*/

		//无参数.
		return null;
	}

	/**
	 * 按照方法名寻找方法对象.
	 * @param methods
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 */
	public Method findTargetMethod( URLMapper mapper ) throws NoSuchMethodException, ClassNotFoundException
	{
		Class<?> targetClz = findTargetClass(mapper);
		Method[] methods = targetClz.getMethods();
		
		String methodName = mapper.getMethodName();
		for ( Method method : methods )
		{
			if ( methodName.equals(method.getName()))
			{
				return method;
			}
		}
		
		throw new NoSuchMethodException("can not find method named "+methodName+" in class "+targetClz.getName());
	}
	
	/**
	 * 按照方法名寻找方法对象.
	 * @param methods
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 */
	public Method findTargetMethod( Class<?> clazz, String methodName ) throws NoSuchMethodException, ClassNotFoundException
	{
		Method[] methods = clazz.getMethods();
		for ( Method method : methods )
		{
			if ( methodName.equals(method.getName()))
			{
				return method;
			}
		}
		
		throw new NoSuchMethodException("can not find method named "+methodName+" in class "+clazz.getName());
	}

	private static final class TargetSearcherHolder
	{
		public static final TargetSearcher instance = new TargetSearcher();
	}

	public static TargetSearcher getInstance()
	{
		return TargetSearcherHolder.instance;
	}
}
