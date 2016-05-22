package trans.std.wklogin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plat.frame.app.impl.TransContext;
import std.mbt.service.WorkerServiceImpl;
import std.mbt.service.Worker;

@Component
public class UserLoginTrans
{
	@Autowired
	WorkerServiceImpl userService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	public void login( TransContext context, ReqUserLogin reqModel, RspUserLogin rspModel )
	{
		Worker user = new Worker();
		user.setUserName("testUser");
		user.setUserId("0098181");
		user.setUserSalary(80.99);
		userService.addUser(user);
		rspModel.setUserId("0089155");
	}
}
