/**
 * 
 */
package com.amzedia.xstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.ITagDao;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.services.interfaces.ITagService;

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

}
