/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.model.User;
import com.amzedia.xstore.services.interfaces.IUserService;

/**
 * @author Tarun Keswani
 *
 */

@Controller
@RequestMapping(value = "/user")
public class UserRestService {
	
	@Autowired
	private IUserService userService;
	
	/**
	 * This api will bring the user
	 * information by passing id of user
	 * 
	 * @param String
	 * @return User
	 */
	@RequestMapping(value="/finduser/{id}")
	@ResponseBody public User getUserById(@PathVariable String id) {
		int userId = Integer.parseInt(id);
		return this.userService.getUser(userId);
	}

}
