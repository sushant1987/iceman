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
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ListResponseWrapper;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
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

	@RequestMapping(value = "/{id}")
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

	@RequestMapping(value = "/{id}/stores/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper addStoreToGroup(@PathVariable String id, @RequestBody Store store) {
		int groupId = Integer.parseInt(id);
		return this.groupService.addStoreToGroup(groupId, store);
		
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	@ResponseBody
	public Group getDummmyGroup() {
		Group group = new Group();
		group.setName("MyGroup");
		group.setStatus(true);
		return group;
	}
	
	@RequestMapping(value = "/{id}/stores", method = RequestMethod.GET)
	@ResponseBody
	public ListResponseWrapper getStoresByGroup(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		return this.groupService.getStoresByGroup(groupId);
	}
	
	@RequestMapping(value = "/{id}/stores/deactivated", method = RequestMethod.GET)
	@ResponseBody
	public ListResponseWrapper getDeactivatedStoresByGroup(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		return this.groupService.getDeactivatedStoresByGroup(groupId);
	}
	
	@RequestMapping(value = "/{id}/stores/activated", method = RequestMethod.GET)
	@ResponseBody
	public ListResponseWrapper getActivatedStoresByGroup(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		return this.groupService.getActivatedStoresByGroup(groupId);
	}
	
	@RequestMapping(value = "/{id}/customers/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper registerCustomerToGroup(@PathVariable String id, @RequestBody Customer customer) {
		int groupId = Integer.parseInt(id);
		return this.groupService.registerCustomerToGroup(groupId, customer);
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/{id}/customers", method = RequestMethod.GET)
	@ResponseBody
	public ListResponseWrapper getCustomersByGroup(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		return this.groupService.getCustomersByGroup(groupId);
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/{id}/customers/activated", method = RequestMethod.GET)
	@ResponseBody
	public ListResponseWrapper getActivatedCustomersByGroup(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		return this.groupService.getActivatedCustomersByGroup(groupId);
	}

	/**
	 * 
	 */
	@RequestMapping(value = "/{id}/customers/deactivated", method = RequestMethod.GET)
	@ResponseBody
	public ListResponseWrapper getDeavtivatedCustomersByGroup(@PathVariable String id) {
		int groupId = Integer.parseInt(id);
		return this.groupService.getDeavtivatedCustomersByGroup(groupId);
	}
}
