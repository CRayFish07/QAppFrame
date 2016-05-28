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

import plat.constant.KBankApp;
import plat.constant.KResponse;
import plat.frame.api.annonation.DEFEND_TYPE;
import plat.frame.api.annonation.ENC_TYPE;
import plat.frame.api.annonation.FieldDefiner;
import plat.frame.api.annonation.SESS_TYPE;
import plat.frame.api.annonation.TransConfig;
import plat.frame.api.meta.QBaseBean;
import plat.frame.app.define.MessageType;
import plat.frame.app.impl.TargetSearcher;
import plat.frame.app.impl.TransContext;
import plat.frame.app.impl.URLMapper;
import plat.frame.app.msg.ReqMessageHead;
import plat.frame.app.msg.RspMessageHead;
import plat.frame.app.session.CTSessionFactory;
import plat.frame.app.session.ICTSession;
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

public class HttpInvokeProxy extends HttpServletyReader
{
	private Logger logger = Logger.getLogger(HttpInvokeProxy.class);

	private Map catcheMap = new ConcurrentHashMap<String, Object>();	//换成class和method.

	public HttpInvokeProxy()
	{
		init();
	}

	private void init()
	{
		logger.info("__GeneralCallProxy init succeed.");
	}

	protected Object callTargetMethod( HttpServletRequest request, URLMapper mapper )
	{
		//TIME0
		long time0 = new Date().getTime();

		TargetSearcher tsearcher = TargetSearcher.getInstance();
		try
		{
			//查找类名.
			Class<?> targetClz = tsearcher.findTargetClass(mapper);

			//查找URL对应的方法,查找方法,采用同名策略.
			Method targetMethod = tsearcher.findTargetMethod(targetClz,mapper.getMethodName());

			//获得服务接收和返回的报文类型信息,没有数据.
			MessageType messageType = initMessageType( targetClz, targetMethod );

			long time1 = new Date().getTime();
			logger.info("__TIME_COUNT:METHOD_SEARCH:"+(time1 - time0));

			//创建会话CTSessionFactory
			ICTSession session = new CTSessionFactory()
			.setRequset(request)
			.setSessType(SESS_TYPE.HTTP_SESS)
			.createSession();
			
			long time2 = new Date().getTime();
			logger.info("__TIME_COUNT:INTPUT_READ:"+(time2 - time1));

			//创建应用上下文.
			TransContext context = new TransContext().setSession(session);

			//按照消息类型信息解析请求报文并放到上线文中.
			parseMessage( request,context, messageType );

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
//			Object obj = targetClz.newInstance();
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
			logger.error("__AppException:"+exception.getErrMsg());
			exception.printStackTrace();
			return packErrorResponse(exception);
		}
		catch ( Exception exception )
		{
			String errMsg = exception.getMessage();
			Throwable thex = exception.getCause();
			if ( thex != null )
			{
				//需要返回给客户端.
				String tmpMsg = thex.getMessage();
				errMsg = tmpMsg.charAt(0) == '@'?tmpMsg.substring(1):errMsg;
			}
			
			logger.error("__Exception:"+errMsg);
			exception.printStackTrace();
			return packErrorResponse( KResponse.FAIL,  errMsg );
		}

		//		return "fail";
	}

	private String getTraceInfo( HttpServletRequest request )
	{
		return "";
	}

	/**
	 * 分析请求报文
	 * @param context
	 * @param msgType
	 * @param indata
	 * @return
	 * @throws Exception 
	 */
	protected void parseMessage( HttpServletRequest request, TransContext context, MessageType msgType ) throws Exception
	{
		//获得请求报文.
		String indata = getInputString( request, "utf-8" );
		if ( StringUtil.isEmpty(indata) )
		{
			throw new AppException(KResponse.INPUT_ERROR, "请求报文为空." );
		}

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
			throw new AppException(KResponse.INPUT_ERROR, KResponse.MSG_INPUT_ERROR );
		}

		//解析请求头.
		Gson gson = new Gson();
		ReqMessageHead reqHead = gson.fromJson(header,ReqMessageHead.class);
		context.setReqHead(reqHead);

		//请求报文报文体.
		if ( msgType.getReqBodyClz() != null )
		{
			if ( body == null )
			{
				throw new AppException(KResponse.INPUT_ERROR, KResponse.MSG_INPUT_ERROR  );
			}

			QBaseBean reqBody = (QBaseBean)gson.fromJson(body, msgType.getReqBodyClz());
			context.setReqBody(reqBody);
		}

		//返回报文.
		if ( msgType.getRspBodyClz() != null )
		{
			context.setRspBody((QBaseBean)msgType.getRspBodyClz().newInstance());
		}
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

		//doTrans( context, [reqBody | rspBody] ) 
		for ( int i = 1; i < paras.length; ++i )
		{
			if ( paras[i].getSimpleName().startsWith("Req") )
			{
				msgType.setReqBodyClz(paras[i]);
			}
			else
			{
				msgType.setRspBodyClz(paras[i]);
			}
		}
	}

	/**
	 * 通过注解获取加密方式和会话类型.
	 * @param clazz
	 * @param method
	 * @return
	 * @throws AppException 
	 */
	protected MessageType initMessageType( Class<?> clazz, Method targetMethod ) throws AppException
	{
		//api use in login time in most time.
		//https session, aes encryption by default.
		MessageType msgType = new MessageType( ENC_TYPE.AES_SS,
				SESS_TYPE.HTTP_SESS, DEFEND_TYPE.DEFAULT );

		TransConfig cfgAnnot = targetMethod.getAnnotation(TransConfig.class);
		if ( cfgAnnot != null )
		{
			msgType.encType = cfgAnnot.encrypt()==ENC_TYPE.DEFAULT?msgType.encType:cfgAnnot.encrypt();;
			msgType.sessType = cfgAnnot.session()==SESS_TYPE.DEFAULT?msgType.sessType:cfgAnnot.session();
			msgType.defendType = cfgAnnot.defend()==DEFEND_TYPE.DEFAULT?msgType.defendType:cfgAnnot.defend();
		}

		//force plain if debug.
		msgType.forcePlain = PropertiesReader.getBoolean("DEBUG.forcePlain");

		//进行方法合法性校验,并获取返回参数.
		parseParas( targetMethod, msgType );

		logger.info("__MESSAGE_TYPE["+msgType.toString()+"]");

		return msgType;
	}

	/**
	 * 组装错误报文.
	 * @param appEx
	 * @return
	 */
	private String packErrorResponse( AppException appEx )
	{
		return packErrorResponse(appEx.getErrCode(),appEx.getErrMsg());
	}

	private String packErrorResponse( String errCode, String errMsg )
	{
		String mark = "";
		if ( PermKeeper.isTest() )
		{
			mark = RandomUtil.randomInt(7)+"_";
			logger.info("__ERRMARK:"+mark+errMsg);
		}
		
		if ( StringUtil.isEmpty(errMsg))
		{
			errMsg = "不好意思,系统开了个小差.";
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
	 * @throws Exception 
	 */
	private String packResponse( TransContext context, MessageType msgType ) throws Exception
	{
		Map rspMap = new HashMap<String,Object>();
		rspMap.put("head", context.getRspHead());
		rspMap.put("body", context.getRspBody()==null?new HashMap():context.getRspBody());

		String retMessage = msgType.getMsgFmt()+encryptMessage( context, msgType, new Gson().toJson(rspMap) );

		return retMessage;
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
	 * @throws Exception 
	 */
	private String decryptMessage( TransContext context, MessageType messageType, String inData ) throws Exception
	{
		//TODEL
		logger.info(String.format("REQ_MSG_ENC:LEN[%d][%s]", inData.length(), inData ));

		//start with E000/P000/
		if ( StringUtil.isEmpty(inData) || inData.length() < 4 )
		{
			throw new AppException(KResponse.INPUT_ERROR, "报文为空或者格式不符合要求" );
		}

		//报文版本类型.
		String msgVer = inData.substring(0, 4);		//E000 FFFF
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
			throw new AppException(KResponse.INPUT_ERROR, KResponse.MSG_INPUT_ERROR );
		}

		return retdata;
	}

	/**
	 * 非登录状态下RSA和AES组合密钥解密.
	 * @param context
	 * @param encData
	 * @return
	 * @throws Exception 
	 */
	private String decryptDataRSA( TransContext context, String encData ) throws Exception
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
		String mysign = MD5Encrypt.MD5(encAESKey+"#"+encJson+"#ebank123" );
		if ( !sign0.equalsIgnoreCase(mysign) )
		{
			logger.error(String.format("ERRCODE[%s][数据签名错误,mysign=%s]",KResponse.TRANS_ILLEGAL,mysign));
			throw new AppException( KResponse.TRANS_ILLEGAL, "报文签名出错." );
		}

		String AESKey = new String( RSATools.getInstance().dencryptData(encAESKey),"UTF-8");

		logger.info("__FROM_APP_AES_KEY:"+AESKey);

		String json = AESTools.decrypt(encJson, AESKey);

		logger.info("__FROM_APP_RSA_DECDATA:"+json);

		//保存密钥用于返回报文加密.
		context.saveTransValue( KBankApp.SECKEY_MARK+ENC_TYPE.RSA_NS, AESKey );

		return "";
	}

	/**
	 * 通过协商的AES密钥解密.
	 * @param context
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	private String decryptDataAES( TransContext context, String inData ) throws Exception
	{
		//解密
		String dr[] = inData.split("#");
		if ( dr.length != 2 )
		{
			logger.error(String.format("ERRCODE[%s][输入数据拼接格式不对]data=%s",KResponse.INPUT_ERROR, inData));
			throw new AppException(KResponse.INPUT_ERROR, KResponse.MSG_INPUT_ERROR);
		}

		String sign0 = dr[0];
		String encJson = dr[1];

		//验证签名.
		String mysign = MD5Encrypt.MD5(encJson+"#"+"ebank123" );
		if ( !sign0.equalsIgnoreCase(mysign))
		{
			logger.error(String.format("ERRCODE[%s][数据签名验证不通过.][mysign=%s]",	KResponse.TRANS_ILLEGAL, mysign ));
			throw new AppException(	KResponse.TRANS_ILLEGAL, "数据签名验证不通过");
		}

		ICTSession session = context.getSession();
		String json = AESTools.decrypt(encJson, session.getEncKey( KBankApp.SECKEY_MARK+ENC_TYPE.AES_SS ));

		logger.info("__FROM_APP_AES_DEC="+json);

		return json;
	}

	/**
	 * 加密请求报文.
	 * @param messageType
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	private String encryptMessage( TransContext context, MessageType messageType, String inData ) throws Exception
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
			ICTSession session = context.getSession();
			retdata = AESTools.encrypt(inData, session.getEncKey( KBankApp.SECKEY_MARK+ENC_TYPE.AES_SS ) );
			break;
		case RSA_NS:
			retdata = AESTools.encrypt(inData, (String)context.getTransValue( KBankApp.SECKEY_MARK+ENC_TYPE.RSA_NS));
			break;
		case PLAIN:
		case DEFAULT:
			break;
		default:
		}

		logger.info(String.format("RSP_MSG_PLAIN:LEN[%d][%s]", retdata.length(), retdata ));

		return retdata;
	}
}
