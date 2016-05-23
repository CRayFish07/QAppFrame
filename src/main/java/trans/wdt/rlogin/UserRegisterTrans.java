package trans.wdt.rlogin;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plat.frame.app.impl.TransContext;
import plat.tools.JsonCoder;
import plat.tools.RandomUtil;
import wdt.dbase.beans.CustInfo;
import wdt.dbase.service.CustInfoService;
import wdt.server.beans.req.ReqRegister;
import wdt.server.beans.rsp.RspRegister;

@Component
public class UserRegisterTrans
{
	private Logger logger = Logger.getLogger(UserRegisterTrans.class);
	
	@Autowired
	private CustInfoService custInfoService;
	
	public void register( TransContext context, ReqRegister reqModel, RspRegister rspModel )
	{
		CustInfo cinfo = new CustInfo();
		cinfo.setAcc(RandomUtil.getRandomSequence(8));
		cinfo.setMobileNo(reqModel.getMobileNo());
		cinfo.setPin(reqModel.getPin());
		cinfo.setLtime(new Date());
		
		custInfoService.insert(cinfo);
		
		rspModel.setToken(RandomUtil.getRandomSequence(20));
		
		logger.info("__REGISTER:"+JsonCoder.toJsonString(cinfo));
		
		return;
	}
}
