package wdt.dbase.dao;

import wdt.dbase.beans.CustInfo;
import wdt.dbase.beans.CustInfoExample;

public interface CustInfoMapper {
    int countByExample(CustInfoExample example);

    int deleteByPrimaryKey(String acc);

    int insert(CustInfo record);

    int insertSelective(CustInfo record);

    CustInfo selectByPrimaryKey(String acc);

    int updateByPrimaryKeySelective(CustInfo record);

    int updateByPrimaryKey(CustInfo record);
}