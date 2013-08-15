/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.ResponseWrapper;

/**
 * @author Tarun Keswani
 * 
 */
public interface ITagDao {

	/**
	 * Get Tag by id
	 * 
	 * @param id
	 * @return Tag
	 */
	ResponseWrapper getTag(int id) throws XstoreException;

}
