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
		final String sql = "insert into w_s_in_warehouse_order_item (order_id,quantity,sku,sku_name,sku_remark,validity_time,production_batch_no,sku_no) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setString(5, item.getSkuRemark());
				if (item.getValidityTime() != null) {
					ps.setLong(6, item.getValidityTime());
				} else {
					ps.setNull(6, Types.BIGINT);
				}
				ps.setString(7, item.getProductionBatchNo());
				ps.setString(8, item.getSkuNo());
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
		final String sql = "insert into w_s_in_warehouse_order_item (order_id,quantity,sku,sku_name,sku_remark,validity_time,production_batch_no,sku_no) values (?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InWarehouseOrderItem item = itemList.get(i);
				ps.setLong(1, item.getOrderId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setString(5, item.getSkuRemark());
				if (item.getValidityTime() != null) {
					ps.setLong(6, item.getValidityTime());
				} else {
					ps.setNull(6, Types.BIGINT);
				}
				ps.setString(7, item.getProductionBatchNo());
				ps.setString(8, item.getSkuNo());
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
		final String sql = "insert into w_s_in_warehouse_order_item (order_id,quantity,sku,sku_name,sku_remark,validity_time,production_batch_no,sku_no) values (?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InWarehouseOrderItem item = itemList.get(i);
				ps.setLong(1, orderId);
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getSkuName());
				ps.setString(5, item.getSkuRemark());
				if (item.getValidityTime() != null) {
					ps.setLong(6, item.getValidityTime());
				} else {
					ps.setNull(6, Types.BIGINT);
				}
				ps.setString(7, item.getProductionBatchNo());
				ps.setString(8, item.getSkuNo());
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
	public List<InWarehouseOrderItem> findInWarehouseOrderItem(InWarehouseOrderItem inWarehouseOrderItem, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,order_id,quantity,sku,sku_name,sku_remark,validity_time,production_batch_no,sku_no from w_s_in_warehouse_order_item where 1=1 ");
		if (inWarehouseOrderItem != null) {
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSku())) {
				sb.append(" and sku = '" + inWarehouseOrderItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSkuNo())) {
				sb.append(" and sku_no = '" + inWarehouseOrderItem.getSkuNo() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSkuName())) {
				sb.append(" and sku_name = '" + inWarehouseOrderItem.getSkuName() + "' ");
			}
			if (inWarehouseOrderItem.getOrderId() != null) {
				sb.append(" and order_id = '" + inWarehouseOrderItem.getOrderId() + "' ");
			}
			if (inWarehouseOrderItem.getProductionBatchNo() != null) {
				sb.append(" and production_batch_no = '" + inWarehouseOrderItem.getProductionBatchNo() + "' ");
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.debug("查询入库订单明细sql:" + sql);
		List<InWarehouseOrderItem> inWarehouseOrderItemList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(InWarehouseOrderItem.class));

		logger.debug("查询入库订单明细sql:" + sql + " size:" + inWarehouseOrderItemList.size());

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
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSkuNo())) {
				sb.append(" and sku_no = '" + inWarehouseOrderItem.getSkuNo() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseOrderItem.getSkuName())) {
				sb.append(" and sku_name = '" + inWarehouseOrderItem.getSkuName() + "' ");
			}
			if (inWarehouseOrderItem.getOrderId() != null) {
				sb.append(" and order_id = '" + inWarehouseOrderItem.getOrderId() + "' ");
			}
			if (inWarehouseOrderItem.getProductionBatchNo() != null) {
				sb.append(" and production_batch_no = '" + inWarehouseOrderItem.getProductionBatchNo() + "' ");
			}
		}
		String sql = sb.toString();
		logger.debug("统计入库订单明细sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public String getSkuNameByCustomerIdAndSku(String sku, Long userIdOfCustomer) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sku_name  from w_s_in_warehouse_order_item i inner join w_s_in_warehouse_order r on i.order_id=r.id where 1=1 ");
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
	public long saveInWarehouseOrderItemSku(Long id, String sku) {
		String sql = "update w_s_in_warehouse_order_item set sku ='" + sku + "' where id=" + id;
		return jdbcTemplate.update(sql);
	}

}