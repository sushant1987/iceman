/**
 * 
 */
package com.amzedia.xstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.ITagDao;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.ResponseWrapper;
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
								tag.setLevel(rs.getInt("LEVEL"));
								tag.setParentId(rs
										.getInt("PARENT_ID"));
								tag.setStatus(rs.getBoolean("STATUS"));
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
	 * getting activated tags under the parent tag
	 */
	public List<Tag> getActivatedTagByParentTag(int id)
			throws RuntimeException {
		List<Tag> tags = new ArrayList<Tag>();
		try {
			sql = SqlScript.GET_ACTIVATED_TAGS_BY_PARENT_TAG;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("ID", id);
			List<Map<String, Object>> list = this
					.getNamedParameterJdbcTemplate()
					.queryForList(sql, values);
			for (Map<String, Object> map : list) {
				Tag tag = new Tag();
				tag.setId((Integer) map.get("ID"));
				tag.setName((String) map.get("NAME"));
				tag.setLevel((Integer) map.get("LEVEL"));
				tag.setParentId((Integer) map.get("PARENT_ID"));
				tag.setStatus((Boolean) map.get("STATUS"));
				tags.add(tag);
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return tags;
	}

	/*
	 * getting deactivated tags under the parent tag
	 */
	public List<Tag> getDeActivatedTagByParentTag(int id)
			throws RuntimeException {
		List<Tag> tags = new ArrayList<Tag>();
		try {
			sql = SqlScript.GET_DEACTIVATED_TAGS_BY_PARENT_TAG;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("ID", id);
			List<Map<String, Object>> list = this
					.getNamedParameterJdbcTemplate()
					.queryForList(sql, values);
			for (Map<String, Object> map : list) {
				Tag tag = new Tag();
				tag.setId((Integer) map.get("ID"));
				tag.setName((String) map.get("NAME"));
				tag.setLevel((Integer) map.get("LEVEL"));
				tag.setParentId((Integer) map.get("PARENT_ID"));
				tag.setStatus((Boolean) map.get("STATUS"));
				tags.add(tag);
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return tags;
	}

	/*
	 * 
	 * Udating the tag
	 */
	public ResponseWrapper updateTag(Tag tag) throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.UPDATE_TAG;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", tag.getName());
			values.put("ID", tag.getId());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			int success = this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if (success > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.TAG_UPDATED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.TAG_NOT_FOUND);
			}

		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("Error in update tag");
		}
		return responseWrapper;
	}

	/*
	 * 
	 */
	public ResponseWrapper deactivateOrActivateTag(Tag tag)
			throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.DEACTIVATE_OR_ACTIVATE_TAG;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("status", tag.isStatus());
			values.put("ID", tag.getId());
			SqlParameterSource paramSource = new MapSqlParameterSource(
					values);
			int success = this.getNamedParameterJdbcTemplate()
					.update(sql, paramSource);
			if (success > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.STORE_DEACTIVATED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.STORE_NOT_DEACTIVATED);
			}

		} catch (DataAccessException e) {
			logger.error("error in deactivateOrActivate tag");
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
		} catch (Exception e) {
			logger.error("error in deactivateOrActivate");
			throw new XstoreException();
		}
		return responseWrapper;
	}

	/*
	 * Adding the child tag TODO
	 */
	/*
	 * public boolean addTagToParentTag(int id, Tag tag) throws
	 * RuntimeException {
	 * 
	 * try { sql = SqlScript.ADD_CHILD_TAG; Map<String, Object> values = new
	 * HashMap<String, Object>(); final Store store = new Store();
	 * values.put("tagName", tag.getName()); values.put("storeId",
	 * store.getId()); values.put("level", tag.getLevel());
	 * values.put("parentId", id); SqlParameterSource params = new
	 * MapSqlParameterSource( values); int success =
	 * this.getNamedParameterJdbcTemplate() .update(sql, params); if
	 * (success > 0) { return true; } else { return false; }
	 * 
	 * } catch (DataAccessException e) {
	 * logger.error("error in adding tag to parent tag id : " + id); throw
	 * new RuntimeException(e); } catch (Exception e) {
	 * logger.error("error in adding tag to parent tag id : " + id); throw
	 * new RuntimeException(e); } }
	 */
}
