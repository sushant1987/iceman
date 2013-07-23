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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.IClientDao;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.ChangePassword;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.util.Message;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;
import com.amzedia.xstore.util.SqlScript;

/**
 * This Class contains all the client operations methods
 * 
 * @author Sushant
 * 
 */
@Component
public class ClientDao extends BaseDao implements IClientDao {

	private static final Logger logger = Logger.getLogger(ClientDao.class);
	private String sql;

	/**
	 * This api will bring Client with client id
	 * 
	 * @param id
	 * @return Client
	 */
	public ResponseWrapper getClient(int id) throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		Client returnClient;
		if (logger.isDebugEnabled()) {
			logger.debug("ClientDao -->>  getClient -->> id is "
					+ id);
		}
		try {
			sql = SqlScript.GET_CLIENT;
			final Client client = new Client();
			final BasicInfo basicInfo = new BasicInfo();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			returnClient = this
					.getNamedParameterJdbcTemplate()
					.query(sql,
							paramMap,
							new ResultSetExtractor<Client>() {

								public Client extractData(
										ResultSet rs)
										throws SQLException {
									if (rs.next()) {
										client.setId(rs.getInt("ID"));
										client.setUserName(rs
												.getString("USER_NAME"));
										client.setStatus(rs
												.getBoolean("STATUS"));
										client.setPlanType(rs
												.getString("PLAN_TYPE"));
										basicInfo.setId(rs
												.getInt("BID"));
										basicInfo.setAddress(rs
												.getString("ADDRESS"));
										basicInfo.setCity(rs
												.getString("CITY"));
										basicInfo.setCountry(rs
												.getString("COUNTRY"));
										basicInfo.setEmail(rs
												.getString("EMAIL"));
										basicInfo.setFax(rs
												.getString("FAX"));
										basicInfo.setFirstName(rs
												.getString("FIRST_NAME"));
										basicInfo.setLastName(rs
												.getString("LAST_NAME"));
										basicInfo.setMiddleName(rs
												.getString("MIDDLE_NAME"));
										basicInfo.setPhoneNumber(rs
												.getString("PHONE_NUMBER"));
										basicInfo.setPin(rs
												.getString("PIN_CODE"));
										basicInfo.setState(rs
												.getString("STATE"));
										client.setBasicInfo(basicInfo);
									}

									return client;
								}
							});
			if (returnClient.getId() != 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(returnClient);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.UNAVAILABLE);
				responseWrapper.setResult(Message.GET_CLIENT
						+ id);
			}

		} catch (DataAccessException e) {
			logger.error("Error in client dao");
		} catch (Exception e) {
			logger.error("Error in client dao");
			throw new XstoreException(e);
		}
		return responseWrapper;
	}

	/**
	 * This api will add new client
	 * 
	 * @param client
	 * @return boolean
	 */
	@Transactional
	public boolean registerClient(Client client)
			throws RuntimeException {
		try {
			int basicInfoId = addBasic(client.getBasicInfo());
			sql = SqlScript.SAVE_CLIENT;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("basicDetailId", basicInfoId);
			values.put("userName", client.getUserName());
			values.put("password", client.getPassword());
			values.put("status", client.isStatus());
			values.put("planType", client.getPlanType());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			int rowUpdatedCount = this
					.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if (rowUpdatedCount > 0) {
				return true;
			} else {
				return false;
			}
		} catch (DataAccessException e) {
			logger.error("exception in register client"
					+ e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("Error in client dao");
			throw new RuntimeException(e);
		}
	}

	/**
	 * This api will update client profile
	 * 
	 * @param client
	 * @return boolean
	 */
	public ResponseWrapper updateClient(Client client)
			throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.UPDATE_CLIENT;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("userName", client.getUserName());
			values.put("status", client.isStatus());
			values.put("ID", client.getId());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			int rowUpdatedCount = this
					.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if (rowUpdatedCount > 0) {
				boolean isUpdated = updateBasic(client
						.getBasicInfo());
				if (isUpdated) {
					responseWrapper.setStatus(ResponseCode.OK);
					responseWrapper.setMessage(ResponseMessage.SUCCESS);
					responseWrapper.setResult(Message.UPDATED_CLIENT);
				} else {
					logger.error("Error in update client");
					responseWrapper.setStatus(ResponseCode.FAIL);
					responseWrapper.setMessage(ResponseMessage.FAIL);
					responseWrapper.setResult(Message.CLIENT_UPDATION_FAIL
							+ client.getId());
				}
			} else {
				logger.error("Error in update client");
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.CLIENT_UPDATION_FAIL
						+ client.getId());
			}
		} catch (DataAccessException e) {
			logger.error("Error in update client");
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(e.getCause().getMessage());
			responseWrapper.setResult(Message.CLIENT_UPDATION_FAIL
					+ client.getId());
		} catch (Exception e) {
			logger.error("Error in update client");
			throw new XstoreException();
		}
		return responseWrapper;
	}

	/**
	 * This will return client object on basis of correct user name and
	 * password
	 * 
	 * @param client
	 * @return Client
	 */
	public ResponseWrapper loginClient(Client client)
			throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("ClientDao -->> loginClient -->> parameters are"
						+ "usename: "
						+ client.getUserName()
						+ "and passwords: "
						+ client.getPassword());
			}
			sql = SqlScript.LOGIN_CLIENT;
			final Client returnClient = new Client();
			Client reClient;
			final BasicInfo basicInfo = new BasicInfo();
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("userName", client.getUserName());
			values.put("password", client.getPassword());
			reClient = this.getNamedParameterJdbcTemplate().query(
					sql, values,
					new ResultSetExtractor<Client>() {

						public Client extractData(
								ResultSet rs)
								throws SQLException {
							if (rs.next()) {
								returnClient.setId(rs
										.getInt("ID"));
								returnClient.setUserName(rs
										.getString("USER_NAME"));
								returnClient.setStatus(rs
										.getBoolean("STATUS"));
								returnClient.setPlanType(rs
										.getString("PLAN_TYPE"));
								basicInfo.setId(rs
										.getInt("BID"));
								basicInfo.setAddress(rs
										.getString("ADDRESS"));
								basicInfo.setCity(rs
										.getString("CITY"));
								basicInfo.setCountry(rs
										.getString("COUNTRY"));
								basicInfo.setEmail(rs
										.getString("EMAIL"));
								basicInfo.setFax(rs
										.getString("FAX"));
								basicInfo.setFirstName(rs
										.getString("FIRST_NAME"));
								basicInfo.setLastName(rs
										.getString("LAST_NAME"));
								basicInfo.setMiddleName(rs
										.getString("MIDDLE_NAME"));
								basicInfo.setPhoneNumber(rs
										.getString("PHONE_NUMBER"));
								basicInfo.setPin(rs
										.getString("PIN_CODE"));
								basicInfo.setState(rs
										.getString("STATE"));
								returnClient.setBasicInfo(basicInfo);
							}
							return returnClient;
						}

					});
			if (reClient.getId() > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(reClient);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.LOGIN_FAILED);
			}
		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
		}
		return responseWrapper;
	}

	/**
	 * This api will change client password
	 * 
	 * @param changePassword
	 * @return boolean
	 */
	public ResponseWrapper changePassword(ChangePassword changePassword)
			throws XstoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This will reset the client password
	 * 
	 * @param client
	 * @return boolean
	 */
	public ResponseWrapper forgetPassword(Client client)
			throws XstoreException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This api will deactivate client
	 * 
	 * @param client
	 * @return boolean
	 */
	public ResponseWrapper deactivateOrActivateClient(Client client)
			throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.DEACTIVATE_OR_ACTIVATE_CLIENT;

			Map<String, Object> values = new HashMap<String, Object>();
			values.put("status", client.isStatus());
			values.put("ID", client.getId());
			SqlParameterSource paramSource = new MapSqlParameterSource(
					values);
			int deactivated = this.getNamedParameterJdbcTemplate()
					.update(sql, paramSource);
			if (deactivated > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.CLIENT_DELETED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.CLIENT_DELETION_FAIL);
			}
		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("error in deactivateOrActivate client");
		} catch (Exception e) {
			logger.error("error in deactivateOrActivate client");
		}
		return responseWrapper;
	}

	private int addBasic(BasicInfo basicInfo) throws XstoreException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			sql = SqlScript.SAVE_BASIC_DETAILS;

			Map<String, Object> values = new HashMap<String, Object>();
			values.put("firstName", basicInfo.getFirstName());
			values.put("middleName", basicInfo.getMiddleName());
			values.put("lastName", basicInfo.getLastName());
			values.put("phoneNumber", basicInfo.getPhoneNumber());
			values.put("email", basicInfo.getEmail());
			values.put("address", basicInfo.getAddress());
			values.put("city", basicInfo.getCity());
			values.put("state", basicInfo.getState());
			values.put("country", basicInfo.getCountry());
			values.put("pin", basicInfo.getPin());
			values.put("fax", basicInfo.getFax());
			SqlParameterSource paramSource = new MapSqlParameterSource(
					values);
			this.getNamedParameterJdbcTemplate().update(sql,
					paramSource, keyHolder);
			logger.debug("SQL " + sql);
		} catch (DataAccessException ex) {
			logger.error("exception " + ex.getMessage());
		}
		return keyHolder.getKey().intValue();
	}

	private boolean updateBasic(BasicInfo basicInfo) throws XstoreException {
		try {
			sql = SqlScript.UPDATE_BASIC_DETAILS;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("firstName", basicInfo.getFirstName());
			values.put("middleName", basicInfo.getMiddleName());
			values.put("lastName", basicInfo.getLastName());
			values.put("phoneNumber", basicInfo.getPhoneNumber());
			values.put("email", basicInfo.getEmail());
			values.put("address", basicInfo.getAddress());
			values.put("city", basicInfo.getCity());
			values.put("state", basicInfo.getState());
			values.put("country", basicInfo.getCountry());
			values.put("pin", basicInfo.getPin());
			values.put("fax", basicInfo.getFax());
			values.put("ID", basicInfo.getId());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			int rowUpdatedCount = this
					.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if (rowUpdatedCount > 0) {
				return true;
			} else {
				return false;
			}

		} catch (DataAccessException e) {
			logger.error("Error in update basic info");
		}
		return false;

	}

	public ResponseWrapper dummy() throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.DUMMY;
			this.getJdbcTemplate().execute(sql);
			responseWrapper.setStatus(ResponseCode.OK);
			responseWrapper.setMessage(ResponseMessage.SUCCESS);
			responseWrapper.setResult(true);

		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			responseWrapper.setStatus("fail");
			responseWrapper.setMessage(e.getMessage());
			responseWrapper.setResult(false);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new XstoreException(e);

		}
		return responseWrapper;
	}

	/**
	 * 
	 */
	public List<Group> getAllGroupByClient(int id) throws RuntimeException {
		List<Group> groups = new ArrayList<Group>();
		try {
			sql = SqlScript.GET_GROUPS_BY_CLIENT;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			for(Map<String, Object> map : list) {
				Group group = new Group();
				group.setId((Integer) map.get("BID"));
				group.setName((String) map.get("NAME"));
				group.setStatus((Boolean) map.get("STATUS"));
				groups.add(group);
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return groups;
	}

	/**
	 * 
	 */
	public List<Group> getDeactivatedGroupByClient(int id)
			throws RuntimeException {
		List<Group> groups = new ArrayList<Group>();
		try {
			sql = SqlScript.GET_DEACTIVATED_GROUPS_BY_CLIENT;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			for(Map<String, Object> map : list) {
				Group group = new Group();
				group.setId((Integer) map.get("BID"));
				group.setName((String) map.get("NAME"));
				group.setStatus((Boolean) map.get("STATUS"));
				groups.add(group);
				
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return groups;
	}

	/**
	 * 
	 */
	public List<Group> getActivatedGroupByClient(int id)
			throws RuntimeException {
		List<Group> groups = new ArrayList<Group>();
		try {
			sql = SqlScript.GET_ACTIVATED_GROUPS_BY_CLIENT;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			for(Map<String, Object> map : list) {
				Group group = new Group();
				group.setId((Integer) map.get("BID"));
				group.setName((String) map.get("NAME"));
				group.setStatus((Boolean) map.get("STATUS"));
				groups.add(group);
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return groups;
	}
	
	public boolean addGroupToClient(int id, Group group) throws RuntimeException {
		try {
			sql = SqlScript.SAVE_GROUP;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("groupName", group.getName());
			values.put("clientId", id);
			values.put("status", group.isStatus());
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
			logger.error("error in add group to client with id : " + id);
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("error in add group to client with id : " + id);
			throw new RuntimeException(e);
		}
		
	}

}
