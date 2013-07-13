/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.model.Group;

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
	Group getGroup(int id);

	/**
	 * This api will add new group
	 * 
	 * @param group
	 * @return boolean
	 */
	boolean addGroup(Group group);

	/**
	 * This api will activate or deactivate group
	 * 
	 * @param group
	 * @return boolean
	 */
	boolean deactivateOrActivateGroup(Group group);

	/**
	 * This api will update group profile
	 * 
	 * @param group
	 * @return boolean
	 */
	boolean updateGroup(Group group);

}
