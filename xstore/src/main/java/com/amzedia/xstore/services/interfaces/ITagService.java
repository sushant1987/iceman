/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Tag;

/**
 * @author Tarun Keswani
 * 
 */
public interface ITagService {

	ResponseWrapper getTag(int id) throws XstoreException;

	ResponseWrapper addTagToParentTag(int id, Tag tag);

}
