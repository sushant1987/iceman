/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.dao.interfaces.IUserDao;
import com.amzedia.xstore.model.User;
import com.amzedia.xstore.services.interfaces.IUserService;

/**
 * @author Tarun Keswani
 *
 */

@Service
public class UserService implements IUserService {
	
	@Autowired
	private IUserDao userDao;
	
	public User getUser(int id) {
		
		return this.userDao.getUser(id);
	}

}
