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

import com.coe.wms.dao.warehouse.storage.IItemShelfInventoryDao;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.util.DateUtil;
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
	public List<ItemShelfInventory> findItemShelfInventory(ItemShelfInventory itemShelfInventory, Map<String, String> moreParam,
			Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id_of_customer,warehouse_id,quantity,sku,seat_code,available_quantity,last_update_time from w_s_item_shelf_inventory where 1=1 ");
		if (itemShelfInventory != null) {
			if (StringUtil.isNotNull(itemShelfInventory.getSku())) {
				sb.append(" and sku = '" + itemShelfInventory.getSku() + "' ");
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
		}
		// 分页sql
		if (page != null) {
			sb.append(page.generatePageSql());
		}
		String sql = sb.toString();
		logger.info("查询库存记录sql:" + sql);
		List<ItemShelfInventory> itemShelfInventoryList = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(ItemShelfInventory.class));
		logger.info("查询库存记录sql:" + sql + " size:" + itemShelfInventoryList.size());
		return itemShelfInventoryList;
	}

	@Override
	public int addItemShelfInventory(final Long warehouseId, final Long userIdOfCustomer, final String seatCode, final String sku,
			final Integer addQuantity) {
		// 查询库存记录是否已存在
		String sql = "select id from w_s_item_shelf_inventory where user_id_of_customer = ? and warehouse_id = ? and sku = ? and seat_code =?";
		List<Long> idList = jdbcTemplate.queryForList(sql, Long.class, userIdOfCustomer, warehouseId, sku, seatCode);
		if (idList.size() > 0) {
			Long id = idList.get(0);
			logger.info("已存在库存记录id:" + id);
			// 更新已有库存记录
			sql = "update w_s_item_shelf_inventory set quantity = quantity+" + addQuantity + " ,available_quantity = available_quantity+"
					+ addQuantity + " ,last_update_time = " + System.currentTimeMillis() + " where id = " + id;
			return jdbcTemplate.update(sql);
		}
		// 插入新库存记录
		final String newSql = "insert into w_s_item_shelf_inventory (user_id_of_customer,warehouse_id,sku,seat_code,quantity,available_quantity,last_update_time) values (?,?,?,?,?,?,?)";
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
				return ps;
			}
		}, keyHolder);
		Long id = keyHolder.getKey().longValue();
		logger.info("新建库存记录id:" + id);
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
		}
		String sql = sb.toString();
		logger.info("统计库存记录sql:" + sql);
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}
}
