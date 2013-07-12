/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.dao.interfaces.IStoreDao;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.services.interfaces.IStoreService;

/**
 * @author Sushant
 * 
 */
@Service
public class StoreService implements IStoreService {

	@Autowired
	private IStoreDao storeDao;

	/**
	 * 
	 */
	public boolean addStore(Store store) {
		return this.storeDao.addStore(store);
	}
	
	public boolean deactivateOrActivateStore(Store store) {
		// TODO Auto-generated method stub
		return this.storeDao.deactivateOrActivateStore(store);
	}
	
	/**
	 * Get client by id of client
	 * 
	 * @param id
	 * @return Store
	 */
	public Store getStore(int id) {
		return this.storeDao.getStore(id);
	}

}