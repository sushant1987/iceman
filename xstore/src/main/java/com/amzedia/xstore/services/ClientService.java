/**
 * 
 */
package com.amzedia.xstore.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.IClientDao;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ListResponseWrapper;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.services.interfaces.IClientService;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;

/**
 * @author Sushant
 * 
 */
@Service
public class ClientService implements IClientService {

	@Autowired
	private IClientDao clientDao;

	public ResponseWrapper getClient(int id) throws XstoreException {
		return this.clientDao.getClient(id);
	}

	/**
	 * @throws XstoreException
	 * 
	 */
	public ResponseWrapper registerClient(Client client) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			responseWrapper.setStatus(ResponseCode.OK);
			responseWrapper.setMessage(ResponseMessage.SUCCESS);
			responseWrapper.setResult(this.clientDao.registerClient(client));
		} catch (Exception e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getCause()
					.getMessage());
		}
		return responseWrapper;
	}

	public ResponseWrapper updateClient(Client client)
			throws XstoreException {
		return this.clientDao.updateClient(client);
	}

	public ResponseWrapper dummy() throws XstoreException {
		return this.clientDao.dummy();
	}

	public ResponseWrapper loginClient(Client client)
			throws XstoreException {
		return this.clientDao.loginClient(client);
	}

	public ResponseWrapper deactivateOrActivateClient(Client client)
			throws XstoreException {
		return this.clientDao.deactivateOrActivateClient(client);
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.services.interfaces.IClientService#getAllGroupByClient(int)
	 */
	public ListResponseWrapper getAllGroupByClient(int id) {
		ListResponseWrapper responseWrapper = new ListResponseWrapper();
		List<Object> objects = new ArrayList<Object>();
		try {
			List<Group> groups = this.clientDao.getAllGroupByClient(id);
			
			for(Group group : groups) {
				objects.add(group);
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
		// TODO Auto-generated method stub
		return responseWrapper;
	}

}
