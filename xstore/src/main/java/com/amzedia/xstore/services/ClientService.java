/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.IClientDao;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.services.interfaces.IClientService;

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
	public ResponseWrapper registerClient(Client client)
			throws XstoreException {
		return this.clientDao.registerClient(client);
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

}
