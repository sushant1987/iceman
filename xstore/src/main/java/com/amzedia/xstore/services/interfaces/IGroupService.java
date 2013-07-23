/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ListResponseWrapper;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;

/**
 * @author Tarun Keswani
 * 
 */
public interface IGroupService {

	ResponseWrapper getGroup(int id) throws XstoreException;

	ResponseWrapper deactivateOrActivateGroup(Group group)  throws XstoreException;

	ResponseWrapper updateGroup(Group group)  throws XstoreException;
	
	ResponseWrapper addStoreToGroup(int id, Store store);
	
	ListResponseWrapper getStoresByGroup(int id);
	
	ListResponseWrapper getActivatedStoresByGroup(int id);
	
	ListResponseWrapper getDeactivatedStoresByGroup(int id);

}
