/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.model.Customer;


/**
 * @author Tarun Keswani
 *
 */
public interface ICustomerDao {
	
	/**
	 * Get store by id of customer
	 * 
	 * @param id
	 * @return Customer
	 */
	Customer getCustomer(int id);

}
