package trans.worldtree.ulogin;

import org.apache.log4j.Logger;

import plat.frame.app.impl.TransContext;
import bean.app.req.ReqUserLogin;
import bean.app.rsp.RspUserLogin;

public class UserLoginTrans
{
	private Logger logger = Logger.getLogger(getClass());
	
	public void login( TransContext context, ReqUserLogin reqModel, RspUserLogin rspModel )
	{
		rspModel.setUserId("0089155");
	}
}
