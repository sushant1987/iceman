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

import com.amzedia.xstore.dao.interfaces.IClientDao;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.ChangePassword;
import com.amzedia.xstore.model.Client;
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
	public Client getClient(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("ClientDao -->>  getClient -->> id is "
					+ id);
		}
		sql = SqlScript.GET_CLIENT;
		final Client client = new Client();
		final BasicInfo basicInfo = new BasicInfo();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);
		return this.getNamedParameterJdbcTemplate().query(sql,
				paramMap, new ResultSetExtractor<Client>() {

					public Client extractData(ResultSet rs)
							throws SQLException {
						if (rs.next()) {
							client.setId(rs.getInt("ID"));
							client.setUserName(rs
									.getString("USER_NAME"));
							client.setStatus(rs
									.getBoolean("STATUS"));
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
	}

	/**
	 * This api will add new client
	 * 
	 * @param client
	 * @return boolean
	 */
	public boolean registerClient(Client client) {
		try {

			int basicInfoId = addBasic(client.getBasicInfo());
			sql = SqlScript.SAVE_CLIENT;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("basicDetailId", basicInfoId);
			values.put("userName", client.getUserName());
			values.put("password", client.getPassword());
			values.put("status", client.isStatus());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			return true;
		} catch (DataAccessException e) {
			logger.error("error in register client");
		}
		return false;
	}

	/**
	 * This api will update client profile
	 * 
	 * @param client
	 * @return boolean
	 */
	public boolean updateClient(Client client) {
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
				return updateBasic(client.getBasicInfo());
			} else {
				return false;
			}
		} catch (DataAccessException e) {
			logger.error("Error in update client");
		}
		return false;
	}

	/**
	 * This will return client object on basis of correct user name and
	 * password
	 * 
	 * @param client
	 * @return Client
	 */
	public Client loginClient(Client client) {
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
			final BasicInfo basicInfo = new BasicInfo();
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("userName", client.getUserName());
			values.put("password", client.getPassword());
			return this.getNamedParameterJdbcTemplate().query(sql,
					values,
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

		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
		}
		return client;
	}

	/**
	 * This api will change client password
	 * 
	 * @param changePassword
	 * @return boolean
	 */
	public boolean changePassword(ChangePassword changePassword) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This will reset the client password
	 * 
	 * @param client
	 * @return boolean
	 */
	public boolean forgetPassword(Client client) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This api will deactivate client
	 * 
	 * @param client
	 * @return boolean
	 */
	public boolean deactivateOrActivateClient(Client client) {
		try {
			sql = SqlScript.DEACTIVATE_OR_ACTIVATE_CLIENT;
			
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("status", client.isStatus());
			values.put("ID", client.getId());
			SqlParameterSource paramSource = new MapSqlParameterSource(
					values);
			this.getNamedParameterJdbcTemplate().update(sql, paramSource);
			return true;
		} catch (Exception e) {
			logger.error("error in deactivateOrActivate client");
		}
		return false;
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
			logger.debug("SQL " + sql);
		} catch (DataAccessException ex) {
			logger.error("exception " + ex.getMessage());
		}
		return keyHolder.getKey().intValue();
	}

	private int getBasicDetailId(int id) {
		return id;

	}

	private boolean updateBasic(BasicInfo basicInfo) {
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

	public boolean dummy() {
		try {
			sql = SqlScript.DUMMY;
			this.getJdbcTemplate().execute(sql);
			return true;

		} catch (Exception e) {
			logger.error("exception " + e.getMessage());
			return false;
		}

	}

}
