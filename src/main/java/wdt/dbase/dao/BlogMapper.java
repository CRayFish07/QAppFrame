package wdt.dbase.dao;

import wdt.dbase.beans.Blog;
import wdt.dbase.beans.BlogExample;

public interface BlogMapper {
    int countByExample(BlogExample example);

    int insert(Blog record);

    int insertSelective(Blog record);
}