/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

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
	Store getStore(int id);

	/**
	 * @param store
	 * @return
	 */
	boolean addStore(Store store);

	/**
	 * This api will deactivate client
	 * 
	 * @param client
	 * @return boolean TODO
	 */
	boolean deactivateOrActivateStore(Store store);

	/**
	 * This api will update store info
	 * 
	 * @param store
	 * @return boolean
	 */
	boolean updateStore(Store store);
}
