/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.IStoreDao;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.services.interfaces.IStoreService;

/**
 * @author Tarun Keswani
 * 
 */
@Service
public class StoreService implements IStoreService {

	@Autowired
	private IStoreDao storeDao;

	/**
	 * @throws XstoreException
	 * 
	 */
	/*public ResponseWrapper addStore(Store store) throws XstoreException {
		return this.storeDao.addStore(store);
	}*/

	public ResponseWrapper deactivateOrActivateStore(Store store)
			throws XstoreException {
		return this.storeDao.deactivateOrActivateStore(store);
	}

	/**
	 * Get client by id of client
	 * 
	 * @param id
	 * @return Store
	 * @throws XstoreException
	 */
	public ResponseWrapper getStore(int id) throws XstoreException {
		return this.storeDao.getStore(id);
	}

	/*
	 * This API will update the Store info
	 */
	public ResponseWrapper updateStore(Store store) throws XstoreException {

		return this.storeDao.updateStore(store);
	}

}
