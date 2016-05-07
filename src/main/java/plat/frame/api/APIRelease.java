package plat.frame.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import plat.tools.JsonCoder;

/**
 * 按照模块返回类名
 * @author zhangcq
 *
 */
public abstract class APIRelease
{
	public abstract List<APIReleaseInfo> queryAPIs();
	
	private Logger logger = Logger.getLogger(APIRelease.class);
	
	protected List<APIReleaseInfo> queryAllAPIs( String urls[] )
	{
		 List<APIReleaseInfo> list = new  ArrayList<APIReleaseInfo>();
		 
		 //无法获取主机地址.
/*		 String hosturl = getHostUrl();
		 if ( hosturl == null || hosturl.trim().length() == 0 )
		 {
			 return null;
		 }*/
		 
		 if ( urls != null && urls.length != 0 )
		 {
			 APIReleaseInfo info = null;
			 for ( int i = 0; i < urls.length; ++i )
			 {
				 logger.info("index="+i+","+urls[i]);
				 if ( urls[i].length() == 0 )
				 {
					 break;
				 }

				 //0,2,4,6
				 if ( i%2 == 0 )
				 {
					 info = new APIReleaseInfo();
					 info.setApiName(urls[i]);
				 }
				 //1,3,5,7
				 else
				 {
					 info.setApiUrl(urls[i]);
					 //返回到list
					 list.add(info);
				 }
			 }
		 }

		 logger.info("__JSON="+JsonCoder.toJsonString(list));
		 return list;
	}
	
/*	private String getHostUrl( )
	{
		 return Properties.getString(KAPI.HOSTURL);
	}*/
}
