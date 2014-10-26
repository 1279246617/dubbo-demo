package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
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
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 */
@Repository("inWarehouseRecordItemDao")
public class InWarehouseRecordItemDaoImpl implements IInWarehouseRecordItemDao {

	Logger logger = Logger.getLogger(InWarehouseRecordItemDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	/**
	 * 保存单个物品
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveInWarehouseRecordItem(final InWarehouseRecordItem item) {
		final String sql = "insert into w_s_in_warehouse_record_item (in_warehouse_record_id,quantity,sku,remark,created_time,user_id_of_operator) values (?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, item.getInWarehouseRecordId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getRemark());
				ps.setLong(5, item.getCreatedTime());
				ps.setLong(6, item.getUserIdOfOperator());
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
	public int saveBatchInWarehouseRecordItem(final List<InWarehouseRecordItem> itemList) {
		final String sql = "insert into w_s_in_warehouse_record_item (in_warehouse_record_id,quantity,sku,remark,created_time,user_id_of_operator) values (?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InWarehouseRecordItem item = itemList.get(i);
				ps.setLong(1, item.getInWarehouseRecordId());
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getRemark());
				ps.setLong(5, item.getCreatedTime());
				ps.setLong(6, item.getUserIdOfOperator());
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
	public int saveBatchInWarehouseRecordItemWithRecordId(final List<InWarehouseRecordItem> itemList, final Long recordId) {
		final String sql = "insert into w_s_in_warehouse_record_item (in_warehouse_record_id,quantity,sku,remark,created_time,user_id_of_operator) values (?,?,?,?,?,?)";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				InWarehouseRecordItem item = itemList.get(i);
				ps.setLong(1, recordId);
				ps.setLong(2, item.getQuantity());
				ps.setString(3, item.getSku());
				ps.setString(4, item.getRemark());
				ps.setLong(5, item.getCreatedTime());
				ps.setLong(6, item.getUserIdOfOperator());
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
	 * 查询入库记录
	 * 
	 * 参数一律使用实体类加Map . 节省QueryVO
	 */
	@Override
	public List<InWarehouseRecordItem> findInWarehouseRecordItem(InWarehouseRecordItem inWarehouseRecordItem,
			Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,in_warehouse_record_id,quantity,sku,remark,created_time,user_id_of_operator from w_s_in_warehouse_record_item where 1=1 ");
		if (inWarehouseRecordItem != null) {
			if (StringUtil.isNotNull(inWarehouseRecordItem.getSku())) {
				sb.append(" and sku = '" + inWarehouseRecordItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseRecordItem.getRemark())) {
				sb.append(" and remark = '" + inWarehouseRecordItem.getRemark() + "' ");
			}
			if (inWarehouseRecordItem.getInWarehouseRecordId() != null) {
				sb.append(" and in_warehouse_record_id = '" + inWarehouseRecordItem.getInWarehouseRecordId() + "' ");
			}
			if (inWarehouseRecordItem.getQuantity() != null) {
				sb.append(" and quantity = " + inWarehouseRecordItem.getQuantity());
			}
			if (inWarehouseRecordItem.getCreatedTime() != null) {
				sb.append(" and created_time = " + inWarehouseRecordItem.getCreatedTime());
			}
			if (inWarehouseRecordItem.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + inWarehouseRecordItem.getUserIdOfOperator());
			}
			if (inWarehouseRecordItem.getId() != null) {
				sb.append(" and id = " + inWarehouseRecordItem.getId());
			}
		}
		if (moreParam != null) {
			if (moreParam.get("createdTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (moreParam.get("createdTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time <= " + date.getTime());
				}
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.info("查询入库记录明细sql:" + sql);
		List<InWarehouseRecordItem> inWarehouseRecordItemList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(InWarehouseRecordItem.class));
		logger.info("查询入库记录明细sql:" + sql + " size:" + inWarehouseRecordItemList.size());
		return inWarehouseRecordItemList;
	}

	/**
	 * 查询入库明细记录
	 */
	@Override
	public Long countInWarehouseRecordItem(InWarehouseRecordItem inWarehouseRecordItem, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(id) from w_s_in_warehouse_record_item where 1=1 ");
		if (inWarehouseRecordItem != null) {
			if (StringUtil.isNotNull(inWarehouseRecordItem.getSku())) {
				sb.append(" and sku = '" + inWarehouseRecordItem.getSku() + "' ");
			}
			if (StringUtil.isNotNull(inWarehouseRecordItem.getRemark())) {
				sb.append(" and remark = '" + inWarehouseRecordItem.getRemark() + "' ");
			}
			if (inWarehouseRecordItem.getInWarehouseRecordId() != null) {
				sb.append(" and in_warehouse_record_id = '" + inWarehouseRecordItem.getInWarehouseRecordId() + "' ");
			}
			if (inWarehouseRecordItem.getQuantity() != null) {
				sb.append(" and quantity = " + inWarehouseRecordItem.getQuantity());
			}
			if (inWarehouseRecordItem.getCreatedTime() != null) {
				sb.append(" and created_time = " + inWarehouseRecordItem.getCreatedTime());
			}
			if (inWarehouseRecordItem.getUserIdOfOperator() != null) {
				sb.append(" and user_id_of_operator = " + inWarehouseRecordItem.getUserIdOfOperator());
			}
			if (inWarehouseRecordItem.getId() != null) {
				sb.append(" and id = " + inWarehouseRecordItem.getId());
			}
		}
		if (moreParam != null) {
			if (moreParam.get("createdTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time >= " + date.getTime());
				}
			}
			if (moreParam.get("createdTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("createdTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and created_time <= " + date.getTime());
				}
			}
		}
		String sql = sb.toString();
		logger.info("统计入库明细记录sql:" + sql);
		return jdbcTemplate.queryForObject(sql, Long.class);
	}

	@Override
	public int updateInWarehouseRecordItemReceivedQuantity(Long recordItemId, int newQuantity) {
		String sql = "update w_s_in_warehouse_record_item set quantity=" + newQuantity + " where id=" + recordItemId;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int countInWarehouseItemSkuQuantityByOrderId(Long inWarehouseOrderId, String sku) {
		String sql = "select sum(quantity) from w_s_in_warehouse_record_item where sku = '" + sku
				+ "' and in_warehouse_record_id in(select id from w_s_in_warehouse_record where in_warehouse_order_id = "
				+ inWarehouseOrderId + ")";
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	@Override
	public int countInWarehouseItemSkuQuantityByRecordId(Long inWarehouseRecordId, String sku) {
		String sql = "select sum(quantity) from w_s_in_warehouse_record_item where sku = '" + sku + "' and in_warehouse_record_id = "
				+ inWarehouseRecordId;
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}
}