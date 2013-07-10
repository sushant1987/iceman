/**
 * 
 */
package com.amzedia.xstore.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.amzedia.xstore.dao.interfaces.IStoreDao;
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
			values.put("clientId", store.getClient().getId());
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
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.amzedia.xstore.dao.interfaces.IStoreDao#deactivateOrActivateStore
	 * (com.amzedia.xstore.model.Store)
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

}
