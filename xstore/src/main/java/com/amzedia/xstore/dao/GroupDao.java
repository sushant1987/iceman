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

import com.amzedia.xstore.dao.interfaces.IGroupDao;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.util.SqlScript;

/**
 * @author Tarun Keswani
 *
 */
@Component
public class GroupDao extends BaseDao implements IGroupDao{
	
	private static final Logger logger = Logger.getLogger(GroupDao.class);
	private String sql;

	/**
	 * This api will bring Group with group id
	 * 
	 * @param id
	 * @return Group
	 */
	public Group getGroup(int id) {
		if (logger.isDebugEnabled()) {
			logger.debug("GroupDao -->>  getGroup -->> id is "
					+ id);
		}
		sql = SqlScript.GET_GROUP;
		final Group group = new Group();
		final Client client = new Client();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ID", id);
		return this.getNamedParameterJdbcTemplate().query(sql,
				paramMap, new ResultSetExtractor<Group>() {

					public Group extractData(ResultSet rs)
							throws SQLException {
						if (rs.next()) {
							group.setId(rs.getInt("ID"));
							group.setName(rs.getString("NAME"));
							group.setStatus(rs.getBoolean("STATUS"));
							client.setId(rs.getInt("CLIENT_ID"));
							group.setClient(client);
						}

						return group;
					}
				});
		
	}

	/* 
	 * This API will add the Group under the client
	 * 
	 * @param group
	 * @return boolean
	 */
	public boolean addGroup(Group group) {
		try {

			sql = SqlScript.SAVE_GROUP;
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("groupName", group.getName());
			values.put("clientId", group.getClient().getId());
			values.put("status", group.isStatus());
			SqlParameterSource params = new MapSqlParameterSource(
					values);
			this.getNamedParameterJdbcTemplate()
					.update(sql, params);
			return true;
		} catch (DataAccessException e) {
			logger.error("error in register group");
		}
		return false;
		
	}
	
	

}
