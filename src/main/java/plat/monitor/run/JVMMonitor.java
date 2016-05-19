package plat.monitor.run;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import plat.tools.DateUtil;

import com.google.gson.Gson;

public class JVMMonitor
{
	public static void main(String[] args)
	{
		JVMMonitor mon = new JVMMonitor();
		mon.doMemoryMonior();
	}
	 
	public String doMemoryMonior()
	{
		//统计内容信息.
		List<MemoryItem> list = new ArrayList<MemoryItem>();
		List<MemoryPoolMXBean> memoryList = ManagementFactory.getMemoryPoolMXBeans();
		if ( memoryList == null || memoryList.size() == 0 )
		{
			return "";
		}
		
		for ( MemoryPoolMXBean bean : memoryList )
		{
			MemoryItem item = new MemoryItem();

			item.setIP(getLocalIp());
			item.setName(bean.getName());
			item.setDate(DateUtil.todayStr(DateUtil.FMT_YHS));
			item.setType(bean.getType()+"");
			
			item.setCollection(bean.getCollectionUsage());
			item.setPeak( bean.getPeakUsage());
			item.setNow( bean.getUsage());
			
			list.add(item);
		}
		
		String json = new Gson().toJson(list);
		log( json );
		return json;
	}
	
	public String getLocalIp()
	{
		InetAddress address;
		try {
			address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static void log( String logMsg )
	{
		System.out.println( logMsg );
	}
}
