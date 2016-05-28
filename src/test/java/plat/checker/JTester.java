/**
 * 
 */
/**
 * @author zhangcq
 *
 */
package plat.checker;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wdt.dbase.beans.CustInfoExample;
import wdt.dbase.service.CustInfoService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class JTester
{
	private JAdder adder;
	
	@Autowired
	private CustInfoService custInfoService;
	
	@Before
	public void setUp()
	{
		adder = new JAdder();
	}
	
	@Test
	public void testAdd()
	{
		int cal = adder.add(1, 2);
		Assert.assertEquals(4, cal);
	}
	
	@Test
	public void userLoginTest()
	{
		CustInfoExample express = new CustInfoExample();
		CustInfoExample.Criteria criteria = express.createCriteria();
		criteria.andMobileNoEqualTo("18621596661");
		
		List list = custInfoService.selectByExample(express);
		Assert.assertNull(list);
	}
}