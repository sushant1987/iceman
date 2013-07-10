/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Store;

/**
 * @author Tarun
 *
 */
public interface IStoreService {
	
	boolean addStore(Store store);
	boolean deactivateOrActivateStore(Store store);
	
	/**
	 * Get store by id of store
	 * 
	 * @param id
	 * @return {@link Store}
	 */
	Store getStore(int id);

}
