/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.ITagDao;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.model.Tag;
import com.amzedia.xstore.services.interfaces.ITagService;
import com.amzedia.xstore.util.Message;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;

/**
 * @author Tarun Keswani
 * 
 * 
 */
@Service
public class TagService implements ITagService {

	@Autowired
	private ITagDao tagDao;

	/*
	 * This will return Tag using TagId
	 */
	public ResponseWrapper getTag(int id) throws XstoreException {
		return this.tagDao.getTag(id);
	}

	/*
	 * adding tag to its parent tag
	 */
	public ResponseWrapper addTagToParentTag(int id, Tag tag) {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			boolean status = this.tagDao.addTagToParentTag(id,
					tag);
			if (status) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.TAG_NOT_ADDED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.TAG_NOT_ADDED);
			}
		} catch (Exception e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getCause()
					.getMessage());
		}
		return responseWrapper;
	}

}
