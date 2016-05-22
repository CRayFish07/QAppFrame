package study.mbt.direct;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import plat.tools.JsonCoder;
import plat.tools.XLog;

public class BatisTest
{
	public static void main(String[] args) throws IOException
	{
		String resource = "inconf/mybatis/mybatis-config.xml";
		
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory  sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		inputStream.close();
		
		XLog.log("%s", "SqlSessionFactory");

		SqlSession session = sqlSessionFactory.openSession();
		try {
			Blog blog = (Blog) session.selectOne("plat.db.mybatis.BlogMapper.selectBlog", "0000");
			//Blog blog = (Blog) session.selectOne("BlogMapper.selectBlog", "0000");
			XLog.log("RET:%s", JsonCoder.toJsonString(blog));
			
			blog = new Blog();
			blog.setTitle("增加的");
			blog.setContent("内容");
			blog.setRank(8);
			int ret = session.insert("plat.db.mybatis.BlogMapper.addBlog", blog);
			XLog.log("RET:%d", ret);
//			session.commit();
		} finally {
			session.close();
		}
	}
}
