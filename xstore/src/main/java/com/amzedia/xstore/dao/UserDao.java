/**
 * 
 */
package com.amzedia.xstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.amzedia.xstore.dao.interfaces.IUserDao;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.model.User;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun Keswani
 * 
 */

@Component
public class UserDao extends BaseDao implements IUserDao {

	private static final Logger logger = Logger.getLogger(UserDao.class);
	private String sql;

	/**
	 * This api will bring User with user id
	 * 
	 * @param id
	 * @return User
	 */
	public User getUser(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("UserDao -->>  getUser -->> id is " + id);
		}
		sql = SqlScript.GET_USER;
		final User user = new User();
		final BasicInfo basicInfo = new BasicInfo();
		final Store store = new Store();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);
		return this.getNamedParameterJdbcTemplate().query(sql,
				paramMap, new ResultSetExtractor<User>() {

					public User extractData(ResultSet rs)
							throws SQLException {
						if (rs.next()) {
							user.setId(rs.getInt("ID"));
							user.setUserName(rs
									.getString("USER_NAME"));
							user.setUserType(rs
									.getString("USER_TYPE"));
							user.setNewsLetter(rs
									.getBoolean("NEWSLETTER"));
							user.setStatus(rs
									.getBoolean("STATUS"));
							store.setId(rs.getInt("STORE_ID"));
							user.setStore(store);
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
							user.setBasicInfo(basicInfo);
						}
						return user;
					}
				});

	}
}
