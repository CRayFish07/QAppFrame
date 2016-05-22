package std.mbt.direct;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface BlogMapper
{
	@Select("SELECT * FROM Blog WHERE state = #{state}")
	Blog selectBlog(String state);
	
	@Insert("INSERT INTO Blog values(#{title},#{content},#{rank},'0000',now())")
	int addBlog(Blog blog);
}
