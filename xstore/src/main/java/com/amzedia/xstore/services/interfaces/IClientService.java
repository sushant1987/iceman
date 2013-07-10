/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.model.Client;

/**
 * @author Sushant
 *
 */
public interface IClientService {

	boolean registerClient(Client client);
	
	boolean updateClient(Client client);
	
	boolean dummy();
	
	Client getClient(int id);
	
	Client loginClient(Client client);
	
	boolean deactivateOrActivateClient(Client client);
	
	
}
