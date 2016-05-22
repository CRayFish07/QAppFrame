package plat.frame.api.meta;

/**
 * 这个类用于保存生成下载URL文件的信息.
 * @author zhangcq
 *
 */
public class ClazzInfo
{
	private String clazzName;		//类名
	private String fullPath;		//全路径.
	
	public String getClazzName() {
		return clazzName;
	}
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
}
