package study.mbt.dao;

import study.mbt.domain.User;

/**
 * 该接口不用被扫描也可以被注入的原因是:
 * 			因为在UserMapper.xml中已经存有该类信息.
 * @author zhangcq
 *
 */
public interface UserMapper {
	int deleteByPrimaryKey(String userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(String userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);
}
