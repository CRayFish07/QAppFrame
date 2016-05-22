package plat.frame.api.meta;

import java.util.List;

public class APIBeanInfo
{
	//bean版本.
	private String beanVersion;
	
	//bean更新记录.
	private String[] beanUpdate;
	
	//bean名称
	private String beanName;
	
	//bean描述
	private String beanDesc;
	
	//bean全限定名
	private String fullName;
	
	//返回列表字段
	private List<FieldEntity> fieldList;

	public List<FieldEntity> getFieldList() {
		return fieldList;
	}

	public String getBeanVersion() {
		return beanVersion;
	}

	public void setBeanVersion(String beanVersion) {
		this.beanVersion = beanVersion;
	}

	public String[] getBeanUpdate() {
		return beanUpdate;
	}

	public void setBeanUpdate(String[] beanUpdate) {
		this.beanUpdate = beanUpdate;
	}

	public void setFieldList(List<FieldEntity> fieldList) {
		this.fieldList = fieldList;
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBeanDesc() {
		return beanDesc;
	}

	public void setBeanDesc(String beanDesc) {
		this.beanDesc = beanDesc;
	}
}
