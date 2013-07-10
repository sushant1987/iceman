/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.model.Store;

/**
 * @author Tarun
 *
 */
public interface IStoreService {
	
	boolean addStore(Store store);
	boolean deactivateOrActivateStore(Store store);

}
