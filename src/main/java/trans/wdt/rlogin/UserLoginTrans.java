package trans.wdt.rlogin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import plat.frame.app.impl.TransContext;
import plat.tools.JsonCoder;
import plat.tools.RandomUtil;
import wdt.dbase.beans.CustInfo;
import wdt.dbase.beans.CustInfoExample;
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
		CustInfoExample express = new CustInfoExample();
		CustInfoExample.Criteria criteria = express.createCriteria();
		criteria.andMobileNoEqualTo(reqModel.getMobileNo());

		List<CustInfo> list = custInfoService.selectByExample(express);
		Assert.notEmpty(list,"@客户尚未注册"+reqModel.getMobileNo());

		rspModel.setToken(RandomUtil.getRandomSequence(20));
		logger.info("__LOGIN:"+JsonCoder.toJsonString(rspModel));

		return;
	}
}
