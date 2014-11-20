package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

@Repository("itemInventoryDao")
public class ItemInventoryDaoImpl implements IItemInventoryDao {
	Logger logger = Logger.getLogger(ItemInventoryDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public long saveItemInventory(ItemInventory itemInventory) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveBatchItemInventory(List<ItemInventory> itemInventoryList) {

		return 0;
	}

	@Override
	public List<ItemInventory> findItemInventory(ItemInventory itemInventory, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id_of_customer,warehouse_id,quantity,sku,batch_no,available_quantity,last_update_time,created_time from w_s_item_inventory where 1=1 ");
		if (itemInventory != null) {
			if (StringUtil.isNotNull(itemInventory.getSku())) {
				sb.append(" and sku = '" + itemInventory.getSku() + "' ");
			}
			if (StringUtil.isNotNull(itemInventory.getBatchNo())) {
				sb.append(" and batch_no = '" + itemInventory.getBatchNo() + "' ");
			}
			if (itemInventory.getQuantity() != null) {
				sb.append(" and quantity = " + itemInventory.getQuantity());
			}
			if (itemInventory.getAvailableQuantity() != null) {
				sb.append(" and available_quantity = " + itemInventory.getAvailableQuantity());
			}
			if (itemInventory.getLastUpdateTime() != null) {
				sb.append(" and last_update_time = " + itemInventory.getLastUpdateTime());
			}
			if (itemInventory.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + itemInventory.getUserIdOfCustomer());
			}
			if (itemInventory.getId() != null) {
				sb.append(" and id = " + itemInventory.getId());
			}
			if (itemInventory.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + itemInventory.getWarehouseId());
			}
		}
		if (moreParam != null) {
			if (moreParam.get("lastUpdateTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("lastUpdateTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and last_update_time >= " + date.getTime());
				}
			}
			if (moreParam.get("lastUpdateTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("lastUpdateTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and last_update_time <= " + date.getTime());
				}
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.info("查询库存记录sql:" + sql);
		List<ItemInventory> itemInventoryList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ItemInventory.class));
		logger.info("查询库存记录sql:" + sql + " size:" + itemInventoryList.size());
		return itemInventoryList;
	}

	@Override
	public int addItemInventory(final Long wareHouseId, final Long userIdOfCustomer, final String batchNo, final String sku, final Integer addQuantity) {
		// 查询库存记录是否已存在
		String sql = "select id from w_s_item_inventory where user_id_of_customer = ? and warehouse_id = ? and sku = ? and batch_no =?";
		List<Long> idList = jdbcTemplate.queryForList(sql, Long.class, userIdOfCustomer, wareHouseId, sku, batchNo);
		if (idList.size() > 0) {
			Long id = idList.get(0);
			logger.info("已存在库存记录id:" + id);
			// 更新已有库存记录
			sql = "update w_s_item_inventory set quantity = quantity+" + addQuantity + " ,available_quantity = available_quantity+" + addQuantity + " ,last_update_time = " + System.currentTimeMillis() + " where id = " + id;
			return jdbcTemplate.update(sql);
		}
		// 插入新库存记录
		final String newSql = "insert into w_s_item_inventory (user_id_of_customer,warehouse_id,sku,batch_no,quantity,available_quantity,last_update_time,created_time) values (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(newSql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, userIdOfCustomer);
				ps.setLong(2, wareHouseId);
				ps.setString(3, sku);
				ps.setString(4, batchNo);
				ps.setLong(5, addQuantity);
				ps.setLong(6, addQuantity);
				ps.setLong(7, System.currentTimeMillis());
				ps.setLong(8, System.currentTimeMillis());
				return ps;
			}
		}, keyHolder);
		Long id = keyHolder.getKey().longValue();
		logger.info("新建库存记录id:" + id);
		return 1;
	}

	@Override
	public Long countItemInventory(ItemInventory itemInventory, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_s_item_inventory where 1=1 ");
		if (itemInventory != null) {
			if (StringUtil.isNotNull(itemInventory.getSku())) {
				sb.append(" and sku = '" + itemInventory.getSku() + "' ");
			}
			if (StringUtil.isNotNull(itemInventory.getBatchNo())) {
				sb.append(" and batch_no = '" + itemInventory.getBatchNo() + "' ");
			}
			if (itemInventory.getQuantity() != null) {
				sb.append(" and quantity = " + itemInventory.getQuantity());
			}
			if (itemInventory.getAvailableQuantity() != null) {
				sb.append(" and available_quantity = " + itemInventory.getAvailableQuantity());
			}
			if (itemInventory.getLastUpdateTime() != null) {
				sb.append(" and last_update_time = " + itemInventory.getLastUpdateTime());
			}
			if (itemInventory.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + itemInventory.getUserIdOfCustomer());
			}
			if (itemInventory.getId() != null) {
				sb.append(" and id = " + itemInventory.getId());
			}
			if (itemInventory.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + itemInventory.getWarehouseId());
			}
		}
		if (moreParam != null) {
			if (moreParam.get("lastUpdateTimeStart") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("lastUpdateTimeStart"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and last_update_time >= " + date.getTime());
				}
			}
			if (moreParam.get("lastUpdateTimeEnd") != null) {
				Date date = DateUtil.stringConvertDate(moreParam.get("lastUpdateTimeEnd"), DateUtil.yyyy_MM_ddHHmmss);
				if (date != null) {
					sb.append(" and last_update_time <= " + date.getTime());
				}
			}
		}
		String sql = sb.toString();
		logger.info("统计库存记录sql:" + sql);
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}

	@Override
	public int updateItemInventoryQuantity(Long id, Integer quantity) {
		String sql = "update w_s_item_inventory set quantity=" + quantity + " where id=" + id;
		return jdbcTemplate.update(sql);
	}
}
