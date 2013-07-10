/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.dao.interfaces.IClientDao;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.services.interfaces.IClientService;

/**
 * @author Sushant
 *
 */
@Service
public class ClientService implements IClientService {

	@Autowired
	private IClientDao clientDao;
	/**
	 * 
	 */
	public boolean registerClient(Client client) {
		return this.clientDao.registerClient(client);
	}
	
	public boolean updateClient(Client client) {
		return this.clientDao.updateClient(client);
	}
	public boolean dummy() {
		return this.clientDao.dummy();
	}
	
	public Client getClient(int id) {
		return this.clientDao.getClient(id);
	}
	
	public Client loginClient(Client client) {
		return this.clientDao.loginClient(client);
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.services.interfaces.IClientService#deactivateOrActivateClient(com.amzedia.xstore.model.Client)
	 */
	public boolean deactivateOrActivateClient(Client client) {
		// TODO Auto-generated method stub
		return this.clientDao.deactivateOrActivateClient(client);
	}
	

}
