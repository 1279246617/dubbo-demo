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
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("outWarehouseOrderItemDao")
public class OutWarehouseOrderItemDaoImpl implements IOutWarehouseOrderItemDao {

	Logger logger = Logger.getLogger(OutWarehouseOrderItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveOutWarehouseOrderItem(final OutWarehouseOrderItem item) {
		final String sql = "insert into w_s_out_warehouse_order_item (out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification) values (?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getOutWarehouseOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				if (item.getSkuUnitPrice() != null) {
					ps.setDouble(5, item.getSkuUnitPrice());
				} else {
					ps.setNull(5, Types.DOUBLE);
				}
				ps.setString(6, item.getSkuPriceCurrency());
				ps.setString(7, item.getRemark());
				if (item.getSkuNetWeight() != null) {
					ps.setDouble(8, item.getSkuNetWeight());
				} else {
					ps.setNull(8, Types.DOUBLE);
				}
				ps.setString(9, item.getSkuNo());
				ps.setString(10, item.getSpecification());
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
	public int saveBatchOutWarehouseOrderItem(final List<OutWarehouseOrderItem> itemList) {
		final String sql = "insert into w_s_out_warehouse_order_item (out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification) values (?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderItem item = itemList.get(i);
				ps.setLong(1, item.getOutWarehouseOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				if (item.getSkuUnitPrice() != null) {
					ps.setDouble(5, item.getSkuUnitPrice());
				} else {
					ps.setNull(5, Types.DOUBLE);
				}
				ps.setString(6, item.getSkuPriceCurrency());
				ps.setString(7, item.getRemark());
				if (item.getSkuNetWeight() != null) {
					ps.setDouble(8, item.getSkuNetWeight());
				} else {
					ps.setNull(8, Types.DOUBLE);
				}
				ps.setString(9, item.getSkuNo());
				ps.setString(10, item.getSpecification());
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
	public int saveBatchOutWarehouseOrderItemWithOrderId(final List<OutWarehouseOrderItem> itemList, final Long orderId) {
		final String sql = "insert into w_s_out_warehouse_order_item (out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification) values (?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				OutWarehouseOrderItem item = itemList.get(i);
				ps.setLong(1, orderId);
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				if (item.getSkuUnitPrice() != null) {
					ps.setDouble(5, item.getSkuUnitPrice());
				} else {
					ps.setNull(5, Types.DOUBLE);
				}
				ps.setString(6, item.getSkuPriceCurrency());
				ps.setString(7, item.getRemark());
				if (item.getSkuNetWeight() != null) {
					ps.setDouble(8, item.getSkuNetWeight());
				} else {
					ps.setNull(8, Types.DOUBLE);
				}
				ps.setString(9, item.getSkuNo());
				ps.setString(10, item.getSpecification());
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
	 * 
	 */
	@Override
	public List<OutWarehouseOrderItem> findOutWarehouseOrderItem(OutWarehouseOrderItem outWarehouseOrderItem, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,out_warehouse_order_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification from w_s_out_warehouse_order_item where 1=1 ");
		if (outWarehouseOrderItem != null) {
			if (StringUtil.isNotNull(outWarehouseOrderItem.getSku())) {
				sb.append(" and sku = '" + outWarehouseOrderItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItem.getSkuNo())) {
				sb.append(" and sku_no = '" + outWarehouseOrderItem.getSkuNo() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItem.getSpecification())) {
				sb.append(" and specification = '" + outWarehouseOrderItem.getSpecification() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItem.getSkuName())) {
				sb.append(" and sku_name = '" + outWarehouseOrderItem.getSkuName() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItem.getSkuPriceCurrency())) {
				sb.append(" and sku_price_currency = '" + outWarehouseOrderItem.getSkuPriceCurrency() + "' ");
			}
			if (outWarehouseOrderItem.getSkuUnitPrice() != null) {
				sb.append(" and sku_price_currency = '" + outWarehouseOrderItem.getSkuUnitPrice() + "' ");
			}
			if (StringUtil.isNotNull(outWarehouseOrderItem.getRemark())) {
				sb.append(" and remark = '" + outWarehouseOrderItem.getRemark() + "' ");
			}
			if (outWarehouseOrderItem.getOutWarehouseOrderId() != null) {
				sb.append(" and out_warehouse_order_id = " + outWarehouseOrderItem.getOutWarehouseOrderId());
			}
			if (outWarehouseOrderItem.getId() != null) {
				sb.append(" and id = '" + outWarehouseOrderItem.getId() + "' ");
			}
			if (outWarehouseOrderItem.getQuantity() != null) {
				sb.append(" and quantity = " + outWarehouseOrderItem.getQuantity());
			}
			if (outWarehouseOrderItem.getSkuNetWeight() != null) {
				sb.append(" and sku_net_weight = " + outWarehouseOrderItem.getSkuNetWeight());
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.debug("查询出库订单明细sql:" + sql);
		List<OutWarehouseOrderItem> outWarehouseOrderItemList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(OutWarehouseOrderItem.class));
		return outWarehouseOrderItemList;
	}

	@Override
	public String getSkuNameByCustomerIdAndSku(String sku, Long userIdOfCustomer) {
		StringBuffer sb = new StringBuffer();
		sb.append("select distinct sku_name  from w_s_out_warehouse_order_item i inner join w_s_out_warehouse_order r on i.out_warehouse_order_id=r.id where 1=1 ");
		sb.append(" and r.user_id_of_customer = " + userIdOfCustomer);
		sb.append(" and i.sku = " + sku);
		Pagination page = new Pagination();
		page.curPage = 1;
		page.pageSize = 1;
		sb.append(page.generatePageSql());
		String skuName = jdbcTemplate.queryForObject(sb.toString(), String.class);
		return skuName;
	}

	@Override
	public Long sumSkuQuantityByOrderIdAndSku(List<Long> orderIds, String sku) {
		String orderIdStrs = "";
		for (Long orderId : orderIds) {
			orderIdStrs += orderId + ",";
		}
		if (orderIdStrs.endsWith(",")) {
			orderIdStrs = orderIdStrs.substring(0, orderIdStrs.length() - 1);
		}
		if (StringUtil.isNull(orderIdStrs)) {
			return 0l;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(quantity)  from w_s_out_warehouse_order_item i inner join w_s_out_warehouse_order r on i.out_warehouse_order_id=r.id where 1=1 ");
		sb.append(" and i.sku = '" + sku + "'");
		sb.append(" and r.id in( " + orderIdStrs + ")");
		Long sum = jdbcTemplate.queryForObject(sb.toString(), Long.class);
		if (sum == null) {
			return 0l;
		}
		return sum;
	}
}