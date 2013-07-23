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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.ICustomerDao;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.util.Message;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun Keswani
 * 
 */

@Component
public class CustomerDao extends BaseDao implements ICustomerDao {

	private static final Logger logger = Logger
			.getLogger(CustomerDao.class);
	private String sql;

	/**
	 * This api will bring Customer with customer id
	 * 
	 * @param id
	 * @return Customer
	 */
	public ResponseWrapper getCustomer(int id) throws XstoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("CustomerDao -->>  getCustomer -->> id is "
					+ id);
		}
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.GET_CUSTOMER;
			Customer returnCustomer;
			final Customer customer = new Customer();
			final BasicInfo basicInfo = new BasicInfo();
			final Store store = new Store();
			final Group group = new Group();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			returnCustomer = this
					.getNamedParameterJdbcTemplate()
					.query(sql,
							paramMap,
							new ResultSetExtractor<Customer>() {

								public Customer extractData(
										ResultSet rs)
										throws SQLException {
									if (rs.next()) {
										customer.setId(rs
												.getInt("ID"));
										customer.setCustomerName(rs
												.getString("USER_NAME"));
										customer.setCustomerType(rs
												.getString("USER_TYPE"));
										customer.setNewsLetter(rs
												.getBoolean("NEWSLETTER"));
										customer.setStatus(rs
												.getBoolean("STATUS"));
										group.setId(rs.getInt("BRAND_ID"));
										customer.setGroup(group);
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
										customer.setBasicInfo(basicInfo);
									}
									return customer;
								}
							});
			if (returnCustomer.getId() > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(returnCustomer);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.CUSTOMRE_NOT_FOUND
						+ id);
			}
		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
		} catch (Exception e) {
			throw new XstoreException(e.getCause().getMessage());
		}
		return responseWrapper;

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

	/*
	 * This api will add the customer under the group
	 */
	@Transactional(readOnly = false)
	public boolean registerCustomer(Customer customer)
			throws RuntimeException {
		try {
			int basicInfoId = addBasic(customer.getBasicInfo());
			sql = SqlScript.ADD_CUSTOMER;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("basicDetailId", basicInfoId);
			values.put("brandId", customer.getGroup().getId());
			values.put("userName", customer.getCustomerName());
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

	/*
	 * API for login for the customers
	 */
	public Customer loginCustomer(Customer customer) {
		Customer reCustomer;
		try {
			sql = SqlScript.LOGIN_CUSTOMER;
			final Customer returnCustomer = new Customer();
			final BasicInfo basicInfo = new BasicInfo();
			final Group group = new Group();
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("userName", customer.getUsername());
			values.put("password", customer.getPassword());
			reCustomer = this
					.getNamedParameterJdbcTemplate()
					.query(sql,
							values,
							new ResultSetExtractor<Customer>() {
								public Customer extractData(
										ResultSet rs)
										throws SQLException {
									if (rs.next()) {
										returnCustomer.setId(rs
												.getInt("ID"));
										returnCustomer.setUsername(rs
												.getNString("USER_NAME"));
										returnCustomer.setCustomerType(rs
												.getString("USER_TYPE"));
										returnCustomer.setNewsLetter(rs
												.getBoolean("NEWSLETTER"));
										returnCustomer.setStatus(rs
												.getBoolean("STATUS"));
										group.setId(rs.getInt("BRAND_ID"));
										returnCustomer.setGroup(group);
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
										returnCustomer.setBasicInfo(basicInfo);
									}
									return returnCustomer;
								}
							});

		} catch (DataAccessException e) {
			logger.error("exception in register customer"
					+ e.getMessage());
			throw new RuntimeException(e);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return reCustomer;
	}
}
