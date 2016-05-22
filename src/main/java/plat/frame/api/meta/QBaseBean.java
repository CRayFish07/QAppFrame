package plat.frame.api.meta;

/**
 * 报文头，也即所有接口的父类。
 * @author zhangcq
 *
 */
public class QBaseBean
{
	public int _api_ = 1; //api版本.

	public int get_api_() {
		return _api_;
	}

	public void set_api_(int _api_) {
		this._api_ = _api_;
	}
}
