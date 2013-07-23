/**
 * 
 */
package com.amzedia.xstore.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.IGroupDao;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ListResponseWrapper;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.services.interfaces.IGroupService;
import com.amzedia.xstore.util.Message;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;

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
	public ResponseWrapper getGroup(int id)  throws XstoreException {
		return this.groupDao.getGroup(id);
	}

	/*
	 * This api will add the group
	 */
	/*public ResponseWrapper addGroup(Group group)  throws XstoreException{

		return this.groupDao.addGroup(group);
	}*/

	/*
	 * This api will activate or deactivate the group
	 */
	public ResponseWrapper deactivateOrActivateGroup(Group group)  throws XstoreException{
		return this.groupDao.deactivateOrActivateGroup(group);
	}

	/*
	 * This API will update the Group info
	 */
	public ResponseWrapper updateGroup(Group group)  throws XstoreException{
		return this.groupDao.updateGroup(group);
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.services.interfaces.IGroupService#addStoreToGroup(int, com.amzedia.xstore.model.Store)
	 */
	public ResponseWrapper addStoreToGroup(int id, Store store) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			boolean status = this.groupDao.addStoreToGroup(id, store);
			if(status) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.STORE_ADDED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.STORE_NOT_ADDED);
			}
		} catch (Exception e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getCause().getMessage());
		}
		return responseWrapper;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.services.interfaces.IGroupService#getActivatedStoresByGroup(int, com.amzedia.xstore.model.Store)
	 */
	public ListResponseWrapper getActivatedStoresByGroup(int id) {
		ListResponseWrapper responseWrapper = new ListResponseWrapper();
		List<Object> objects = new ArrayList<Object>();
		try {
			List<Store> stores = this.groupDao.getActivatedStoresByGroup(id);
			
			for(Store store : stores) {
				objects.add(store);
			}
			responseWrapper.setStatus(ResponseCode.OK);
			responseWrapper.setMessage(ResponseMessage.SUCCESS);
			responseWrapper.setResult(objects);
		} catch (Exception e) {
			objects.add(e.getCause().getCause().getMessage());
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(objects);
		}
		return responseWrapper;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.services.interfaces.IGroupService#getStoresByGroup(int, com.amzedia.xstore.model.Store)
	 */
	public ListResponseWrapper getStoresByGroup(int id) {
		ListResponseWrapper responseWrapper = new ListResponseWrapper();
		List<Object> objects = new ArrayList<Object>();
		try {
			List<Store> stores = this.groupDao.getStoresByGroup(id);
			
			for(Store store : stores) {
				objects.add(store);
			}
			responseWrapper.setStatus(ResponseCode.OK);
			responseWrapper.setMessage(ResponseMessage.SUCCESS);
			responseWrapper.setResult(objects);
		} catch (Exception e) {
			objects.add(e.getCause().getCause().getMessage());
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(objects);
		}
		return responseWrapper;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.services.interfaces.IGroupService#getDeactivatedStoresByGroup(int, com.amzedia.xstore.model.Store)
	 */
	public ListResponseWrapper getDeactivatedStoresByGroup(int id) {
		ListResponseWrapper responseWrapper = new ListResponseWrapper();
		List<Object> objects = new ArrayList<Object>();
		try {
			List<Store> stores = this.groupDao.getDeactivatedStoresByGroup(id);
			
			for(Store store : stores) {
				objects.add(store);
			}
			responseWrapper.setStatus(ResponseCode.OK);
			responseWrapper.setMessage(ResponseMessage.SUCCESS);
			responseWrapper.setResult(objects);
		} catch (Exception e) {
			objects.add(e.getCause().getCause().getMessage());
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(objects);
		}
		return responseWrapper;
	}
}
