/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.services.interfaces.ITagService;

/**
 * @author Tarun Keswani
 * 
 */

@Controller
@RequestMapping(value = "/tag")
public class TagRestService {

	@Autowired
	private ITagService tagService;

	@RequestMapping(value = "/{id}")
	@ResponseBody
	public ResponseWrapper getTagById(@PathVariable String id) {
		int tagId = Integer.parseInt(id);
		try {
			return this.tagService.getTag(tagId);
		} catch (XstoreException e) {
			e.printStackTrace();
			return null;
		}
	}

}
