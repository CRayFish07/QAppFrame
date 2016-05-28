package plat.frame.app.session;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

/**
 * Redis会话实现类.
 * @author zhangcq
 *
 */
@Component
public class CTRedisSession implements ICTSession
{
	private String sessToken;
	private ConcurrentHashMap<String, Object> dataMap;
	
	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setValue(String key, String value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object getObj(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setObj(String key, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getSessToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean destroySess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getEncKey(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setEncKey(String type, String encKey) {
		// TODO Auto-generated method stub
		return false;
	}
}
