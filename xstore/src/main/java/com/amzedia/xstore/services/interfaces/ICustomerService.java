/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.model.ResponseWrapper;

/**
 * @author Tarun Keswani
 * 
 */
public interface ICustomerService {

	/**
	 * 
	 * @param id
	 * @return {@link ResponseWrapper}
	 * @throws XstoreException
	 */
	ResponseWrapper getCustomer(int id) throws XstoreException;
	
	/**
	 * 
	 * @param customer
	 * @return
	 * @throws XstoreException
	 */
	ResponseWrapper registerCustomer(Customer customer) throws XstoreException;

}
