/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import java.util.List;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Product;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.model.Tag;

/**
 * @author Tarun
 * 
 */
public interface IStoreDao {

	/**
	 * Get store by id of client
	 * 
	 * @param id
	 * @return Client
	 */
	ResponseWrapper getStore(int id) throws XstoreException;

	/**
	 * This api will deactivate client
	 * 
	 * @param client
	 * @return boolean TODO
	 */
	ResponseWrapper deactivateOrActivateStore(Store store)
			throws XstoreException;

	/**
	 * This api will update store info
	 * 
	 * @param store
	 * @return boolean
	 */
	ResponseWrapper updateStore(Store store) throws XstoreException;

	/**
	 * 
	 * @param id
	 * @param tag
	 * @return
	 * @throws RuntimeException
	 */
	boolean addTagToStore(int id, Tag tag) throws RuntimeException;
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws RuntimeException
	 */
	List<Tag> getTagsByStore(int id) throws RuntimeException;
	
	/**
	 * 
	 * @param id
	 * @param product
	 * @return
	 * @throws XstoreException
	 */
	boolean addProductToStore(int id, Product product)
			throws XstoreException;
}
