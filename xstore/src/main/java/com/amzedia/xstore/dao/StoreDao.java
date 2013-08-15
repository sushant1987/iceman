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

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.dao.interfaces.IStoreDao;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.Product;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.util.Message;
import com.amzedia.xstore.util.ResponseCode;
import com.amzedia.xstore.util.ResponseMessage;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun
 * 
 */
@Component
public class StoreDao extends BaseDao implements IStoreDao {

	private String sql;

	/*
	 * public ResponseWrapper addStore(Store store) throws XstoreException {
	 * ResponseWrapper responseWrapper = new ResponseWrapper(); try {
	 * 
	 * sql = SqlScript.ADD_STORE; Map<String, Object> values = new
	 * HashMap<String, Object>(); values.put("storeName", store.getName());
	 * values.put("groupId", store.getGroup().getId());
	 * values.put("currency", store.getCurrency()); values.put("timeZone",
	 * store.getTimeZone()); values.put("status", store.isStatus());
	 * SqlParameterSource params = new MapSqlParameterSource( values); int
	 * success = this.getNamedParameterJdbcTemplate() .update(sql, params);
	 * if (success > 0) { responseWrapper.setStatus(ResponseCode.OK);
	 * responseWrapper.setMessage(ResponseMessage.SUCCESS);
	 * responseWrapper.setResult(Message.STORE_ADDED); } else {
	 * responseWrapper.setStatus(ResponseCode.OK);
	 * responseWrapper.setMessage(ResponseMessage.SUCCESS);
	 * responseWrapper.setResult(Message.STORE_NOT_ADDED); } } catch
	 * (DataAccessException e) {
	 * responseWrapper.setStatus(ResponseCode.FAIL);
	 * responseWrapper.setMessage(ResponseMessage.FAIL);
	 * responseWrapper.setResult(e.getCause().getMessage());
	 * logger.error("error in adding store"); } catch (Exception e) {
	 * logger.error("error in adding store"); throw new XstoreException(); }
	 * return responseWrapper; }
	 */

	/*
	 * 
	 * This api will activate or deactivate client
	 * 
	 * @param client
	 * 
	 * @return boolean TODO
	 */
	public ResponseWrapper deactivateOrActivateStore(Store store)
			throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.DEACTIVATE_OR_ACTIVATE_STORE;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("status", store.isStatus());
			values.put("ID", store.getId());
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
			logger.error("error in deactivateOrActivate store");
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
		} catch (Exception e) {
			logger.error("error in deactivateOrActivate");
			throw new XstoreException();
		}
		return responseWrapper;

	}

	/**
	 * TODO
	 */
	public ResponseWrapper getStore(int id) throws XstoreException {
		if (logger.isDebugEnabled()) {
			logger.debug("StoreDao -->>  getStore -->> id is " + id);
		}
		ResponseWrapper responseWrapper = new ResponseWrapper();
		try {
			sql = SqlScript.GET_STORE;
			Store returnStore;
			final Store store = new Store();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ID", id);
			returnStore = this
					.getNamedParameterJdbcTemplate()
					.query(sql,
							paramMap,
							new ResultSetExtractor<Store>() {

								public Store extractData(
										ResultSet rs)
										throws SQLException {
									if (rs.next()) {
										store.setId(rs.getInt("ID"));
										store.setName(rs.getString("NAME"));
										store.setCurrency(rs
												.getString("CURRENCY"));
										store.setTimeZone(rs
												.getString("TIME_ZONE"));
										store.setStatus(rs
												.getBoolean("STATUS"));
									}
									return store;
								}
							});
			if (returnStore.getId() > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(returnStore);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.STORE_NOT_FOUND
						+ id);
			}
		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("Error in get store");
		} catch (Exception e) {
			logger.error("Error in get store");
			throw new XstoreException();
		}

		return responseWrapper;
	}

	/*
	 * this API will update the Store info
	 */
	public ResponseWrapper updateStore(Store store) throws XstoreException {
		ResponseWrapper responseWrapper = new ResponseWrapper();
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
			int success = this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			if (success > 0) {
				responseWrapper.setStatus(ResponseCode.OK);
				responseWrapper.setMessage(ResponseMessage.SUCCESS);
				responseWrapper.setResult(Message.STORE_UPDATED);
			} else {
				responseWrapper.setStatus(ResponseCode.FAIL);
				responseWrapper.setMessage(ResponseMessage.FAIL);
				responseWrapper.setResult(Message.STORE_NOT_UPDATED);
			}
		} catch (DataAccessException e) {
			responseWrapper.setStatus(ResponseCode.FAIL);
			responseWrapper.setMessage(ResponseMessage.FAIL);
			responseWrapper.setResult(e.getCause().getMessage());
			logger.error("Error in update store");
		} catch (Exception e) {
			logger.error("Error in update store");
			throw new XstoreException();
		}
		return responseWrapper;
	}

	/*
	 * API to add the product
	 */
	public boolean addProductToStore(int id, Product product)
			throws RuntimeException {
		try {
			sql = SqlScript.ADD_PRODUCT;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("productName", product.getProductName());
			values.put("productDescription",
					product.getProductDescription());
			values.put("storeId", id);
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
			logger.error("error in adding product to store id : "
					+ id);
			throw new RuntimeException(e);
		} catch (Exception e) {
			logger.error("error in adding product to store id : "
					+ id);
			throw new RuntimeException(e);
		}
	}

}
