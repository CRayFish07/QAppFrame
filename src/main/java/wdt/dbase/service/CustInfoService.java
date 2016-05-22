package wdt.dbase.service;

import wdt.dbase.beans.CustInfo;


public interface CustInfoService
{
	public int addCustInfo(CustInfo custInfo );
	public CustInfo selectCustInfo( String accno );
}
