package plat.frame.api.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法实体
 * @author zhangcq
 *
 */
public class APIEntity
{
	//接口版本
	private String apiVersion;
	
	//变更内容
	private String[] apiUpdates;
	
	//接口中文名
	private String name;
	
	//接口描述
	private String desc;
	private String group;

	//接口示例.
	private String reqExample;
	private String rspExample;
	
	//请求列表字段
//	List<FieldEntity> reqList;
	private APIBeanInfo reqBeanInfo;
	
	//返回列表字段
//	List<FieldEntity> rspList;
	private APIBeanInfo rspBeanInfo;
	
	//下载bean的字段,用于生成下载文件列表.
	List<ClazzInfo> beanList;
	
	//增加beanlist;
	public void addBean( String clzname, String path )
	{
		if ( beanList == null )
		{
			beanList = new ArrayList<ClazzInfo>();
		}
		
		ClazzInfo beanInfo = new ClazzInfo();
		beanInfo.setClazzName(clzname);
		beanInfo.setFullPath(path);
		beanList.add(beanInfo);
	}
	
	//====getter-setters
	
	public String getName() {
		return name;
	}
	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String[] getApiUpdates() {
		return apiUpdates;
	}

	public void setApiUpdates(String[] apiUpdates) {
		this.apiUpdates = apiUpdates;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getReqExample() {
		return reqExample;
	}
	public void setReqExample(String reqExample) {
		this.reqExample = reqExample;
	}
	public String getRspExample() {
		return rspExample;
	}
	public void setRspExample(String rspExample) {
		this.rspExample = rspExample;
	}

	public APIBeanInfo getReqBeanInfo() {
		return reqBeanInfo;
	}

	public void setReqBeanInfo(APIBeanInfo reqBeanInfo) {
		this.reqBeanInfo = reqBeanInfo;
	}

	public APIBeanInfo getRspBeanInfo() {
		return rspBeanInfo;
	}

	public void setRspBeanInfo(APIBeanInfo rspBeanInfo) {
		this.rspBeanInfo = rspBeanInfo;
	}

	public List<ClazzInfo> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<ClazzInfo> beanList) {
		this.beanList = beanList;
	}
	
}
