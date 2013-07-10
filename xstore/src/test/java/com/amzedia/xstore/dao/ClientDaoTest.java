/**
 * 
 */
package com.amzedia.xstore.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.amzedia.xstore.dao.interfaces.IClientDao;
import com.amzedia.xstore.model.BasicInfo;

/**
 * @author Sushant
 *
 */

//@RunWith(SpringJUnit4ClassRunner.class)
public class ClientDaoTest {

	@Autowired
	private IClientDao clientDao;
	
	//@Test
	public void testJummy(){
		BasicInfo basicInfo = new BasicInfo();
		basicInfo.setFirstName("Jony");
		basicInfo.setMiddleName("Carl");
		basicInfo.setLastName("Depp");
		basicInfo.setAddress("NYC");
		basicInfo.setCity("Nyc");
		basicInfo.setCountry("US");
		basicInfo.setEmail("jony@gmail.com");
		basicInfo.setFax("123456");
		basicInfo.setPhoneNumber("78945613230");
		basicInfo.setPin("12345");
		basicInfo.setState("sates");
		//System.out.println("Id: "+clientDao.addBasic(basicInfo));
		
	}
}
