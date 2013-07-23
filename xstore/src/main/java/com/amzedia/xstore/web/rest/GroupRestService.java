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

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.services.interfaces.IGroupService;

/**
 * @author Tarun Keswani
 * 
 */

@Controller
@RequestMapping(value = "/groups")
public class GroupRestService {

	@Autowired
	private IGroupService groupService;

	/**
	 * This api will bring the group information by passing id of group
	 * 
	 * @param String
	 * @return Group
	 */

	@RequestMapping(value = "/find/{id}")
	@ResponseBody
	public ResponseWrapper getGroupById(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		try {
			return this.groupService.getGroup(groupId);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This api will add store
	 * 
	 * @param Store
	 * @return boolean
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper addGroup(@RequestBody Group group) {
		try {
			return this.groupService.addGroup(group);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/activateordeactivate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper deactivateOrActivateGroup(@RequestBody Group group) {
		try {
			return this.groupService.deactivateOrActivateGroup(group);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This api will update group info
	 * 
	 * @param Group
	 * @return boolean
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper updaterGroup(@RequestBody Group group) {
		try {
			return this.groupService.updateGroup(group);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	@ResponseBody
	public Group getDummmyGroup() {
		Group group = new Group();
		Client client = new Client();
		group.setName("MyGroup");
		group.setStatus(true);
		client.setId(1);
		group.setClient(client);
		return group;
		
	}
}
