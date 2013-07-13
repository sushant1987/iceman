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

}
