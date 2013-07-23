/**
 * 
 */
package com.amzedia.xstore.services.interfaces;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;

/**
 * @author Tarun Keswani
 * 
 */
public interface IGroupService {

	ResponseWrapper getGroup(int id) throws XstoreException;

	/*ResponseWrapper addGroup(Group group)  throws XstoreException;*/

	ResponseWrapper deactivateOrActivateGroup(Group group)  throws XstoreException;

	ResponseWrapper updateGroup(Group group)  throws XstoreException;

}
