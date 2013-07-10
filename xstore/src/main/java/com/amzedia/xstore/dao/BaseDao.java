/**
 * 
 */
package com.amzedia.xstore.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

/**
 * @author Sushant
 *
 */
@Component
public class BaseDao extends JdbcDaoSupport{

	@Autowired
    public void currentDataSource(DataSource dataSource) {
	super.setDataSource(dataSource);
    }

    protected NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
	return new NamedParameterJdbcTemplate(this.getDataSource());
    }
}
