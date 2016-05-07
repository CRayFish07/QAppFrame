package plat.constant;

public class KResponse {
	
	/**
	 *  0. 特等！响应码8001-8999为特殊号段 有特殊用途，请不要私自占用.
	 */
	//重新拉取客户信息.
	public static final String PULL_CUST_AGAIN = "8006";
	
	//重新拉取卡信息.
	public static final String PULL_CARD_AGAIN = "8007";
	
	//手势密码关闭
	public static final String CLOSE_HANDPIN = "8008";
	
	//手势密码过期
	public static final String RESET_HANDPIN = "8009";
	
	/****************
	  			1. 响应枚举值
	 						*************/
	/* 结果map中的交易码和交易信息 */
	public static final String kRETCODE = "STATUS";
	public static final String kRETMSG = "MSG";
	public static final String kACTION = "ACTION"; //行为.
	
	/**************************
	 *		 2. 数据库操作响应码						*
	 				*************************/
	//数据库增加失败.
	public static final String DB_ADD_FAIL = "4001";
	
	//数据库删除失败
	public static final String DB_DELETE_FAIL = "4002";
	
	//数据库修改失败.
	public static final String DB_UPDATE_FAIL = "4003";
	
	//数据库查询失败.
	public static final String DB_SELECT_FAIL = "4004";
	
	//数据库数据状态：有效
	public static final String VALID_STATUS="00";
	
	//数据库数据状态：wu
	public static final String INVALID_STATUS="01";
	
	/***************
	  			4. 公共响应码 
	  					***************/
	//失败.
	public static final String FAIL = "9999";
	
	//成功.
	public static final String SUCCESS = "0000";
	
	//会话超时.
	public static final String SESSION_TIMEOUT = "0005";
	
	//网络繁忙，请稍后再试。
	public static final String NET_BUSY = "1001";
	
	//交易非法。这类交易不要给出明显的提示信息.
	public static final String TRANS_ILLEGAL = "1002";
	
	//输入数据检查不正确或者不完整.
	public static final String INPUT_ERROR = "1004";
	
	//发送渠道失败.
	public static final String ICOP_TRANS_FAIL = "1005";
	
	//渠道交易失败.
	public static final String ICOP_RET_FAIL = "1006";

	//渠道返回数据失败.
	public static final String ICOP_DATA_ERROR = "10061";
	
	//渠道返回的数据不符合预期.
	public static final String ICOP_DATA_UNEXP = "10062";
	
	//没有找到交易记录
	public static final String NO_INFO_FOUND = "1007";
	
	//没有更新记录
	public static final String NO_UPDATE = "1008";
	
	//未开理财卡
	public static final String NO_FINACECARD = "1009";
	
	//文件不存在
	public static final String FILE_NOT_FOUND = "1010";
	
	//交易配置不正确.
	public static final String TRANS_CONF_ERROR = "1011";
	
	//密码解密失败
	public static final String AES_DECODE_FAILED = "1012";
	
	//RSA密码解密失败
	public static final String RSA_DECODE_FAILED = "1013";
	
	//名字和身份证验证失败.
	public static final String ID_CHECK_FAILED = "1014";
	
	//密码验证失败
	public static final String CARD_PIN_FAILED = "1015";
	
	//交易重复
	public static final String TRANS_REPEAT = "1016";
	
	//交易数据校验非法.
	public static final String DATA_ILLEGAL = "1017";
	
	//交易过于频繁
	public static final String DEAL_FREQUENT = "1018";
	
	//交易密码错
	public static final String AUTH_ERR = "1200";
	
	//交易过期
	public static final String TRANS_OVERDUE="1201";
	
	//验证码验证失败
	public static final String VALID_FAILED = "1202";
	
	//短信验证失败.
	public static final String SMS_VERIFY_ERR = "1203";
	/*************
	 * 权限检查
	 */
	
	//交易正在维护
	public static final String TRANS_NOT_AVAILALBLE = "2001";
	
	public static final String TRANSFER_ICOP_MEANS = "2002";
	
	//一般对外消息.用于隐藏真实关键错误信息.注意有三个...
	public static final String MSG_SUCCESS = "交易成功";
	public static final String MSG_NET_BUSY = "网络繁忙哦，请稍后重试.";
	public static final String MSG_ILLEGAL = "交易失败，请检查客户端是否为最新版本...";
	public static final String MSG_FAIL = "交易失败.";
	public static final String MSG_DATA_LOST = "数据缺失,你懂的.";
	//用户拉取客户信息 保存的随机密钥出现异常情况时
	public static final String MSG_SYS_ERROR = "系统错误.";
	public static final String MSG_QUIT_LOAD = "网络错误，请退出客户端，登录后再交易...";
	
	public static final String MSGI_ENC_ERROR = "加密失败.";
	public static final String MSG_DEAL_FREQUENT = "操作太过频繁，请稍后再试";
	public static final String MSG_CUMODEL_LOST = "客户信息获取失败，请尝试重新登录.";
	
	//短信二次验证失败.
	public static final String MSG_SMSVERIFY_ERR = "短信验证失败.";
	public static final String MSG_SMSVERIFY_ERR2 = "短信验证失败2.";
	
	public static final String TRANS_AMT_ZERO ="交易金额不能为0.";
	public static final String VALUES_IS_NULL ="客户端传送字段为空.";
	public static final String ACCOUNT_NOT_FOUND ="该账号不存在，请核实后重新输入.";
	
	public static final String NO_CUSTINFO_GET = "客户信息失败,请尝试重新登录";
	
	public static final String CHECK_PIN_MARK ="1";
	/*****************
	 * 交易状态          成功:1  失败：2 处理中：3
	 */
	public static final String TRANS_STATUS_SUCESS="1";
	public static final String TRANS_STATUS_FAILURE="2";
	public static final String TRANS_STSTUS_PROCING="3";
	
	
	/**
	 * 补充数据
	 */
	public static final String BANK_NAME="长沙银行";
	public static final String BANK_NO="313551088886";
	
	
	/**
	 * 特殊异常提示
	 */
	public static final String ACTION_POMSG = "POPMSG";
}
