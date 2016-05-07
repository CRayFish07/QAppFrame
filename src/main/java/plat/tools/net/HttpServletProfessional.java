package plat.tools.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import plat.tools.DateUtil;

public class HttpServletProfessional
{
	private boolean timeoutflag = false;
	private int maxlen = -1;

	//请求记录.
	private ConcurrentHashMap<String,Thread> readMap = new ConcurrentHashMap<String,Thread>();

	/**
	 * 读取数据
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public byte[] readInput( HttpServletRequest request ) throws IOException, InterruptedException
	{
		return readInput( request, maxlen );
	}

	/**
	 * 读取数据,有超时策略.
	 * 需要后台线程扫描map.
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private byte[] readInput( HttpServletRequest request, int readmax ) throws IOException, InterruptedException
	{
		//默认一次读取4k
		int blocksize = 4*1024;
		int totallen = request.getContentLength();	//获取总长度.

		//数据大小检查.
		if ( readmax != -1 && totallen > readmax )
		{
			throw new IOException("DataTooLargeException:MaxLen="+readmax+":ActualLen="+totallen);
		}

		//后台线程扫描读取记录,清理超时.
		String readkey = null;
		if ( timeoutflag )
		{
			readkey = DateUtil.todayStr(DateUtil.FMT_ALL);		//高并发这里存在key重复的时候,不准确.
			readMap.put(readkey,Thread.currentThread());
		}

		//初始化字节流.
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();

		InputStream instream = null;
		try
		{
			//获取输入流
			instream = request.getInputStream();

			byte[] readdata = new byte[blocksize];
			int leftlen = totallen;
			while ( true )
			{
				int readlen = instream.read( readdata );
				if ( readlen == -1 )	//EOF
				{
					break;
				}

				outstream.write( readdata, 0, readlen );

				//读完了.
				leftlen -= readlen;
				if ( leftlen <= 0 )
				{
					break;
				}
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			//清理输入流
			if ( instream != null )
			{
				instream.close();
			}

			//移除Map
			if ( readkey != null )
			{
				readMap.remove(readkey);
			}
			
			outstream.close();
		}

		return outstream.toByteArray();
	}

	//单例模式>>>>
	private HttpServletProfessional()
	{
	}

	public static final class Holder
	{
		public static final HttpServletProfessional instance = new HttpServletProfessional();
	}

	public static HttpServletProfessional getInstance()
	{
		return Holder.instance;
	}
	//<<<<<

	//getter-setters... >>>>
	public boolean isTimeoutflag() {
		return timeoutflag;
	}

	public HttpServletProfessional setTimeoutflag(boolean timeoutflag) {
		this.timeoutflag = timeoutflag;
		return this;
	}
	
	public int getMaxlen() {
		return maxlen;
	}

	public HttpServletProfessional setMaxlen(int maxlen) {
		this.maxlen = maxlen;
		return this;
	}
}
