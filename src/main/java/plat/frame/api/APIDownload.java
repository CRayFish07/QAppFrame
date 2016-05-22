package plat.frame.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import plat.frame.api.meta.APIBeanInfo;
import plat.frame.api.meta.FieldEntity;
import plat.frame.app.impl.BeanParser;
import plat.tools.PermKeeper;
import plat.tools.StringUtil;

@Controller
public class APIDownload extends BeanParser
{
	private String pkgName = "package XXXX;";
	
	Logger logger = Logger.getLogger(this.getClass());
	
	/***
	 * 为客户端生成bean代码
	 * @param request
	 * @param response
	 * @param filename
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping( value="/api/download/{ostype}/{filename}",method=RequestMethod.GET )
	public void autoCodeApple( HttpServletRequest request, HttpServletResponse response,
								@PathVariable String ostype, @PathVariable String filename,
								@RequestParam  String fullpath ) throws UnsupportedEncodingException
	{
		//不对生产开放.
		if ( !PermKeeper.isTest() )
		{
			return;
		}

		logger.info(String.format("__DOWNLOAD/%s/%s",ostype,filename));

		response.setContentType("application/octet-stream");
		//		response.setContentType("text/html");
		String codefile = "";
		if("ios".equals(ostype))
		{
			codefile = autoAppleCodeFile( fullpath );
		}
		else
		{
			codefile = autoJavaCodeFile( fullpath );
		}

		OutputStream ostream;
		try
		{
			ostream = response.getOutputStream();
			ostream.write(codefile.getBytes("utf-8"));
			ostream.flush();
			ostream.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成Java代码.
	 * @param fullpath
	 * @return
	 */
	private String autoJavaCodeFile( String fullpath )
	{
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullpath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		APIBeanInfo beanInfo = doParseBeanInfo(clazz);
		
		StringBuffer buffer = new StringBuffer();
//		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		String className = clazz.getSimpleName();
		
		//打印包名
		addLine( buffer, pkgName+"\n\n" );
		
		//打印头
		printHeader(buffer,beanInfo);
		
		buffer.append(String.format("public class %s\n{\n",className));
		
		//写入版本信息.
		addLine(buffer,"\t//接口版本信息\n\tprivate String _apiVersion = \"%s\";",beanInfo.getBeanVersion()==null?"":beanInfo.getBeanVersion());
		
		for ( FieldEntity entity : beanInfo.getFieldList() )
		{
			//打印注释.
			String line = "\n";
			if ( entity.isRequired() || !StringUtil.isEmpty(entity.getFdChName()))
			{
				String nulltag = "";
				if (entity.isRequired())
				{
					nulltag = "not null:\t";
				}
				
				buffer.append(String.format("\n\t//%s%s  %s\n",
						nulltag,entity.getFdChName(), entity.getDesc()==null?"":entity.getDesc() ));
				line = "";
			}
			
			//打印域名.
			buffer.append(String.format("%s\tprivate %s %s;\n", line, convertTypeName4Java(entity), entity.getFdName()));
		}
		
		buffer.append("\n\t//getter-setters\n\t\n}\n");
		
		return buffer.toString();
	}
	
	/**
	 * 生成iOS代码
	 * @param fullpath
	 * @return
	 */
	private String autoAppleCodeFile( String fullpath )
	{
		Class<?> clazz = null;
		try {
			clazz = Class.forName(fullpath);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		APIBeanInfo beanInfo = doParseBeanInfo(clazz);

		StringBuffer buffer = new StringBuffer();
		//		ByteArrayOutputStream ostream = new ByteArrayOutputStream();
		String className = clazz.getSimpleName();

		//打印头
		printHeader(buffer,beanInfo);

		buffer.append(String.format("@interface %s:NSObject\n\n",className));
		for ( FieldEntity entity : beanInfo.getFieldList() )
		{
			//打印域名.
			buffer.append(String.format("@property (nonatomic,copy) %s%s;", convertTypeName4Apple(entity), entity.getFdName()));

			if ( entity.isRequired() || !StringUtil.isEmpty(entity.getFdChName()))
			{
				String nulltag = "";
				if (entity.isRequired())
				{
					nulltag = "not null:\t";
				}

				buffer.append(String.format("/**<%s%s%s*/\n",
						nulltag,entity.getFdChName(), entity.getDesc()==null?"":entity.getDesc() ));
			}
		}

		buffer.append("\n@end\n");

		return buffer.toString();
	}
	
	/**
	 * 转换Java类型.
	 * @param entity
	 * @return
	 */
	private String convertTypeName4Java( FieldEntity entity )
	{
		String typeName = entity.getType();

		if ( "String".equals(typeName))
		{
			return "String";
		}
		else if ( "Integer".equals(typeName) )
		{
			return "int";
		}
		else if ( "Double".equals(typeName) )
		{
			return "double";
		}
		else if ( "Long".equals(typeName) )
		{
			return "long";
		}
		else if ( "List".equals(typeName) )
		{
			return String.format("List<%s>",entity.getBeanInfo().getBeanName());
		}
		
		return typeName;
	}
	
	/**
	 * 转换iOS类型.
	 * @param entity
	 * @return
	 */
	private String convertTypeName4Apple( FieldEntity entity )
	{
		String typeName = entity.getType();

		if ( "String".equals(typeName))
		{
			return "NSString *";
		}
		else if ( "Integer".equals(typeName) )
		{
			return "int";
		}
		else if ( "Double".equals(typeName) )
		{
			return "double";
		}
		else if ( "Long".equals(typeName) )
		{
			return "long";
		}
		else if ( "List".equals(typeName) )
		{
			return String.format("List<%s>",entity.getBeanInfo().getBeanName());
		}
		
		return typeName;
	}
	
	/**
	 * 增加行.
	 * @param buffer
	 * @param format
	 * @param args
	 */
	private void addLine( StringBuffer buffer, String format, String args )
	{
		String strLine = String.format(format,args);
		buffer.append(strLine+"\n");
	}
	
	private void addLine( StringBuffer buffer, String line )
	{
		if ( StringUtil.isEmpty(line))
		{
			line = "";
		}
		buffer.append(line+"\n");
	}
	
	/**
	 * 打印代码头.
	 * @param buffer
	 * @param beanInfo
	 */
	private void printHeader( StringBuffer buffer, APIBeanInfo beanInfo )
	{
		addLine( buffer, "/*****\n\tVersion: %s", beanInfo.getBeanVersion() );
		addLine( buffer,"\tDescription: %s",beanInfo.getBeanDesc());
		addLine( buffer,"\tModified:\n\t\t%s",StringUtil.concat(beanInfo.getBeanUpdate(), "\n\t\t" ) );
		addLine( buffer, "*****/\n" );
	}
}
