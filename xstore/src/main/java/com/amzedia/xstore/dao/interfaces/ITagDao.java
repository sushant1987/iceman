/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Tag;

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

	/**
	 * 
	 * @param id
	 * @param tag
	 * @return boolean
	 * @throws RuntimeException
	 */
	boolean addTagToParentTag(int id, Tag tag) throws RuntimeException;

}
