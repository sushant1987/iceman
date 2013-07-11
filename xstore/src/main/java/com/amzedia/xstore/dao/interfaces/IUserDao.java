/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.model.User;


/**
 * @author Tarun Keswani
 *
 */
public interface IUserDao {
	
	/**
	 * Get store by id of user
	 * 
	 * @param id
	 * @return User
	 */
	User getUser(int id);

}
