package com.coe.wms.dao.warehouse.transport.impl;

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
import com.coe.wms.dao.warehouse.transport.IFirstWaybillItemDao;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("firstWaybillItemDao")
public class FirstWaybillItemDaoImpl implements IFirstWaybillItemDao {

	Logger logger = Logger.getLogger(FirstWaybillItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveFirstWaybillItem(final FirstWaybillItem item) {
		final String sql = "insert into w_t_first_waybill_item (first_waybill_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,order_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getFirstWaybillId());
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
				ps.setLong(11, item.getOrderId());
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
	public int saveBatchFirstWaybillItem(final List<FirstWaybillItem> itemList) {
		final String sql = "insert into w_t_first_waybill_item (first_waybill_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,order_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				FirstWaybillItem item = itemList.get(i);
				ps.setLong(1, item.getFirstWaybillId());
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
				ps.setLong(11, item.getOrderId());
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
	public int saveBatchFirstWaybillItemWithFirstWaybillId(final List<FirstWaybillItem> itemList, final Long FirstWaybillId) {
		final String sql = "insert into w_t_first_waybill_item (first_waybill_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,order_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				FirstWaybillItem item = itemList.get(i);
				ps.setLong(1, FirstWaybillId);
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
				ps.setLong(11, item.getOrderId());
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
	public List<FirstWaybillItem> findFirstWaybillItem(FirstWaybillItem FirstWaybillItem, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,first_waybill_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,order_id from w_t_first_waybill_item where 1=1 ");
		if (FirstWaybillItem != null) {
			if (StringUtil.isNotNull(FirstWaybillItem.getSku())) {
				sb.append(" and sku = '" + FirstWaybillItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(FirstWaybillItem.getSkuNo())) {
				sb.append(" and sku_no = '" + FirstWaybillItem.getSkuNo() + "' ");
			}
			if (StringUtil.isNotNull(FirstWaybillItem.getSpecification())) {
				sb.append(" and specification = '" + FirstWaybillItem.getSpecification() + "' ");
			}
			if (StringUtil.isNotNull(FirstWaybillItem.getSkuName())) {
				sb.append(" and sku_name = '" + FirstWaybillItem.getSkuName() + "' ");
			}
			if (StringUtil.isNotNull(FirstWaybillItem.getSkuPriceCurrency())) {
				sb.append(" and sku_price_currency = '" + FirstWaybillItem.getSkuPriceCurrency() + "' ");
			}
			if (FirstWaybillItem.getSkuUnitPrice() != null) {
				sb.append(" and sku_price_currency = '" + FirstWaybillItem.getSkuUnitPrice() + "' ");
			}
			if (StringUtil.isNotNull(FirstWaybillItem.getRemark())) {
				sb.append(" and remark = '" + FirstWaybillItem.getRemark() + "' ");
			}
			if (FirstWaybillItem.getFirstWaybillId() != null) {
				sb.append(" and first_waybill_id = " + FirstWaybillItem.getFirstWaybillId());
			}
			if (FirstWaybillItem.getOrderId() != null) {
				sb.append(" and order_id = " + FirstWaybillItem.getOrderId());
			}
			if (FirstWaybillItem.getId() != null) {
				sb.append(" and id = '" + FirstWaybillItem.getId() + "' ");
			}
			if (FirstWaybillItem.getQuantity() != null) {
				sb.append(" and quantity = " + FirstWaybillItem.getQuantity());
			}
			if (FirstWaybillItem.getSkuNetWeight() != null) {
				sb.append(" and sku_net_weight = " + FirstWaybillItem.getSkuNetWeight());
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		List<FirstWaybillItem> FirstWaybillItemList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FirstWaybillItem.class));
		return FirstWaybillItemList;
	}
}