/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.ListResponseWrapper;
import com.amzedia.xstore.model.Product;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.model.Tag;

/**
 * @author Tarun
 * 
 */
public interface IStoreService {

	/*ResponseWrapper addStore(Store store) throws XstoreException;*/

	ResponseWrapper deactivateOrActivateStore(Store store)
			throws XstoreException;

	ResponseWrapper getStore(int id) throws XstoreException;

	ResponseWrapper updateStore(Store store) throws XstoreException;
	
	ResponseWrapper addProductToStore(int id, Product product);
	
	ResponseWrapper addTagToStore(int id, Tag tag);
	
	ListResponseWrapper getTagsByStore(int id);
	
	ListResponseWrapper getActivatedTagsByStore(int id);
	
	ListResponseWrapper getDeactivatedTagsByStore(int id);

}
