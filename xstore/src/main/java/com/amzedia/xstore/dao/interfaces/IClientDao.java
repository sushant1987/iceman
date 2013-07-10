/**
 * This Class will provide all client services
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.ChangePassword;
import com.amzedia.xstore.model.Client;

/**
 * This Class contains all the client operations methods
 * 
 * @author Sushant
 * 
 */
public interface IClientDao {

	/**
	 * Get client by id of client
	 * 
	 * @param id
	 * @return Client
	 */
	Client getClient(int id);

	/**
	 * This api will add new client
	 * 
	 * @param client
	 * @return boolean
	 */
	boolean registerClient(Client client);

	/**
	 * This api will update client profile
	 * 
	 * @param client
	 * @return boolean
	 */
	boolean updateClient(Client client);

	/**
	 * This will return client object on basis of correct user name and
	 * password
	 * 
	 * @param client
	 * @return Client
	 */
	Client loginClient(Client client);

	/**
	 * This api will change client password
	 * 
	 * @param changePassword
	 * @return boolean
	 */
	boolean changePassword(ChangePassword changePassword);

	/**
	 * This will reset the client password
	 * 
	 * @param client
	 * @return boolean
	 */
	boolean forgetPassword(Client client);

	/**
	 * This api will deactivate client
	 * 
	 * @param client
	 * @return boolean
	 */
	boolean deactivateOrActivateClient(Client client);

	/**
	 * This api will add dummy data to db
	 * 
	 * @return boolean
	 */
	boolean dummy();
}
