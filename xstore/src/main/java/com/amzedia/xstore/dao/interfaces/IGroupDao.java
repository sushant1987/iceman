/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;

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
	 * This api will add new group
	 * 
	 * @param group
	 * @return boolean
	 */
	/*ResponseWrapper addGroup(Group group) throws XstoreException;*/

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

}
