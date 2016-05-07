package plat.frame.api.controller;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plat.frame.api.APIEntity;
import plat.frame.api.APIReleaseInfo;
import plat.frame.api.annonation.APIDefiner;
import plat.frame.api.annonation.MethodDefiner;
import plat.frame.app.impl.BeanParser;
import plat.frame.app.impl.TargetSearcher;
import plat.frame.app.impl.UrlParseBean;
import plat.tools.BaseCoder;
import plat.tools.JsonCoder;
import plat.tools.PermKeeper;
import plat.tools.PropertiesReader;
import plat.tools.StringUtil;

@Controller
public class APIEntry extends BeanParser
{
	private static final String hostUrlDef = "http://localhost:8080/directBank";
	private static final String releasePkg = "com.csbank.api.release.";		//接口发布包.
	private static final String exampleMethod = "showExample";				//实例方法名字.
	
	private static final String kHOSTURL = "HOSTURL";
	
	@RequestMapping( value="/ns0/test/test.api" )
	@ResponseBody
	public String testapi( HttpServletRequest request, @RequestParam String module )
	{
		return "SUCCESS";
	}

	/**
	 * 按照功能查询所有发布接口.
	 * @param request
	 * @param module
	 * @return
	 */
	@RequestMapping( value="/ns0/api/list.do" )
	public String queryAllApisByTransModel( HttpServletRequest request, @RequestParam String module )
	{
/*		//不对生产开放.
		if ( !PermKeeper.isTest() )
		{
			return "error0";
		}*/
		
		if ( module == null || module.trim().length() == 0 )
		{
			request.setAttribute("errMsg", "module为空.");
			return "error0";
		}
		
		String hostUrl = PropertiesReader.getString(kHOSTURL);
		if ( StringUtil.isEmpty(hostUrl))
		{
			logger.info("INFO:无法获取服务器地址,采用默认地址.");
			hostUrl = hostUrlDef;
		}
		
		String fullName = releasePkg+module;
		try
		{
			Class<?> apiClass = Class.forName(fullName);
			Method mtd = apiClass.getMethod("queryAPIs", null);
			Object obj = apiClass.newInstance();
			List<APIReleaseInfo> urlList = (List<APIReleaseInfo>) mtd.invoke(obj, null);
			request.setAttribute("apiList", urlList);
			request.setAttribute("hostUrl", hostUrl);
			return "/page/allAPIs";
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "fail";
	}
	
	/**
	 * tr0 - 有会话检查
	 * 根据模块/类/方法名字进行调用.
	 * 模块只能写在包basepkg中.
	 * @param request
	 * @param response
	 * @param model
	 * @param clz
	 * @param mtd
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping( value="/{transtype}/{module}/{clazz}/{method}.api" /*,method=RequestMethod.POST*/ )
//	@ResponseBody
	public String callAPIInfos( HttpServletRequest request,
				@PathVariable String transtype, @PathVariable String module,
				@PathVariable String clazz, @PathVariable String method ) throws UnsupportedEncodingException
	{
		UrlParseBean urlbean = new UrlParseBean();
		urlbean.setTransType(transtype);
		urlbean.setModuleName(module);
		urlbean.setClazzName(clazz);
		urlbean.setMethodName(method);
		
		if( !callAPIMain(request,urlbean,method) )
		{
			return "fail";
		}
		
		logger.info("__RET_SUCCESS.");
		
		return "/page/APITable";
	}
	
	/**
	 * 主方法.
	 * @param request
	 * @param module
	 * @param clazz
	 * @param method
	 * @return
	 */
	private boolean callAPIMain( HttpServletRequest request, UrlParseBean urlbean, String method )
	{
		//不对生产开放.
		if ( !PermKeeper.isTest() )
		{
			return false;
		}
		
		APIEntity apiEntity = null;
		try
		{
			apiEntity = convertApiEntity( urlbean );
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		logger.info("__RET_MSG"+JsonCoder.toJsonString(apiEntity));
		
		//返回实体给JSP页面.
		request.setAttribute("apiEntity", apiEntity);
		
		String hostUrl = PropertiesReader.getString(kHOSTURL);
		if ( StringUtil.isEmpty(hostUrl))
		{
			logger.info("INFO:无法获取服务器地址,采用默认地址.");
			hostUrl = hostUrlDef;
		}
		
		//返回主机地址
		request.setAttribute("hostURL", hostUrl );
		
		return true;
	}
	
	/**
	 * 从注解获取参数信息.
	 * @param targetClass
	 * @param methodName
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws ClassNotFoundException 
	 */
	private APIEntity convertApiEntity( UrlParseBean urlbean ) throws NoSuchMethodException, ClassNotFoundException
	{
		TargetSearcher tsearcher = TargetSearcher.getInstance();
		APIEntity entity = new APIEntity();
		
		//获取方法描述.
		Method targetMethod = tsearcher.findTargetMethod(urlbean);
		MethodDefiner mtdef = targetMethod.getAnnotation(MethodDefiner.class);
		if ( mtdef != null )
		{
//			entity.setName(mtdef.name());
//			entity.setDesc(StringUtil.concat(mtdef.desc(), "\n"));
		}

		//获取版本以及变更描述.
		APIDefiner apidef = targetMethod.getAnnotation(APIDefiner.class);
		if ( apidef != null )
		{
			entity.setName(apidef.name());
			entity.setDesc(StringUtil.concat(apidef.desc(), "\n"));
			entity.setApiVersion(apidef.version());
			entity.setApiUpdates(apidef.updates());
		}
		
		Class<?>[] apiClazz = tsearcher.parseTargetParas(urlbean.getTransType(), targetMethod);
		for ( int i = 0; i < apiClazz.length; ++i )
		{
			Class<?> tmpClz = apiClazz[i];
			
			logger.info("__CLASS PARSE:"+tmpClz.getSimpleName());
			
			//存放用于生成下载文件内容.
			entity.addBean(tmpClz.getSimpleName(), tmpClz.getName());
			
			//解析请求报文结构.
			if( tmpClz.getSimpleName().startsWith("Req") )
			{
				entity.setReqBeanInfo(doParseBeanInfo(tmpClz,entity));
//				entity.setReqExample(ETools.toJsonString(generateExampleObj(tmpClz)));
			}
			else
			//解析响应报文.
//			else if ( tmpClz.getSimpleName().startsWith("Rsp") )
			{
				entity.setRspBeanInfo(doParseBeanInfo(tmpClz,entity));
//				entity.setRspExample(ETools.toJsonString(generateExampleObj(tmpClz)));
			}
		}
		
		return entity;
	}
	
	private Object generateExampleObj( Class<?> clazz )
	{
		Method mtd;
		try {
			mtd = clazz.getMethod(exampleMethod, null );
			if ( mtd != null )
			{
				return mtd.invoke(clazz.newInstance(), null);
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}