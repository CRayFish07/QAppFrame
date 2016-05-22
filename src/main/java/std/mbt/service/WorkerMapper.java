package std.mbt.service;


/**
 * 该接口不用被扫描也可以被注入的原因是:
 * 			因为在UserMapper.xml中已经存有该类信息.
 * @author zhangcq
 *
 */
public interface WorkerMapper {
	int deleteByPrimaryKey(String userId);

	int insert(Worker record);

	int insertSelective(Worker record);

	Worker selectByPrimaryKey(String userId);

	int updateByPrimaryKeySelective(Worker record);

	int updateByPrimaryKey(Worker record);
}
