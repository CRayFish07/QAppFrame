package std.mbt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gacl
 * 使用@Service注解将UserServiceImpl类标注为一个service
 * service的id是userService
 */
@Service("workerService")
public class WorkerServiceImpl
{
    /**
     * 使用@Autowired注解标注userMapper变量，
     * 当需要使用UserMapper时，Spring就会自动注入UserMapper
     */
    @Autowired
    private WorkerMapper userMapper;//注入dao

    public void addUser(Worker user) {
        userMapper.insert(user);
    }

    public Worker getUserById(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
