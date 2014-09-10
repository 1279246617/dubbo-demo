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
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("inWarehouseOrderDao")
public class InWarehouseOrderDaoImpl implements IInWarehouseOrderDao {

	Logger logger = Logger.getLogger(InWarehouseOrderDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveInWarehouseOrder(final InWarehouseOrder pag) {
		final String sql = "insert into w_s_in_warehouse_order (user_id,package_no,package_tracking_no,weight,small_package_quantity,created_time,remark) values (?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, pag.getUserId());
				ps.setString(2, pag.getPackageNo());
				ps.setString(3, pag.getPackageTrackingNo());
				ps.setDouble(4, pag.getWeight());
				ps.setInt(5, pag.getSmallPackageQuantity());
				ps.setLong(6, pag.getCreatedTime());
				ps.setString(7, pag.getRemark());
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