/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.dao.interfaces.IGroupDao;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.services.interfaces.IGroupService;

/**
 * @author Tarun Keswani
 *
 */
@Service
public class GroupService implements IGroupService {

	@Autowired
	private IGroupDao groupDao;

	/* 
	 * This will return Group using GroupId
	 */
	public Group getGroup(int id) {
		return this.groupDao.getGroup(id);
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.services.interfaces.IGroupService#addGroup(com.amzedia.xstore.model.Group)
	 */
	public boolean addGroup(Group group) {
		
		return this.groupDao.addGroup(group);
	}
}
