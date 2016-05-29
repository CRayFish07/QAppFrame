package plat.constant;

public class KResponse
{
	/**********
	 * 响应吗
	 */
	//成功 
	public static final String SUCCESS = "0000";
	
	//失败.用于不长见的错误。
	public static final String FAIL = "9999";
	
	//会话超时.
	public static final String SESSION_TIMEOUT = "0001";
	
	//请求数据不正确或者缺失.
	public static final String INPUT_ERROR = "0002";
	
	//客户尝试做越权操作,交易不合法.
	public static final String TRANS_ILLEGAL = "0003";
	
	//加解密失败.
	public static final String ENCRYPT_ERR = "0004";
	
	/*******
	 *  交易码相应信息.MSG_***
	 *  */
	public static final String MSG_NETBUSI = "网络繁忙.";
	public static final String MSG_SYSERR = "系统错误.";
	
	public static final String MSG_SUCCESS = "交易成功";
	public static final String MSG_FAIL = "交易失败";
	public static final String MSG_INPUT_ERROR = "请求数据不正确或者缺失.";
	public static final String MSG_TRANS_ILLEGAL = MSG_NETBUSI;
	public static final String MSG_ENCRYPT_ERR = MSG_SYSERR;
	
	/*********
	 * Redis response.
	 */
	public static final String RET_OK = "OK";
}
