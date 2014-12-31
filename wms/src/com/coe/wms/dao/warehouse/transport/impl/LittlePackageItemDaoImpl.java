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
import com.coe.wms.dao.warehouse.transport.ILittlePackageItemDao;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("littlePackageItemDao")
public class LittlePackageItemDaoImpl implements ILittlePackageItemDao {

	Logger logger = Logger.getLogger(LittlePackageItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveLittlePackageItem(final FirstWaybillItem item) {
		final String sql = "insert into w_t_little_package_item (little_package_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,big_package_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getLittlePackageId());
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
				ps.setLong(11, item.getBigPackageId());
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
	public int saveBatchLittlePackageItem(final List<FirstWaybillItem> itemList) {
		final String sql = "insert into w_t_little_package_item (little_package_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,big_package_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				FirstWaybillItem item = itemList.get(i);
				ps.setLong(1, item.getLittlePackageId());
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
				ps.setLong(11, item.getBigPackageId());
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
	public int saveBatchLittlePackageItemWithLittlePackageId(final List<FirstWaybillItem> itemList, final Long littlePackageId) {
		final String sql = "insert into w_t_little_package_item (little_package_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,big_package_id) values (?,?,?,?,?,?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				FirstWaybillItem item = itemList.get(i);
				ps.setLong(1, littlePackageId);
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
				ps.setLong(11, item.getBigPackageId());
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
	public List<FirstWaybillItem> findLittlePackageItem(FirstWaybillItem littlePackageItem, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,little_package_id,quantity,sku,sku_name,sku_unit_price,sku_price_currency,remark,sku_net_weight,sku_no,specification,big_package_id from w_t_little_package_item where 1=1 ");
		if (littlePackageItem != null) {
			if (StringUtil.isNotNull(littlePackageItem.getSku())) {
				sb.append(" and sku = '" + littlePackageItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(littlePackageItem.getSkuNo())) {
				sb.append(" and sku_no = '" + littlePackageItem.getSkuNo() + "' ");
			}
			if (StringUtil.isNotNull(littlePackageItem.getSpecification())) {
				sb.append(" and specification = '" + littlePackageItem.getSpecification() + "' ");
			}
			if (StringUtil.isNotNull(littlePackageItem.getSkuName())) {
				sb.append(" and sku_name = '" + littlePackageItem.getSkuName() + "' ");
			}
			if (StringUtil.isNotNull(littlePackageItem.getSkuPriceCurrency())) {
				sb.append(" and sku_price_currency = '" + littlePackageItem.getSkuPriceCurrency() + "' ");
			}
			if (littlePackageItem.getSkuUnitPrice() != null) {
				sb.append(" and sku_price_currency = '" + littlePackageItem.getSkuUnitPrice() + "' ");
			}
			if (StringUtil.isNotNull(littlePackageItem.getRemark())) {
				sb.append(" and remark = '" + littlePackageItem.getRemark() + "' ");
			}
			if (littlePackageItem.getLittlePackageId() != null) {
				sb.append(" and little_package_id = " + littlePackageItem.getLittlePackageId());
			}
			if (littlePackageItem.getBigPackageId() != null) {
				sb.append(" and big_package_id = " + littlePackageItem.getBigPackageId());
			}
			if (littlePackageItem.getId() != null) {
				sb.append(" and id = '" + littlePackageItem.getId() + "' ");
			}
			if (littlePackageItem.getQuantity() != null) {
				sb.append(" and quantity = " + littlePackageItem.getQuantity());
			}
			if (littlePackageItem.getSkuNetWeight() != null) {
				sb.append(" and sku_net_weight = " + littlePackageItem.getSkuNetWeight());
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		List<FirstWaybillItem> littlePackageItemList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FirstWaybillItem.class));
		return littlePackageItemList;
	}
}