package plat.frame.api;

/**
 * 报文头，也即所有接口的父类。
 * @author zhangcq
 *
 */
public class BaseRequestBean
{
	protected String _reqVersion; //api版本.

	public String get_reqVersion() {
		return _reqVersion;
	}
	public void set_reqVersion(String _reqVersion) {
		this._reqVersion = _reqVersion;
	}
}
