package plat.frame.api;

import java.util.List;

/**
 * 字段域实体
 * @author zhangcq
 *
 */
public class FieldEntity
{
	private String fdName;
	private String fdChName;
	private String type;			// List,String,int,double,long
	private String length;
	private String desc;
	private boolean required;
	
	//list的泛型.
	private APIBeanInfo beanInfo;	// 泛型bean信息
	
	public String getFdName() {
		return fdName;
	}
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	public String getFdChName() {
		return fdChName;
	}
	public void setFdChName(String fdChName) {
		this.fdChName = fdChName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public APIBeanInfo getBeanInfo() {
		return beanInfo;
	}
	public void setBeanInfo(APIBeanInfo beanInfo) {
		this.beanInfo = beanInfo;
	}
}
