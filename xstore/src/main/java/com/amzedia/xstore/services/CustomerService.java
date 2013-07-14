/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.dao.interfaces.ICustomerDao;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.services.interfaces.ICustomerService;

/**
 * @author Tarun Keswani
 * 
 */

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	private ICustomerDao customerDao;

	public Customer getCustomer(int id) {

		return this.customerDao.getCustomer(id);
	}

}
