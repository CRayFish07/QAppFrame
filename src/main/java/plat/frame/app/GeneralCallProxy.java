package plat.frame.app;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import plat.constant.KResponse;
import plat.frame.api.annonation.DEFEND_TYPE;
import plat.frame.api.annonation.ENC_TYPE;
import plat.frame.api.annonation.FieldDefiner;
import plat.frame.api.annonation.SESS_TYPE;
import plat.frame.api.annonation.TransConfig;
import plat.frame.app.define.ITransContext;
import plat.frame.app.define.MessageType;
import plat.frame.app.impl.TargetSearcher;
import plat.frame.app.impl.TransContext;
import plat.frame.app.impl.UrlParseBean;
import plat.frame.app.msg.ReqMessageHead;
import plat.frame.app.msg.RspMessageHead;
import plat.security.enc.AESTools;
import plat.security.enc.MD5Encrypt;
import plat.security.enc.RSATools;
import plat.tools.JsonCoder;
import plat.tools.PermKeeper;
import plat.tools.PropertiesReader;
import plat.tools.RandomUtil;
import plat.tools.StringUtil;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GeneralCallProxy extends CallProxy
{
	private Logger logger = Logger.getLogger(GeneralCallProxy.class);

	private Map catcheMap = new ConcurrentHashMap<String, Object>();	//换成class和method.

	private ApplicationContext springAppContext;

	public GeneralCallProxy()
	{
		init();
	}

	private void init()
	{
		logger.info("__GeneralCallProxy init succeed.");
	}

	protected Object callTargetMethod( HttpServletRequest request, UrlParseBean urlbean )
	{
		//TIME0
		long time0 = new Date().getTime();

		TargetSearcher tsearcher = TargetSearcher.getInstance();
		try
		{
			//查找类名.
			Class<?> targetClz = tsearcher.findTargetClass(urlbean);

			//查找URL对应的方法,查找方法,采用同名策略.
			Method targetMethod = tsearcher.findTargetMethod(targetClz,urlbean.getMethodName());

			logger.info("__springAppContext"+AppContextHolder.getContext());

			//获得服务接收的报文类信息.
			MessageType messageType = initMessageType( targetClz, targetMethod );

			long time1 = new Date().getTime();
			logger.info("__TIME_COUNT:METHOD_SEARCH:"+(time1 - time0));

			//获得请求报文.
			String indata = getInputString( request, "utf-8" );
			if ( StringUtil.isEmpty(indata) )
			{
				logger.error("ERROR:请求报文为空.");
				throw new AppException(KResponse.INPUT_ERROR, "请求报文为空." );
			}

			long time2 = new Date().getTime();
			logger.info("__TIME_COUNT:INTPUT_READ:"+(time2 - time1));

			//创建应用上下文.
			ITransContext context = new TransContext();

			//按照消息类型信息解析请求报文并放到上线文中.
			parseMessage( context, messageType, indata );

			checkInputValue( context.getReqBody(), messageType.getReqBodyClz() );

			long time3 = new Date().getTime();
			logger.info("__TIME_COUNT:PARSER_REQ_MSG:"+(time3 - time2));

			//拼装请求参数.
			List<Object> list = new ArrayList<Object>();
			list.add(context);

			if ( messageType.getReqBodyClz() != null )
			{
				list.add(context.getReqBody());
			}

			if ( messageType.getRspBodyClz() != null )
			{
				list.add(context.getRspBody());
			}

			Object obj = AppContextHolder.getContext().getBean(targetClz);
			if ( obj == null )
			{
				new AppException(KResponse.FAIL, "目标对象为空."+targetClz.toString());
			}
			long time4 = new Date().getTime();
			logger.info("__TIME_COUNT:GET_BEAN:"+(time4 - time3));

			Object[] paras = list.toArray();
			for (  int i = 0; i < paras.length; ++i )
			{
				logger.info(String.format( "__PARA%d=%s",i,paras.getClass().getName()));
			}

			logger.info("__CALL_APP:"+targetClz.getSimpleName()+":"+targetMethod.getName()+":"+getTraceInfo(request));
			targetMethod.invoke( obj, list.toArray() );

			long time5 = new Date().getTime();
			logger.info("__TIME_COUNT:INVOKE_TRANS:"+(time5 - time4));

			//进行数据处理.
			String retStr = packResponse(context,messageType);

			long time6 = new Date().getTime();
			logger.info("__TIME_COUNT:PACK_RESPONSE_MSG:"+(time6 - time5));

			return retStr;
		}
		catch ( AppException exception )
		{
			exception.printStackTrace();
			return packErrorResponse(exception);
		}
		catch ( Exception exception )
		{
			exception.printStackTrace();
			return packErrorResponse( KResponse.FAIL, "不好意思,系统开了个小差." );
		}

		//		return "fail";
	}
	
/*	private String getTraceInfo( HttpServletRequest request )
	{
		HttpSession session = request.getSession(false);
		
		String custNo = (String)session.getAttribute(SessConsts.CUSTNO);
		String mobile = (String)session.getAttribute(SessConsts.MOBILENO);
		
		custNo = custNo == null ? "":custNo;
		mobile = mobile == null ? "":mobile;
		return custNo+"#"+mobile;
	}*/

	/**
	 * 组装错误报文.
	 * @param appEx
	 * @return
	 */
	private String packErrorResponse( AppException appEx )
	{
		return packErrorResponse(appEx.getErrCode(),appEx.getErrInfo());
	}

	private String packErrorResponse( String errCode, String errMsg )
	{
		String mark = "";
		if ( PermKeeper.isTest() )
		{
			mark = RandomUtil.randomInt(7)+"_";
			logger.info("__ERRMARK:"+mark+errMsg);
		}

		RspMessageHead errHead = new RspMessageHead();
		errHead.setRetCode(errCode);
		errHead.setRetMsg(mark+errMsg);
		
		Map rspMap = new HashMap<String,Object>();
		rspMap.put("head", errHead );
		rspMap.put("body", new HashMap() );

		return "FFFF"+JsonCoder.toJsonString(rspMap);
	}

	/**
	 * 正常组包
	 * @param context
	 * @param msgType
	 * @return
	 */
	private String packResponse( IBusinessContext context, MessageType msgType )
	{
		Map rspMap = new HashMap<String,Object>();
		rspMap.put("head", context.getRspHead());
		rspMap.put("body", context.getRspBody()==null?new HashMap():context.getRspBody());

		String retMessage = msgType.getMsgFmt()+encryptMessage( context, msgType, new Gson().toJson(rspMap) );

		return retMessage;
	}

	/**
	 * 分析请求报文
	 * @param context
	 * @param msgType
	 * @param indata
	 * @return
	 * @throws AppException 
	 */
	private void parseMessage( ITransContext context, MessageType msgType, String indata ) throws AppException
	{
		//查看是否强制明文,该功能用于测试.
		msgType.forcePlain = PropertiesReader.getBoolean("FORCEPLAIN");

		//根据定义的消息类型,解析请求报文.
		//并填写消息格式信息.
		//根据配置文件判断是否加密.
		String msgData = decryptMessage( context, msgType, indata );

		//反序列化对象.
		JsonObject jo = new JsonParser().parse(msgData).getAsJsonObject();
		JsonElement header = jo.get("head");
		JsonElement body = jo.get("body");
		if ( header == null )
		{
			throw new AppException(KResponse.INPUT_ERROR, "NH,"+KResponse.MSG_SYS_ERROR );
		}

		Gson gson = new Gson();
		ReqMessageHead reqHead = gson.fromJson(header,ReqMessageHead.class);
		context.setReqHead(reqHead);

		if ( msgType.getReqBodyClz() != null )
		{
			if ( body == null )
			{
				throw new AppException(KResponse.INPUT_ERROR, "NC,"+KResponse.NET_BUSY  );
			}

			Object reqBody = gson.fromJson(body, msgType.getReqBodyClz());
			context.setReqBody(reqBody);
		}

		if ( msgType.getRspBodyClz() != null )
		{
			try
			{
				context.setRspBody(msgType.getRspBodyClz().newInstance());
			}
			catch ( Exception e)
			{
				throw new AppException(KResponse.FAIL, KResponse.MSG_SYS_ERROR);
			}
		}
	}

	private boolean checkInputValue( Object inObj, Class<?> clazz ) throws AppException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		//只在开发联调阶段启动.
		if ( !PermKeeper.isTest() )
		{
			return true;
		}

		//没有输入参数.
		if ( clazz == null )
		{
			return true;
		}
		
		Field[] fds = clazz.getDeclaredFields();
		for ( Field fd: fds )
		{
			//无注解.
			FieldDefiner fdef = fd.getAnnotation(FieldDefiner.class);
			if ( fdef == null )
			{
				continue;
			}

			//没有参数.
			if ( fdef.required() == false )
			{
				continue;
			}

			Method getter;
			getter = clazz.getMethod("get"+StringUtil.bigFirstCase(fd.getName()));
			Object value = getter.invoke(inObj);
			if ( value == null || value.toString().trim().equals(""))
			{
				throw new AppException( KResponse.INPUT_ERROR, fd.getName()+"取值不能为空" );
			}
		}

		return true;
	}

	/**
	 * 用于分析请求报文,序列化成bean.
	 * @param busiCtx
	 * @param messageType
	 * @param data
	 * @return
	 * @throws AppException 
	 */
	private String decryptMessage( ITransContext context, MessageType messageType, String inData ) throws AppException
	{
		//TODEL
		logger.info(String.format("REQ_MSG_ENC:LEN[%d][%s]", inData.length(), inData ));

		if ( StringUtil.isEmpty(inData) || inData.length() < 4 )
		{
			throw new AppException(KResponse.INPUT_ERROR, "报文为空或者格式不符合要求" );
		}

		//报文版本类型.
		String msgVer = inData.substring(0, 4);
		messageType.setMsgFmt(msgVer);

		//报文实体.
		String retdata = inData.substring(4);

		//用于测试使用.
		if ( messageType.forcePlain )
		{
			return retdata;
		}

		switch( messageType.encType )
		{
		case AES_SS:
			retdata = decryptDataAES(context, retdata);
			break;
		case RSA_NS:
			retdata = decryptDataRSA( context, retdata );
			break;
		case PLAIN:
		case DEFAULT:
			break;
		default:
		}

		//由于报文有头 所以肯定不为空.
		if ( StringUtil.isEmpty(retdata) )
		{
			throw new AppException(KResponse.AES_DECODE_FAILED, "很抱歉,系统错误 ,请重试.");
		}

		return retdata;
	}

	/**
	 * 非登录状态下RSA和AES组合密钥解密.
	 * @param context
	 * @param encData
	 * @return
	 * @throws AppException 
	 */
	private String decryptDataRSA( ITransContext context, String encData ) throws AppException
	{
		//解密
		String datas[] = encData.split("#");
		if ( datas.length != 3 )
		{
			logger.error(String.format("ERRCODE[%s][输入数据拼接格式不对]data=%s",KResponse.INPUT_ERROR, encData));
			throw new AppException( KResponse.INPUT_ERROR, "输入数据拼接格式不对" );
		}

		String sign0 = datas[0];
		String encAESKey = datas[1];
		String encJson = datas[2];

		//验证签名
		try {
			String mysign = MD5Encrypt.MD5(encAESKey+"#"+encJson+"#ebank123" );
			if ( !sign0.equalsIgnoreCase(mysign) )
			{
				logger.error(String.format("ERRCODE[%s][数据签名错误,mysign=%s]",KResponse.TRANS_ILLEGAL,mysign));
				throw new AppException( KResponse.TRANS_ILLEGAL, "签名出错.", false );
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		String AESKey = null;
		try
		{
			AESKey = RSATools.getInstance().dencryptDataPKCS1Padding(encAESKey);
			if ( AESKey == null )
			{
				logger.error("无法解析出AES密钥,encAESKey="+encAESKey);
				return null;
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String json = AESTools.AESDecrypt(encJson, AESKey );
		if ( json == null )
		{
			logger.error("无法解析出请求数据,encJson="+encJson);
			return null;
		}

		logger.info("__FROM_APP_RSA_DEC:"+json);

		context.saveSecKey( ENC_TYPE.RSA_NS, AESKey );

		logger.info("AESKey="+AESKey);

		return json;
	}

	/**
	 * 通过协商的AES密钥解密.
	 * @param context
	 * @param inData
	 * @return
	 */
	private String decryptDataAES( ITransContext context, String inData )
	{
		//解密
		String dr[] = inData.split("#");
		if ( dr.length != 2 )
		{
			logger.error(String.format("ERRCODE[%s][输入数据拼接格式不对]data=%s",
					KResponse.DATA_ILLEGAL, inData));
			return null;
		}

		String sign0 = dr[0];
		String encJson = dr[1];

		//验证签名.
		try {
			String mysign = MD5Encrypt.MD5(encJson+"#"+"ebank123" );
			if ( !sign0.equalsIgnoreCase(mysign))
			{
				logger.error(String.format("ERRCODE[%s][数据签名验证不通过.][mysign=%s]",	KResponse.TRANS_ILLEGAL, mysign ));
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		String json = AESTools.AESDecrypt( encJson, context.getSecKey( ENC_TYPE.AES_SS ) );
		if ( json == null )
		{
			logger.error("无法解析出请求数据,encJson="+encJson);
			return null;
		}

		logger.info("__FROM_APP_AES_DEC="+json);

		return json;
	}

	/**
	 * 加密请求报文.
	 * @param messageType
	 * @param data
	 * @return
	 */
	private String encryptMessage( ITransContext context, MessageType messageType, String inData )
	{
		String retdata = inData;

		//TODEL
		logger.info(String.format("RSP_MSG_PLAIN:LEN[%d][%s]", inData.length(), inData ));

		//用于测试使用.
		if ( messageType.forcePlain )
		{
			return retdata;
		}

		switch( messageType.encType )
		{
		case AES_SS:
			retdata = AESTools.AESEncrypt(inData, context.getSecKey(ENC_TYPE.AES_SS) );
			break;
		case RSA_NS:
			retdata = AESTools.AESEncrypt(inData, context.getSecKey(ENC_TYPE.RSA_NS) );
			break;
		case PLAIN:
		case DEFAULT:
			break;
		default:
		}

		logger.info(String.format("RSP_MSG_PLAIN:LEN[%d][%s]", retdata.length(), retdata ));

		return retdata;
	}

	/**
	 * 通过注解获取加密方式和会话类型.
	 * @param clazz
	 * @param method
	 * @return
	 * @throws AppException 
	 */
	private MessageType initMessageType( Class<?> clazz, Method targetMethod ) throws AppException
	{
		//api use in login time in most time.
		//https session, aes encryption by default.
		MessageType msgType = new MessageType( ENC_TYPE.AES_SS,
				SESS_TYPE.HTTP_SESS, DEFEND_TYPE.DEFAULT );

		//get class level enctype and sesstype.
		/*		ClazzDefiner clzAnnot = clazz.getAnnotation(ClazzDefiner.class);
		if ( clzAnnot != null )
		{
//			result.encType = clzAnnot.encrypt()==ENC_TYPE.DEFAULT?result.encType:clzAnnot.encrypt();
//			result.sessType = clzAnnot.session()==SESS_TYPE.DEFAULT?result.sessType:clzAnnot.session();
		}*/

		//but method's is more effective than class level.

		/*		MethodDefiner mtdAnnot = method.getAnnotation(MethodDefiner.class);
		if ( mtdAnnot != null )
		{
//			result.encType = mtdAnnot.encrypt()==ENC_TYPE.DEFAULT?result.encType:mtdAnnot.encrypt();;
//			result.sessType = mtdAnnot.session()==SESS_TYPE.DEFAULT?result.sessType:mtdAnnot.session();
		}*/

		TransConfig cfgAnnot = targetMethod.getAnnotation(TransConfig.class);
		if ( cfgAnnot != null )
		{
			msgType.encType = cfgAnnot.encrypt()==ENC_TYPE.DEFAULT?msgType.encType:cfgAnnot.encrypt();;
			msgType.sessType = cfgAnnot.session()==SESS_TYPE.DEFAULT?msgType.sessType:cfgAnnot.session();
			msgType.defendType = cfgAnnot.defend()==DEFEND_TYPE.DEFAULT?msgType.defendType:cfgAnnot.defend();
		}

		//进行方法合法性校验,并获取返回参数.
		parseParas( targetMethod, msgType );

		logger.info("__MESSAGE_TYPE["+msgType.toString()+"]");

		return msgType;
	}

	/**
	 * 进行参数检查和方法分析.
	 * 这里需要考虑到:有(无)请求报文体,有(无)响应报文体共四种情况.
	 * 所以采用参数形式.doTrans( context, reqBody, rspBody )的形式,简单明了.
	 * @param method
	 * @param msgType
	 * @throws AppException 
	 */
	private void parseParas( Method method, MessageType msgType ) throws AppException
	{
		Class<?> paras[] = method.getParameterTypes();
		if ( paras == null || paras.length == 0 || paras.length > 3 )	//1,2,3:doTrans( context ...);
		{
			throw new AppException( KResponse.INPUT_ERROR, String.format("接口参数非法[%s][paras:%s]",method.getName(),paras.length), false);
		}

		//doTrans( context, reqBody or rspBody ) 
		if ( paras.length == 2 )
		{
			if ( paras[1].getSimpleName().startsWith("Req") )
			{
				msgType.setReqBodyClz(paras[1]);
			}
			else
			{
				msgType.setRspBodyClz(paras[1]);
			}
		}

		//doTrans( context, reqBody, rspBody )
		if ( paras.length == 3 )
		{
			msgType.setReqBodyClz(paras[1]);
			msgType.setRspBodyClz(paras[2]);
		}
	}
}
