/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import java.util.List;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;

/**
 * @author Tarun Keswani
 * 
 */
public interface IGroupDao {

	/**
	 * Get client by id of client
	 * 
	 * @param id
	 * @return Client
	 */
	ResponseWrapper getGroup(int id) throws XstoreException;

	/**
	 * This api will activate or deactivate group
	 * 
	 * @param group
	 * @return boolean
	 */
	ResponseWrapper deactivateOrActivateGroup(Group group) throws XstoreException;

	/**
	 * This api will update group profile
	 * 
	 * @param group
	 * @return boolean
	 */
	ResponseWrapper updateGroup(Group group) throws XstoreException;
	
	/**
	 * 
	 * @param id
	 * @param store
	 * @return boolean
	 * @throws RuntimeException
	 */
	boolean addStoreToGroup(int id, Store store) throws RuntimeException;
	
	List<Store> getStoresByGroup(int id) throws RuntimeException;
	
	List<Store> getActivatedStoresByGroup(int id) throws RuntimeException;
	
	List<Store> getDeactivatedStoresByGroup(int id) throws RuntimeException;

}
