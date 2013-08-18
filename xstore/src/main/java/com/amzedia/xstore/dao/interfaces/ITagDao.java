/**
 * 
 */
package com.amzedia.xstore.dao.interfaces;

import java.util.List;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Group;
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
	 *                 TODO
	 */
	// boolean addTagToParentTag(int id, Tag tag) throws RuntimeException;

	List<Tag> getActivatedTagByParentTag(int id) throws RuntimeException;

	List<Tag> getDeActivatedTagByParentTag(int id) throws RuntimeException;

	ResponseWrapper updateTag(Tag tag) throws XstoreException;
}
