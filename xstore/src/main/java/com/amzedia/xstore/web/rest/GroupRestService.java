/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.services.interfaces.IGroupService;

/**
 * @author Tarun Keswani
 * 
 */

@Controller
@RequestMapping(value = "/group")
public class GroupRestService {

	@Autowired
	private IGroupService groupService;

	/**
	 * This api will bring the group information by passing id of group
	 * 
	 * @param String
	 * @return Group
	 */

	@RequestMapping(value = "/findgroup/{id}")
	@ResponseBody
	public Group getGroupById(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		return this.groupService.getGroup(groupId);
	}

	/**
	 * This api will add store
	 * 
	 * @param Store
	 * @return boolean
	 */
	@RequestMapping(value = "/addgroup", method = RequestMethod.POST)
	@ResponseBody
	public boolean addGroup(@RequestBody Group group) {
		return this.groupService.addGroup(group);
	}

	@RequestMapping(value = "/activateordeactivate", method = RequestMethod.POST)
	@ResponseBody
	public boolean deactivateOrActivateGroup(@RequestBody Group group) {
		return this.groupService.deactivateOrActivateGroup(group);
	}

	/**
	 * This api will update group info
	 * 
	 * @param Group
	 * @return boolean
	 */
	@RequestMapping(value = "/updategroup", method = RequestMethod.POST)
	@ResponseBody
	public boolean updaterGroup(@RequestBody Group group) {
		return this.groupService.updateGroup(group);
	}
}
