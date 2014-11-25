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

import com.coe.wms.dao.warehouse.storage.IItemShelfInventoryDao;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.NumberUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

@Repository("itemShelfInventoryDao")
public class ItemShelfInventoryDaoImpl implements IItemShelfInventoryDao {

	Logger logger = Logger.getLogger(ItemShelfInventoryDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public long saveItemShelfInventory(ItemShelfInventory itemShelfInventory) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveBatchItemShelfInventory(List<ItemShelfInventory> itemShelfInventoryList) {

		return 0;
	}

	@Override
	public List<ItemShelfInventory> findItemShelfInventory(ItemShelfInventory itemShelfInventory, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id_of_customer,warehouse_id,quantity,sku,seat_code,available_quantity,last_update_time,batch_no,created_time from w_s_item_shelf_inventory where 1=1 ");
		if (itemShelfInventory != null) {
			if (StringUtil.isNotNull(itemShelfInventory.getSku())) {
				sb.append(" and sku = '" + itemShelfInventory.getSku() + "' ");
			}
			if (StringUtil.isNotNull(itemShelfInventory.getBatchNo())) {
				sb.append(" and batch_no = '" + itemShelfInventory.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(itemShelfInventory.getSeatCode())) {
				sb.append(" and seat_code = '" + itemShelfInventory.getSeatCode() + "' ");
			}
			if (itemShelfInventory.getQuantity() != null) {
				sb.append(" and quantity = " + itemShelfInventory.getQuantity());
			}
			if (itemShelfInventory.getAvailableQuantity() != null) {
				sb.append(" and available_quantity = " + itemShelfInventory.getAvailableQuantity());
			}
			if (itemShelfInventory.getLastUpdateTime() != null) {
				sb.append(" and last_update_time = " + itemShelfInventory.getLastUpdateTime());
			}
			if (itemShelfInventory.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + itemShelfInventory.getUserIdOfCustomer());
			}
			if (itemShelfInventory.getId() != null) {
				sb.append(" and id = " + itemShelfInventory.getId());
			}
			if (itemShelfInventory.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + itemShelfInventory.getWarehouseId());
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
			if (moreParam.get("minQuantity") != null) {
				sb.append(" and quantity >= " + Integer.valueOf(moreParam.get("minQuantity")));
			}
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.debug("查询库存记录sql:" + sql);
		List<ItemShelfInventory> itemShelfInventoryList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ItemShelfInventory.class));
		logger.debug("查询库存记录sql:" + sql + " size:" + itemShelfInventoryList.size());
		return itemShelfInventoryList;
	}

	@Override
	public int addItemShelfInventory(final Long warehouseId, final Long userIdOfCustomer, final String seatCode, final String sku, final Integer addQuantity, final String batchNo) {
		// 查询库存记录是否已存在
		String sql = "select id from w_s_item_shelf_inventory where user_id_of_customer = ? and warehouse_id = ? and sku = ? and seat_code =? and batch_no = ?";
		List<Long> idList = jdbcTemplate.queryForList(sql, Long.class, userIdOfCustomer, warehouseId, sku, seatCode, batchNo);
		if (idList.size() > 0) {
			Long id = idList.get(0);
			logger.debug("已存在库存记录id:" + id);
			// 更新已有库存记录
			sql = "update w_s_item_shelf_inventory set quantity = quantity+" + addQuantity + " ,available_quantity = available_quantity+" + addQuantity + " ,last_update_time = " + System.currentTimeMillis() + " where id = " + id;
			return jdbcTemplate.update(sql);
		}
		// 插入新库存记录
		final String newSql = "insert into w_s_item_shelf_inventory (user_id_of_customer,warehouse_id,sku,seat_code,quantity,available_quantity,last_update_time,batch_no,created_time) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(newSql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, userIdOfCustomer);
				ps.setLong(2, warehouseId);
				ps.setString(3, sku);
				ps.setString(4, seatCode);
				ps.setLong(5, addQuantity);
				ps.setLong(6, addQuantity);
				ps.setLong(7, System.currentTimeMillis());
				ps.setString(8, batchNo);
				ps.setLong(9, System.currentTimeMillis());
				return ps;
			}
		}, keyHolder);
		Long id = keyHolder.getKey().longValue();
		logger.debug("新建库存记录id:" + id);
		return 1;
	}

	@Override
	public Long countItemShelfInventory(ItemShelfInventory itemShelfInventory, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_s_item_shelf_inventory where 1=1 ");
		if (itemShelfInventory != null) {
			if (StringUtil.isNotNull(itemShelfInventory.getSku())) {
				sb.append(" and sku = '" + itemShelfInventory.getSku() + "' ");
			}
			if (StringUtil.isNotNull(itemShelfInventory.getBatchNo())) {
				sb.append(" and batch_no = '" + itemShelfInventory.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(itemShelfInventory.getSeatCode())) {
				sb.append(" and seat_code = '" + itemShelfInventory.getSeatCode() + "' ");
			}
			if (itemShelfInventory.getQuantity() != null) {
				sb.append(" and quantity = " + itemShelfInventory.getQuantity());
			}
			if (itemShelfInventory.getAvailableQuantity() != null) {
				sb.append(" and available_quantity = " + itemShelfInventory.getAvailableQuantity());
			}
			if (itemShelfInventory.getLastUpdateTime() != null) {
				sb.append(" and last_update_time = " + itemShelfInventory.getLastUpdateTime());
			}
			if (itemShelfInventory.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + itemShelfInventory.getUserIdOfCustomer());
			}
			if (itemShelfInventory.getId() != null) {
				sb.append(" and id = " + itemShelfInventory.getId());
			}
			if (itemShelfInventory.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + itemShelfInventory.getWarehouseId());
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
			if (moreParam.get("minQuantity") != null) {
				sb.append(" and quantity >= " + Integer.valueOf(moreParam.get("minQuantity")));
			}
		}
		String sql = sb.toString();
		logger.debug("统计库存记录sql:" + sql);
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}

	@Override
	public List<ItemShelfInventory> findItemShelfInventoryForPreOutShelf(Long userIdOfCustomer, Long warehouseId, String sku) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id_of_customer,warehouse_id,sku,seat_code,quantity,available_quantity,last_update_time,batch_no,created_time from w_s_item_shelf_inventory where quantity>0 and available_quantity >0  ");
		if (StringUtil.isNotNull(sku)) {
			sb.append(" and sku = '" + sku + "' ");
		}
		if (warehouseId != null) {
			sb.append(" and warehouse_id = " + warehouseId);
		}
		if (userIdOfCustomer != null) {
			sb.append(" and user_id_of_customer = " + userIdOfCustomer);
		}
		sb.append(" order by batch_no asc,created_time asc;");
		String sql = sb.toString();
		logger.debug("查询库位库存记录明细sql:" + sql);
		List<ItemShelfInventory> itemShelfInventoryList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ItemShelfInventory.class));
		logger.debug("查询库位库存记录明细sql:" + sql + " size:" + itemShelfInventoryList.size());
		return itemShelfInventoryList;
	}

	@Override
	public int updateItemShelfInventoryAvailableQuantity(Long id, Integer availableQuantity) {
		String sql = "update w_s_item_shelf_inventory set available_quantity=" + availableQuantity + " where id=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateItemShelfInventoryQuantity(Long id, Integer quantity) {
		String sql = "update w_s_item_shelf_inventory set quantity=" + quantity + " where id=" + id;
		return jdbcTemplate.update(sql);
	}

	@Override
	public int updateBatchItemShelfInventoryAvailableQuantity(final List<ItemShelfInventory> itemShelfInventoryList) {
		final String sql = "update w_s_item_shelf_inventory set available_quantity = ? where id = ?";
		int[] batchUpdateSize = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ItemShelfInventory item = itemShelfInventoryList.get(i);
				ps.setInt(1, item.getAvailableQuantity());
				ps.setLong(2, item.getId());
			}

			@Override
			public int getBatchSize() {
				return itemShelfInventoryList.size();
			}
		});
		return NumberUtil.sumArry(batchUpdateSize);
	}

	@Override
	public Long sumItemAvailableQuantity(ItemShelfInventory itemShelfInventory) {
		StringBuffer sb = new StringBuffer();
		sb.append("select sum(available_quantity) from w_s_item_shelf_inventory where 1=1 ");
		if (itemShelfInventory != null) {
			if (StringUtil.isNotNull(itemShelfInventory.getSku())) {
				sb.append(" and sku = '" + itemShelfInventory.getSku() + "' ");
			}
			if (StringUtil.isNotNull(itemShelfInventory.getBatchNo())) {
				sb.append(" and batch_no = '" + itemShelfInventory.getBatchNo() + "' ");
			}
			if (StringUtil.isNotNull(itemShelfInventory.getSeatCode())) {
				sb.append(" and seat_code = '" + itemShelfInventory.getSeatCode() + "' ");
			}
			if (itemShelfInventory.getQuantity() != null) {
				sb.append(" and quantity = " + itemShelfInventory.getQuantity());
			}
			if (itemShelfInventory.getAvailableQuantity() != null) {
				sb.append(" and available_quantity = " + itemShelfInventory.getAvailableQuantity());
			}
			if (itemShelfInventory.getLastUpdateTime() != null) {
				sb.append(" and last_update_time = " + itemShelfInventory.getLastUpdateTime());
			}
			if (itemShelfInventory.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + itemShelfInventory.getUserIdOfCustomer());
			}
			if (itemShelfInventory.getId() != null) {
				sb.append(" and id = " + itemShelfInventory.getId());
			}
			if (itemShelfInventory.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + itemShelfInventory.getWarehouseId());
			}
		}
		String sql = sb.toString();
		logger.debug("统计库位可用库存数量sql:" + sql);
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}
}
