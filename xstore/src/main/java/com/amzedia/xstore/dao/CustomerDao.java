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

import com.amzedia.xstore.dao.interfaces.ICustomerDao;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun Keswani
 * 
 */

@Component
public class CustomerDao extends BaseDao implements ICustomerDao {

	private static final Logger logger = Logger.getLogger(CustomerDao.class);
	private String sql;

	/**
	 * This api will bring Customer with customer id
	 * 
	 * @param id
	 * @return Customer
	 */
	public Customer getCustomer(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("CustomerDao -->>  getCustomer -->> id is " + id);
		}
		sql = SqlScript.GET_Customer;
		final Customer customer = new Customer();
		final BasicInfo basicInfo = new BasicInfo();
		final Store store = new Store();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);
		return this.getNamedParameterJdbcTemplate().query(sql,
				paramMap, new ResultSetExtractor<Customer>() {

					public Customer extractData(ResultSet rs)
							throws SQLException {
						if (rs.next()) {
							customer.setId(rs.getInt("ID"));
							customer.setCustomerName(rs
									.getString("USER_NAME"));
							customer.setCustomerType(rs
									.getString("USER_TYPE"));
							customer.setNewsLetter(rs
									.getBoolean("NEWSLETTER"));
							customer.setStatus(rs
									.getBoolean("STATUS"));
							store.setId(rs.getInt("STORE_ID"));
							customer.setStore(store);
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

	}
}
