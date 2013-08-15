/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.ResponseWrapper;

/**
 * @author Tarun Keswani
 * 
 */
public interface ITagService {

	ResponseWrapper getTag(int id) throws XstoreException;

}
