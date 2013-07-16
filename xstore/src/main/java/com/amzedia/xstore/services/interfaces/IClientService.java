/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.ResponseWrapper;

/**
 * @author Sushant
 * 
 */
public interface IClientService {

	ResponseWrapper getClient(int id) throws XstoreException;

	ResponseWrapper registerClient(Client client) throws XstoreException;

	boolean updateClient(Client client) throws XstoreException;

	ResponseWrapper dummy() throws XstoreException;

	Client loginClient(Client client) throws XstoreException;

	boolean deactivateOrActivateClient(Client client)
			throws XstoreException;

}
