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
	 * @param store
	 * @return
	 */
	boolean addStore(Store store);
	
	/**
	 * This api will deactivate client
	 * 
	 * @param client
	 * @return boolean
	 * TODO
	 */
	boolean deactivateOrActivateStore(Store store);
}
