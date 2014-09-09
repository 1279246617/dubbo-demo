package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IPackageItemDao;
import com.coe.wms.model.warehouse.storage.PackageItem;
import com.mysql.jdbc.Statement;

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
	public long savePackageItem(final PackageItem item) {
		final String sql = "insert into w_package_item (package_id,quantity,sku) values (?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getPackageId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	public int saveBatchPackageItem(final List<PackageItem> itemList) {
		final String sql = "insert into w_package_item (package_id,quantity,sku) values (?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				PackageItem item = itemList.get(i);
				ps.setLong(1, item.getPackageId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
			}

			@Override
			public int getBatchSize() {
				return itemList.size();
			}
		});
		
		for(int a :batchUpdateSize){
			System.out.println(a);
		}
		return 0;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}