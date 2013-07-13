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

	/*
	 * This api will add the group
	 */
	public boolean addGroup(Group group) {

		return this.groupDao.addGroup(group);
	}

	/*
	 * This api will activate or deactivate the group
	 */
	public boolean deactivateOrActivateGroup(Group group) {
		return this.groupDao.deactivateOrActivateGroup(group);
	}

	/*
	 * This API will update the Group info
	 */
	public boolean updateGroup(Group group) {
		return this.groupDao.updateGroup(group);
	}
}
