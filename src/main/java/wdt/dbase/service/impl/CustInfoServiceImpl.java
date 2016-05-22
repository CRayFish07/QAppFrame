package wdt.dbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wdt.dbase.beans.CustInfo;
import wdt.dbase.dao.CustInfoMapper;
import wdt.dbase.service.CustInfoService;

@Service
public class CustInfoServiceImpl implements CustInfoService
{
	@Autowired
	private CustInfoMapper mapper;

	@Override
	public int addCustInfo(CustInfo custInfo) {
		// TODO Auto-generated method stub
		return mapper.insert(custInfo);
	}

	@Override
	public CustInfo selectCustInfo(String accno) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(accno);
	}
}
