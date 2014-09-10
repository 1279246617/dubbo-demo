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
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.util.NumberUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("inWarehouseOrderItemDao")
public class InWarehouseOrderItemDaoImpl implements IInWarehouseOrderItemDao {

	Logger logger = Logger.getLogger(InWarehouseOrderItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveInWarehouseOrderItem(final InWarehouseOrderItem item) {
		final String sql = "insert into w_s_in_warehouse_order_item (package_id,quantity,sku) values (?,?,?)";
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

	/**
	 * 批量保存物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchInWarehouseOrderItem(final List<InWarehouseOrderItem> itemList) {
		final String sql = "insert into w_s_in_warehouse_order_item (package_id,quantity,sku) values (?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InWarehouseOrderItem item = itemList.get(i);
				ps.setLong(1, item.getPackageId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
			}

			@Override
			public int getBatchSize() {
				return itemList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}
	
	

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}