package trans.worldtree.ulogin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import plat.frame.app.impl.TransContext;
import study.mbt.domain.User;
import study.mbt.service.UserServiceImpl;
import bean.app.req.ReqUserLogin;
import bean.app.rsp.RspUserLogin;

@Component
public class UserLoginTrans
{
	@Autowired
	UserServiceImpl userService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	public void login( TransContext context, ReqUserLogin reqModel, RspUserLogin rspModel )
	{
		User user = new User();
		user.setUserName("testUser");
		user.setUserId("0098181");
		user.setUserSalary(80.99);
		userService.addUser(user);
		rspModel.setUserId("0089155");
	}
}
