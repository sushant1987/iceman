/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Product;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;

/**
 * @author Tarun
 * 
 */
public interface IStoreDao {

	/**
	 * Get store by id of client
	 * 
	 * @param id
	 * @return Client
	 */
	ResponseWrapper getStore(int id) throws XstoreException;

	/**
	 * @param store
	 * @return
	 */
	/* ResponseWrapper addStore(Store store) throws XstoreException; */

	/**
	 * This api will deactivate client
	 * 
	 * @param client
	 * @return boolean TODO
	 */
	ResponseWrapper deactivateOrActivateStore(Store store)
			throws XstoreException;

	/**
	 * This api will update store info
	 * 
	 * @param store
	 * @return boolean
	 */
	ResponseWrapper updateStore(Store store) throws XstoreException;

	/**
	 * 
	 * @param id
	 * @param product
	 * @return
	 * @throws XstoreException
	 */
	boolean addProductToStore(int id, Product product)
			throws XstoreException;
}
