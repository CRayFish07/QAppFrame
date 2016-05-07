package plat.frame.app.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import plat.frame.api.APIBeanInfo;
import plat.frame.api.APIEntity;
import plat.frame.api.FieldEntity;
import plat.frame.api.annonation.APIDefiner;
import plat.frame.api.annonation.FieldDefiner;
import plat.tools.StringUtil;

public class BeanParser
{
	protected Logger logger = Logger.getLogger(this.getClass());
	
	//解析bean获取list
	public APIBeanInfo doParseBeanInfo( Class<?> beanclass )
	{
		return doParseBeanInfo( beanclass, null );
	}

	//解析bean获取field list.
	/**
	 * 
	 * @param beanclass
	 * @param apiEntity 这个类是由于要把所有文件列表返回到界面才作为入参的.
	 * @return
	 */
	public APIBeanInfo doParseBeanInfo( Class<?> beanclass, APIEntity apiEntity )
	{
		APIBeanInfo beanInfo = new APIBeanInfo();
		
		beanInfo.setBeanName(beanclass.getSimpleName());
		beanInfo.setFullName(beanclass.getName());
		
		//获取bean的版本信息.
		APIDefiner apidefiner = beanclass.getAnnotation(APIDefiner.class);
		if ( apidefiner != null )
		{
			beanInfo.setBeanVersion(apidefiner.version());
			beanInfo.setBeanUpdate(apidefiner.updates());
			beanInfo.setBeanDesc(StringUtil.concat(apidefiner.desc(), "\n"));
		}

		//无法获取继承的属性,而通过方法分析可以,所以bean都有getter-setter方法.
		List<FieldEntity> fdlist = new ArrayList<FieldEntity>();
		Field[] fds = beanclass.getDeclaredFields();
		for ( int i = 0; i < fds.length; ++i )
		{
			Field field = fds[i];
			FieldEntity  fdentity = new FieldEntity();
			
			fdentity.setType(field.getType().getSimpleName());
			fdentity.setFdName(field.getName());
			
			FieldDefiner fddefine = field.getAnnotation(FieldDefiner.class);
			if ( fddefine != null )
			{
				fdentity.setFdChName(fddefine.name());
				fdentity.setRequired(fddefine.required());
				fdentity.setDesc(fddefine.desc());
				fdentity.setLength("");
			}
			
			//遇到列表成员.
			if ( fddefine != null && fddefine.classT().length == 1 )
			{
				Class<?> tmpclz = fddefine.classT()[0];
				
				if ( apiEntity != null )
				{
					apiEntity.addBean(tmpclz.getSimpleName(), tmpclz.getName());
				}
				
				fdentity.setBeanInfo(doParseBeanInfo(tmpclz,apiEntity));
				
				logger.info(String.format("__BEAN=%s,%s", tmpclz.getSimpleName(), tmpclz.getName()));
			}
			
			fdlist.add(fdentity);
		}
		
		beanInfo.setFieldList(fdlist);
		
		return beanInfo;
	}
}
