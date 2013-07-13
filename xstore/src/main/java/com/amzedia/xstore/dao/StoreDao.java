/**
 * 
 */
package com.amzedia.xstore.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.amzedia.xstore.dao.interfaces.IStoreDao;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun
 * 
 */
@Component
public class StoreDao extends BaseDao implements IStoreDao {

	private String sql;

	public boolean addStore(Store store) {
		try {

			sql = SqlScript.ADD_STORE;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("storeName", store.getName());
			values.put("groupId", store.getGroup().getId());
			values.put("currency", store.getCurrency());
			values.put("timeZone", store.getTimeZone());
			values.put("status", store.isStatus());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			return true;
		} catch (DataAccessException e) {
			System.out.println("error in adding store");
		}
		return false;
	}

	/*
	 * 
	 * This api will activate or deactivate client
	 * 
	 * @param client
	 * 
	 * @return boolean TODO
	 */
	public boolean deactivateOrActivateStore(Store store) {
		try {
			sql = SqlScript.DEACTIVATE_OR_ACTIVATE_STORE;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("status", store.isStatus());
			values.put("ID", store.getId());
			SqlParameterSource paramSource = new MapSqlParameterSource(
					values);
			this.getNamedParameterJdbcTemplate().update(sql,
					paramSource);
			return true;
		} catch (Exception e) {
			logger.error("error in deactivateOrActivate client");
		}
		return false;

	}

	/**
	 * TODO
	 */
	public Store getStore(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("StoreDao -->>  getStore -->> id is " + id);
		}
		sql = SqlScript.GET_STORE;
		final Store store = new Store();
		final Group group = new Group();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);
		return this.getNamedParameterJdbcTemplate().query(sql,
				paramMap, new ResultSetExtractor<Store>() {

					public Store extractData(ResultSet rs)
							throws SQLException {
						if (rs.next()) {
							store.setName(rs.getString("NAME"));
							store.setCurrency(rs
									.getString("CURRENCY"));
							store.setTimeZone(rs
									.getString("TIME_ZONE"));
							store.setStatus(rs
									.getBoolean("STATUS"));
							group.setId(rs.getInt("BRAND_ID"));
							store.setGroup(group);
						}
						return store;
					}
				});
	}

	/*
	 * this API will update the Store info
	 */
	public boolean updateStore(Store store) {
		try {
			sql = SqlScript.UPDATE_STORE;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("name", store.getName());
			values.put("currency", store.getCurrency());
			values.put("status", store.isStatus());
			values.put("timeZone", store.getTimeZone());
			values.put("ID", store.getId());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			return true;
		} catch (DataAccessException e) {
			logger.error("Error in update store");
		}
		return false;
	}

}
