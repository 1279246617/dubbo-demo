package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
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
		final String sql = "insert into w_s_in_warehouse_order_item (order_id,quantity,sku,sku_name,sku_remark) values (?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setString(5, item.getSkuRemark());
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
		final String sql = "insert into w_s_in_warehouse_order_item (order_id,quantity,sku,sku_name,sku_remark) values (?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InWarehouseOrderItem item = itemList.get(i);
				ps.setLong(1, item.getOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setString(5, item.getSkuRemark());
			}

			@Override
			public int getBatchSize() {
				return itemList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public int saveBatchInWarehouseOrderItemWithOrderId(final List<InWarehouseOrderItem> itemList, final Long orderId) {
		final String sql = "insert into w_s_in_warehouse_order_item (order_id,quantity,sku,sku_name,sku_remark) values (?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InWarehouseOrderItem item = itemList.get(i);
				ps.setLong(1, orderId);
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setString(5, item.getSkuRemark());
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

	/**
	 * 查询入库订单
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<InWarehouseOrderItem> findInWarehouseOrderItem(InWarehouseOrderItem inWarehouseOrderItem, Map<String, String> moreParam,
			Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,order_id,quantity,sku,sku_name,sku_remark from w_s_in_warehouse_order_item where 1=1 ");
		if (inWarehouseOrderItem != null) {
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSku())) {
				sb.append(" and sku = '" + inWarehouseOrderItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSkuName())) {
				sb.append(" and sku_name = '" + inWarehouseOrderItem.getSkuName() + "' ");
			}
			if (inWarehouseOrderItem.getOrderId() != null) {
				sb.append(" and order_id = '" + inWarehouseOrderItem.getOrderId() + "' ");
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.info("查询入库订单明细sql:" + sql);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(InWarehouseOrderItem.class));

		logger.info("查询入库订单明细sql:" + sql + " size:" + inWarehouseOrderItemList.size());

		return inWarehouseOrderItemList;
	}

	/**
	 * 查询入库明细记录
	 */
	@Override
	public Long countInWarehouseOrderItem(InWarehouseOrderItem inWarehouseOrderItem, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_s_in_warehouse_order_item where 1=1 ");
		if (inWarehouseOrderItem != null) {
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSku())) {
				sb.append(" and sku = '" + inWarehouseOrderItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSkuName())) {
				sb.append(" and sku_name = '" + inWarehouseOrderItem.getSkuName() + "' ");
			}
			if (inWarehouseOrderItem.getOrderId() != null) {
				sb.append(" and order_id = '" + inWarehouseOrderItem.getOrderId() + "' ");
			}
		}
		String sql = sb.toString();
		logger.info("统计入库订单明细sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}
}