package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IPackageItemDao;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("packageItemDao")
public class PackageItemDaoImpl implements IPackageItemDao {

	Logger logger = Logger.getLogger(PackageItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long savePackageItem(final com.coe.wms.model.warehouse.storage.PackageItem item) {
		final String sql = "insert into w_package_item (package_id,quantity,sku) values (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setLong(1, item.getPackageId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}