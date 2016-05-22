package trans.wdt.rlogin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plat.frame.app.impl.TransContext;
import plat.tools.JsonCoder;
import plat.tools.RandomUtil;
import wdt.dbase.service.CustInfoService;
import wdt.server.beans.req.ReqUserLogin;
import wdt.server.beans.rsp.RspUserLogin;

@Component
public class UserLoginTrans
{
	private Logger logger = Logger.getLogger(UserLoginTrans.class);

	@Autowired
	private CustInfoService custInfoService;

	/**
	 * @param context
	 * @param reqModel
	 * @param rspModel
	 */
	public void login( TransContext context, ReqUserLogin reqModel, RspUserLogin rspModel )
	{

		rspModel.setToken(RandomUtil.getRandomSequence(20));
		logger.info("__LOGIN:"+JsonCoder.toJsonString(rspModel));

		return;
	}
}
