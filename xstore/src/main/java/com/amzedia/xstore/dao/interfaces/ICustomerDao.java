/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.model.ResponseWrapper;

/**
 * @author Tarun Keswani
 * 
 */
public interface ICustomerDao {

	/**
	 * 
	 * @param id
	 * @return {@link ResponseWrapper}
	 * @throws XstoreException
	 */
	ResponseWrapper getCustomer(int id) throws XstoreException;

	Customer loginCustomer(Customer customer) throws RuntimeException;

}
