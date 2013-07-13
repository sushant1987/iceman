/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.model.Group;

/**
 * @author Tarun Keswani
 *
 */
public interface IGroupService {
	
	Group getGroup(int id);
	boolean addGroup(Group group);
	
}
