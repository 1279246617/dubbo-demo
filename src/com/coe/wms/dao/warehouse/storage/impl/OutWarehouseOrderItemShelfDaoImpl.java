package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemShelfDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehouseOrderItemShelfDao")
public class OutWarehouseOrderItemShelfDaoImpl implements IOutWarehouseOrderItemShelfDao {

	Logger logger = Logger.getLogger(OutWarehouseOrderItemShelfDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehouseOrderItemShelf(final OutWarehouseOrderItemShelf item) {
		final String sql = "insert into w_s_out_warehouse_order_item_shelf (out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,seat_code,sku_net_weight,batch_no) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getOutWarehouseOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setDouble(5, item.getSkuUnitPrice());
				ps.setString(6, item.getSkuPriceCurrency());
				ps.setString(7, item.getSeatCode());
				ps.setDouble(8, item.getSkuNetWeight());
				ps.setString(9, item.getBatchNo());
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
	public int saveBatchOutWarehouseOrderItemShelf(final List<OutWarehouseOrderItemShelf> itemList) {
		final String sql = "insert into w_s_out_warehouse_order_item_shelf (out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,seat_code,sku_net_weight,batch_no) values (?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderItemShelf item = itemList.get(i);
				ps.setLong(1, item.getOutWarehouseOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setDouble(5, item.getSkuUnitPrice());
				ps.setString(6, item.getSkuPriceCurrency());
				ps.setString(7, item.getSeatCode());
				ps.setDouble(8, item.getSkuNetWeight());
				ps.setString(9, item.getBatchNo());
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
	public int saveBatchOutWarehouseOrderItemShelfWithOrderId(final List<OutWarehouseOrderItemShelf> itemList, final Long orderId) {
		final String sql = "insert into w_s_out_warehouse_order_item_shelf (out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,seat_code,sku_net_weight,batch_no) values (?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderItemShelf item = itemList.get(i);
				ps.setLong(1, orderId);
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				if (item.getSkuUnitPrice() == null) {
					ps.setNull(5, Types.DOUBLE);
				} else {
					ps.setDouble(5, item.getSkuUnitPrice());
				}
				ps.setString(6, item.getSkuPriceCurrency());
				ps.setString(7, item.getSeatCode());
				if (item.getSkuNetWeight() == null) {
					ps.setNull(8, Types.DOUBLE);
				} else {
					ps.setDouble(8, item.getSkuNetWeight());
				}
				ps.setString(9, item.getBatchNo());
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
	 * 参数一律使用实体类加Map . 
	 */
	@Override
	public List<OutWarehouseOrderItemShelf> findOutWarehouseOrderItemShelf(OutWarehouseOrderItemShelf outWarehouseOrderItemShelf,
			Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,seat_code,sku_net_weight,batch_no from w_s_out_warehouse_order_item_shelf where 1=1 ");
		if (outWarehouseOrderItemShelf != null) {
			if (StringUtil.isNotNull(outWarehouseOrderItemShelf.getSku())) {
				sb.append(" and sku = '" + outWarehouseOrderItemShelf.getSku() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItemShelf.getSkuName())) {
				sb.append(" and sku_name = '" + outWarehouseOrderItemShelf.getSkuName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItemShelf.getSkuPriceCurrency())) {
				sb.append(" and sku_price_currency = '" + outWarehouseOrderItemShelf.getSkuPriceCurrency() + "' ");
			}
			if (outWarehouseOrderItemShelf.getSkuUnitPrice() != null) {
				sb.append(" and sku_price_currency = '" + outWarehouseOrderItemShelf.getSkuUnitPrice() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItemShelf.getSeatCode())) {
				sb.append(" and seat_code = '" + outWarehouseOrderItemShelf.getSeatCode() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItemShelf.getBatchNo())) {
				sb.append(" and batch_no = '" + outWarehouseOrderItemShelf.getBatchNo() + "' ");
			}
			if (outWarehouseOrderItemShelf.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = " + outWarehouseOrderItemShelf.getOutWarehouseOrderId());
			}
			if (outWarehouseOrderItemShelf.getId() != null) {
				sb.append(" and id = '" + outWarehouseOrderItemShelf.getId() + "' ");
			}
			if (outWarehouseOrderItemShelf.getQuantity() != null) {
				sb.append(" and quantity = " + outWarehouseOrderItemShelf.getQuantity());
			}
			if (outWarehouseOrderItemShelf.getSkuNetWeight() != null) {
				sb.append(" and sku_net_weight = " + outWarehouseOrderItemShelf.getSkuNetWeight());
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.info("查询出库订单明细sql:" + sql);
		List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderItemShelf.class));
		return outWarehouseOrderItemShelfList;
	}
}