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
import org.springframework.transaction.annotation.Transactional;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.IGroupDao;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
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

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#addStoreToGroup(int, com.amzedia.xstore.model.Store)
	 */
	public boolean addStoreToGroup(int id, Store store)
			throws RuntimeException {
		try {

			sql = SqlScript.ADD_STORE;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("storeName", store.getName());
			values.put("groupId", id);
			values.put("currency", store.getCurrency());
			values.put("timeZone", store.getTimeZone());
			values.put("status", store.isStatus());
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
			logger.error("error in adding store to group id : " + id);
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("error in adding store to group id : " + id);
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#getStoresByGroup(int)
	 */
	public List<Store> getStoresByGroup(int id) throws RuntimeException {
		List<Store> stores= new ArrayList<Store>();
		try {
			sql = SqlScript.GET_STORES_BY_GROUP;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			for(Map<String, Object> map : list) {
				Store store = new Store();
				store.setId((Integer) map.get("ID"));
				store.setName((String) map.get("NAME"));
				store.setCurrency((String) map.get("CURRENCY"));
				store.setStatus((Boolean) map.get("STATUS"));
				store.setTimeZone((String) map.get("TIME_ZONE"));
				stores.add(store);
			}
		}catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return stores;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#getActivatedStoresByGroup(int)
	 */
	public List<Store> getActivatedStoresByGroup(int id)
			throws RuntimeException {
		List<Store> stores= new ArrayList<Store>();
		try {
			sql = SqlScript.GET_ACTIVATED_STORES_BY_GROUP;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			for(Map<String, Object> map : list) {
				Store store = new Store();
				store.setId((Integer) map.get("ID"));
				store.setName((String) map.get("NAME"));
				store.setCurrency((String) map.get("CURRENCY"));
				store.setStatus((Boolean) map.get("STATUS"));
				store.setTimeZone((String) map.get("TIME_ZONE"));
				stores.add(store);
			}
		}catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return stores;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#getDeactivatedStoresByGroup(int)
	 */
	public List<Store> getDeactivatedStoresByGroup(int id)
			throws RuntimeException {
		List<Store> stores= new ArrayList<Store>();
		try {
			sql = SqlScript.GET_DEACTIVATED_STORES_BY_GROUP;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
			for(Map<String, Object> map : list) {
				Store store = new Store();
				store.setId((Integer) map.get("ID"));
				store.setName((String) map.get("NAME"));
				store.setCurrency((String) map.get("CURRENCY"));
				store.setStatus((Boolean) map.get("STATUS"));
				store.setTimeZone((String) map.get("TIME_ZONE"));
				stores.add(store);
			}
		}catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return stores;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#registerCustomerToGroup(int, com.amzedia.xstore.model.Customer)
	 */
	@Transactional
	public boolean registerCustomerToGroup(int id, Customer customer)
			throws RuntimeException {
		try {
			int basicInfoId = addBasic(customer.getBasicInfo());
			sql = SqlScript.ADD_CUSTOMER;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("basicDetailId", basicInfoId);
			values.put("brandId", id);
			values.put("userName", customer.getUsername());
			values.put("password", customer.getPassword());
			values.put("userType", customer.getCustomerType());
			values.put("newsletter", customer.isNewsLetter());
			values.put("status", customer.isStatus());
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
			logger.error("exception in register customer"
					+ e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#getCustomersByGroup(int)
	 */
	public List<Customer> getCustomersByGroup(int id)
			throws RuntimeException {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			sql = SqlScript.GET_CUSTOMERS_BY_GROUP;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, values);
			for(Map<String, Object> map : list) {
				Customer customer = new Customer();
				BasicInfo basicInfo = new BasicInfo();
				basicInfo.setFirstName((String) map.get("FIRST_NAME"));
				basicInfo.setMiddleName((String) map.get("MIDDLE_NAME"));
				basicInfo.setLastName((String) map.get("LAST_NAME"));
				basicInfo.setPhoneNumber((String) map.get("PHONE_NUMBER"));
				basicInfo.setEmail((String) map.get("EMAIL"));
				basicInfo.setAddress((String) map.get("ADDRESS"));
				basicInfo.setCity((String) map.get("CITY"));
				basicInfo.setState((String) map.get("STATE"));
				basicInfo.setCountry((String) map.get("COUNTRY"));
				basicInfo.setPin((String) map.get("PIN_CODE"));
				basicInfo.setFax((String) map.get("FAX"));
				customer.setBasicInfo(basicInfo);
				customer.setId((Integer) map.get("ID"));
				customer.setUsername((String) map.get("USER_NAME"));
				customer.setCustomerType((String) map.get("USER_TYPE"));
				customer.setNewsLetter((Boolean) map.get("NEWSLETTER"));
				customer.setStatus((Boolean) map.get("STATUS"));
				customers.add(customer);
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return customers;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#getActivatedCustomersByGroup(int)
	 */
	public List<Customer> getActivatedCustomersByGroup(int id)
			throws RuntimeException {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			sql = SqlScript.GET_ACTIVATED_CUSTOMERS_BY_GROUP;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, values);
			for(Map<String, Object> map : list) {
				Customer customer = new Customer();
				BasicInfo basicInfo = new BasicInfo();
				basicInfo.setFirstName((String) map.get("FIRST_NAME"));
				basicInfo.setMiddleName((String) map.get("MIDDLE_NAME"));
				basicInfo.setLastName((String) map.get("LAST_NAME"));
				basicInfo.setPhoneNumber((String) map.get("PHONE_NUMBER"));
				basicInfo.setEmail((String) map.get("EMAIL"));
				basicInfo.setAddress((String) map.get("ADDRESS"));
				basicInfo.setCity((String) map.get("CITY"));
				basicInfo.setState((String) map.get("STATE"));
				basicInfo.setCountry((String) map.get("COUNTRY"));
				basicInfo.setPin((String) map.get("PIN_CODE"));
				basicInfo.setFax((String) map.get("FAX"));
				customer.setBasicInfo(basicInfo);
				customer.setId((Integer) map.get("ID"));
				customer.setUsername((String) map.get("USER_NAME"));
				customer.setCustomerType((String) map.get("USER_TYPE"));
				customer.setNewsLetter((Boolean) map.get("NEWSLETTER"));
				customer.setStatus((Boolean) map.get("STATUS"));
				customers.add(customer);
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return customers;
	}

	/* (non-Javadoc)
	 * @see com.amzedia.xstore.dao.interfaces.IGroupDao#getDeavtivatedCustomersByGroup(int)
	 */
	public List<Customer> getDeavtivatedCustomersByGroup(int id)
			throws RuntimeException {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			sql = SqlScript.GET_DEACTIVATED_CUSTOMERS_BY_GROUP;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("ID", id);
			List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, values);
			for(Map<String, Object> map : list) {
				Customer customer = new Customer();
				BasicInfo basicInfo = new BasicInfo();
				basicInfo.setFirstName((String) map.get("FIRST_NAME"));
				basicInfo.setMiddleName((String) map.get("MIDDLE_NAME"));
				basicInfo.setLastName((String) map.get("LAST_NAME"));
				basicInfo.setPhoneNumber((String) map.get("PHONE_NUMBER"));
				basicInfo.setEmail((String) map.get("EMAIL"));
				basicInfo.setAddress((String) map.get("ADDRESS"));
				basicInfo.setCity((String) map.get("CITY"));
				basicInfo.setState((String) map.get("STATE"));
				basicInfo.setCountry((String) map.get("COUNTRY"));
				basicInfo.setPin((String) map.get("PIN_CODE"));
				basicInfo.setFax((String) map.get("FAX"));
				customer.setBasicInfo(basicInfo);
				customer.setId((Integer) map.get("ID"));
				customer.setUsername((String) map.get("USER_NAME"));
				customer.setCustomerType((String) map.get("USER_TYPE"));
				customer.setNewsLetter((Boolean) map.get("NEWSLETTER"));
				customer.setStatus((Boolean) map.get("STATUS"));
				customers.add(customer);
			}
		} catch (DataAccessException e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			throw new RuntimeException(e);
		}
		return customers;
	}
	
	private int addBasic(BasicInfo basicInfo) {
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
		} catch (DataAccessException e) {
			logger.error("exception in register Basic Info"
					+ e.getMessage());
		}
		return keyHolder.getKey().intValue();
	}

}
