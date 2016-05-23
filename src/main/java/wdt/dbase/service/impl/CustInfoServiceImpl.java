package wdt.dbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wdt.dbase.beans.CustInfo;
import wdt.dbase.beans.CustInfoExample;
import wdt.dbase.dao.CustInfoMapper;
import wdt.dbase.service.CustInfoService;

@Service
public class CustInfoServiceImpl implements CustInfoService
{
	@Autowired
	private CustInfoMapper mapper;

	@Override
	public int deleteByPrimaryKey(String acc) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(CustInfo record) {
		// TODO Auto-generated method stub
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(CustInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CustInfo> selectByExample(CustInfoExample example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public CustInfo selectByPrimaryKey(String acc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(CustInfo record, CustInfoExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(CustInfo record, CustInfoExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(CustInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(CustInfo record) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
