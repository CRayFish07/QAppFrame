package wdt.dbase.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import wdt.dbase.beans.CustInfo;
import wdt.dbase.beans.CustInfoExample;

public interface CustInfoService
{

    int deleteByPrimaryKey(String acc);

    int insert(CustInfo record);

    int insertSelective(CustInfo record);

    List<CustInfo> selectByExample(CustInfoExample example);

    CustInfo selectByPrimaryKey(String acc);

    int updateByExampleSelective(@Param("record") CustInfo record, @Param("example") CustInfoExample example);

    int updateByExample(@Param("record") CustInfo record, @Param("example") CustInfoExample example);

    int updateByPrimaryKeySelective(CustInfo record);

    int updateByPrimaryKey(CustInfo record);

}
