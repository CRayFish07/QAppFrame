package wdtree.db.dao;

import wdtree.db.beans.Blog;
import wdtree.db.beans.BlogExample;

public interface BlogMapper {
    int countByExample(BlogExample example);

    int insert(Blog record);

    int insertSelective(Blog record);
}