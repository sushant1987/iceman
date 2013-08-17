/**
 * 
 */
package com.amzedia.xstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.ITagDao;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.model.Tag;
import com.amzedia.xstore.util.Message;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun Keswani
 * 
 */
@Component
public class TagDao extends BaseDao implements ITagDao {

	private static final Logger logger = Logger.getLogger(TagDao.class);
	private String sql;

	/*
	 * 
	 * This will return Tag using TagId
	 */
	public ResponseWrapper getTag(int id) throws XstoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("TagDao -->>  getTag -->> id is " + id);
		}
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.GET_TAG;
			Tag returnTag;
			final Tag tag = new Tag();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			final Store store = new Store();
			paramMap.put("ID", id);
			returnTag = this.getNamedParameterJdbcTemplate().query(
					sql, paramMap,
					new ResultSetExtractor<Tag>() {

						public Tag extractData(
								ResultSet rs)
								throws SQLException {
							if (rs.next()) {
								tag.setId(rs.getInt("ID"));
								tag.setName(rs.getString("NAME"));
								store.setId(rs.getInt("STORE_ID"));
								tag.setLevel(rs.getInt("LEVEL"));
								tag.setParent_id(rs
										.getInt("PARENT_ID"));
							}

							return tag;
						}
					});
			if (returnTag.getId() > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(returnTag);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.UNAVAILABLE);
				responseWrapper.setResult(Message.TAG_NOT_FOUND
						+ id);
			}

		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("error in get tag");
		} catch (Exception e) {
			logger.error("error in get tag");
			throw new XstoreException(e.getCause().getMessage());
		}
		return responseWrapper;
	}

	/*
	 * Adding the child tag
	 */
	public boolean addTagToParentTag(int id, Tag tag)
			throws RuntimeException {

		try {
			sql = SqlScript.ADD_CHILD_TAG;
			Map<String, Object> values = new HashMap<String, Object>();
			final Store store = new Store();
			values.put("tagName", tag.getName());
			values.put("storeId", store.getId());
			values.put("level", tag.getLevel());
			values.put("parentId", tag.getParent_id());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			int success = this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if (success > 0) {
				return true;
			} else {
				return false;
			}

		} catch (DataAccessException e) {
			logger.error("error in adding tag to parent tag id : "
					+ id);
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("error in adding tag to parent tag id : "
					+ id);
			throw new RuntimeException(e);
		}
	}
}
