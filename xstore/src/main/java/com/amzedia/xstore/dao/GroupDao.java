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
import com.amzedia.xstore.dao.interfaces.IGroupDao;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.util.Message;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun Keswani
 * 
 */
@Component
public class GroupDao extends BaseDao implements IGroupDao {

	private static final Logger logger = Logger.getLogger(GroupDao.class);
	private String sql;

	/**
	 * This api will bring Group with group id
	 * 
	 * @param id
	 * @return Group
	 */
	public ResponseWrapper getGroup(int id) throws XstoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("GroupDao -->>  getGroup -->> id is " + id);
		}
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.GET_GROUP;
			Group returnGroup;
			final Group group = new Group();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			returnGroup = this
					.getNamedParameterJdbcTemplate()
					.query(sql,
							paramMap,
							new ResultSetExtractor<Group>() {

								public Group extractData(
										ResultSet rs)
										throws SQLException {
									if (rs.next()) {
										group.setId(rs.getInt("ID"));
										group.setName(rs.getString("NAME"));
										group.setStatus(rs
												.getBoolean("STATUS"));
									}

									return group;
								}
							});
			if (returnGroup.getId() > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(returnGroup);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.UNAVAILABLE);
				responseWrapper.setResult(Message.GROUP_NOT_FOUND
						+ id);
			}
		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("error in get store");
		} catch (Exception e) {
			logger.error("error in get store");
			throw new XstoreException(e.getCause().getMessage());
		}
		return responseWrapper;
	}

	/*
	 * This API will add the Group under the client
	 * 
	 * @param group
	 * 
	 * @return boolean
	 */
	/*public ResponseWrapper addGroup(Group group) throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.SAVE_GROUP;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("groupName", group.getName());
			values.put("clientId", group.getClient().getId());
			values.put("status", group.isStatus());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			int success = this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if (success > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.GROUP_ADDED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.GROUP_NOT_ADDED);
			}

		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("error in register group");
		} catch (Exception e) {
			logger.error("error in register group");
		}
		return responseWrapper;

	}*/

	/**
	 * This api will activate or deactivate group
	 * 
	 * @param group
	 * @return boolean
	 */
	public ResponseWrapper deactivateOrActivateGroup(Group group) throws XstoreException{
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.DEACTIVATE_OR_ACTIVATE_GROUP;

			Map<String, Object> values = new HashMap<String, Object>();
			values.put("status", group.isStatus());
			values.put("ID", group.getId());
			SqlParameterSource paramSource = new MapSqlParameterSource(
					values);
			int success = this.getNamedParameterJdbcTemplate().update(sql,
					paramSource);
			if(success > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.GROUP_DEACTIVATED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.GROUP_NOT_DEACTIVATED);
			}
		} catch(DataAccessException e) {
			logger.error("error in deactivateOrActivate group");
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
		} catch (Exception e) {
			logger.error("error in deactivateOrActivate group");
		}
		return responseWrapper;
	}

	/*
	 * this api will update the Group Info
	 */
	public ResponseWrapper updateGroup(Group group) throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.UPDATE_GROUP;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", group.getName());
			values.put("status", group.isStatus());
			values.put("ID", group.getId());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			int success = this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if(success > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.GROUP_UPDATED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.GROUP_NOT_FOUND);
			}
		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("Error in update group");
		}
		return responseWrapper;
	}

}
