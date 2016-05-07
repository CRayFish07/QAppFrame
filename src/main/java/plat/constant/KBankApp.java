package plat.constant;

public class KBankApp {
	
	//老渠道标示。
	public static final String ICP_CHANNEL_TYPE="MB_01";
	//新渠道标示。
	public static final String ICP_CHANNEL_NTYPE="104";
	//新渠道标示。
	public static final String ICP_CHANNEL_MERCHANT="113";
	
	//E钱庄登录渠道，网银.
	public static final String CHTYPE_APP ="EBK";
	public static final String CHTYPE_WEB ="WEB";
	
	//发送交警SDK标示。
	public static final String ICP_CHANNEL_SDK_TYPE="602";

	public static final String vTRANS_SUCCESS = "0000";		// v 表示value

	//从数据结构中获得渠道参数的key
	public static final String kCHANNEL_ID = "_Channel_Id";  //渠道号
	public static final String kBRANCH_ID = "_BRANCH_ID";  	//请求机构号
	public static final String kOPER_ID = "_OPER_ID";  		//操作员号
	public static final String kTERM_NO = "_TERM_NO";  		//操作员号
	
	public static final String vBRANCH_ID = "990101";  	//请求机构号
	public static final String vOPER_ID = "9901815";  	//操作员号
	
	//公共信息模型存放位置.
	public static final String kBUSIDATA_MODULE = "kBUSIDATA_MODULE";
	
	//临时密钥存放位置
	public static final String kTmpAESKey = "_kTmpAESKey";
	
	//交易结果集合.
	public static final String kRET_MAP = "_kRET_MAP";
	
	//交易结果
	public static final String kTRANS_RESULT = "_kTRANS_RESULT";
	
	//存放
	public static final String kCOMM_TYPE = "kCOMM_TYPE";
	
	//01版本数据
	//使用新的头部.
	//使用的body会嵌套Request/Response标签.
	public static final String vCOMM_TYPE_01 = "01";
	
	//测试开关.
	public static final boolean isTest = false;
	
	//UTF-8 字符集
	public static final String CHAR_UTF8 = "utf-8";
	
	//对于返回的列表的itemid键值
	public static final String kITEM_ID = "_item_id";
	
	//存放持有理财产品的列表
	public static final String kFINANCE_HOLDLIST = "_financelist";
	
	//密码数组
	public static final int aespos[] = { 18,20,30,22,0,2,4,6,8,10,12,14,16,25,27,31 };
	
	/*******配置文件选项开关配置开始 *******/
	
	//是否是新账户体系。
	public static final String NEW_ACC = "NEW_ACC";
	
	//是否开启防重复检测功能。
	public static final String CHECK_REPEAT = "CHECK_REPEAT";
	
	//是否开启关键信息打印
	public static final String LOG_ON="LOG_ON";
	
	//是否开启关键信息打印
	public static final String IS_TEST="IS_TEST";
	
	//是否拦截
	public static final String IS_INTCP = "IS_INTCP";
	
	//文件存取主机.
	public static final String DATA_HOST = "datahost";
	
	//文件目录
	public static final String DATA_PATH = "datapath";
	
	//文件类型(即二级目录)
	public static final String DATATYPE_FINAN = "finan";
	
	//主目录
	public static final String DATA_DIR = "DATA_DIR";
	
	//加载的文件名字
	public static final String DATA_FILES = "DATA_FILES";
	public static final String RISK_QUESTION = "risk_question"; //风险评测.
	public static final String SMS_TEMPLATE = "sms_template"; //风险评测.
	
	
	/*******配置文件选项开关配置结束 *******/
	
	//密钥
	public static final int AES_PIN_LEN = 32;
	
	//短信场景
	public static final String SMS_VT_REGISTER = "RG";//注册.
	
	//返回约定枚举值.
	public static final String  YES = "Y"; //是
	public static final String  NO = "N"; //否
	public static final String  TO = "T"; //高净值客户过期
	
	//高净值键值
	public static String K_WELLEVE = "wealthLevel";
	
	//日期格式
	public static String YEAR_TO_DAY = "yyyyMMdd";
	public static String YEAR_TO_SECONDS ="yyyyMMddHHmmss";
	public static String HOUR_TO_SECONDS ="HHmmss";
	public static String MONTH_TO_SECONDS= "MMddHHmmss";
	
	//短信模板
	public static String MESSAGE_TEMP_ONE = "ST01";      //默认模板
	public static String MESSAGE_TEMP_TWO = "ST02";      //转账
	public static String MESSAGE_TEMP_THREE = "ST03";     //添现宝转入
	public static String MESSAGE_TEMP_FOUR = "ST04";      //添现宝转出
	public static String MESSAGE_TEMP_FIVE = "ST05";      //扫码转账
	public static String MESSAGE_TEMP_SIX = "ST06";       //芙蓉宝转入
	public static String MESSAGE_TEMP_SEVEN = "ST07";     //芙蓉宝转出
	public static String MESSAGE_TEMP_EIGHT = "ST08";    
	  
	//安全加固类型
	public static String SECURITY_TYPE="0";//设备绑定类型
	public static String NEEDNT_BINDIP="0";//不需要校验ip
	public static String NEED_BINDDEVICE="1";//需要绑定设备
	public static String NEED_BINDIP="2";//需要校验ip
	public static String NEED_BINDDEVICE_BINDIP="3";//需要绑定设备
	public static String kSECCHECK_BIT = "_kSecCheckBit";	//安全检查位图;值0表示开启,1表示关闭;第0位表示绑定设备,1位表示绑定ip地址.

	//水煤电业务类型
	public static String BUSI_TYPE_WATER = "D4";	//水
	public static String BUSI_TYPE_COAL = "D3";		//煤
	public static String BUSI_TYPE_POWER = "D1";	//电
	public static String BUSI_TYPE_MOBILE = "I1";	//手机缴话费
	
	public static char DO_CHECK = '1';		//需要检查.
	public static char NO_CHECK = '0';		//不需要检查.
}
