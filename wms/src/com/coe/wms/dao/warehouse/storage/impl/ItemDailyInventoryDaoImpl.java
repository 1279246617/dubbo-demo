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

import com.coe.wms.dao.warehouse.storage.IItemDailyInventoryDao;
import com.coe.wms.model.warehouse.storage.record.ItemDailyInventory;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;
import com.mysql.jdbc.Statement;

@Repository("itemDailyInventoryDao")
public class ItemDailyInventoryDaoImpl implements IItemDailyInventoryDao {
	Logger logger = Logger.getLogger(ItemDailyInventoryDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public long saveItemDailyInventory(final ItemDailyInventory itemDailyInventory) {
		// 插入新库存记录
		final String newSql = "insert into w_s_item_daily_inventory (user_id_of_customer,warehouse_id,sku,quantity,available_quantity,inventory_date,created_time) values (?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(newSql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, itemDailyInventory.getUserIdOfCustomer());
				ps.setLong(2, itemDailyInventory.getWarehouseId());
				ps.setString(3, itemDailyInventory.getSku());
				ps.setLong(4, itemDailyInventory.getQuantity());
				ps.setLong(5, itemDailyInventory.getAvailableQuantity());
				ps.setString(6, itemDailyInventory.getInventoryDate());
				ps.setLong(7, System.currentTimeMillis());
				return ps;
			}
		}, keyHolder);
		Long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	public List<ItemDailyInventory> findItemDailyInventory(ItemDailyInventory ItemDailyInventory, Map<String, String> moreParam, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,user_id_of_customer,warehouse_id,sku,quantity,available_quantity,inventory_date,created_time from w_s_item_daily_inventory where 1=1 ");
		if (ItemDailyInventory != null) {
			if (StringUtil.isNotNull(ItemDailyInventory.getSku())) {
				sb.append(" and sku = '" + ItemDailyInventory.getSku() + "' ");
			}
			if (ItemDailyInventory.getQuantity() != null) {
				sb.append(" and quantity = " + ItemDailyInventory.getQuantity());
			}
			if (ItemDailyInventory.getAvailableQuantity() != null) {
				sb.append(" and available_quantity = " + ItemDailyInventory.getAvailableQuantity());
			}
			if (ItemDailyInventory.getInventoryDate() != null) {
				sb.append(" and inventory_date = '" + ItemDailyInventory.getInventoryDate() + "'");
			}
			if (ItemDailyInventory.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + ItemDailyInventory.getUserIdOfCustomer());
			}
			if (ItemDailyInventory.getId() != null) {
				sb.append(" and id = " + ItemDailyInventory.getId());
			}
			if (ItemDailyInventory.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + ItemDailyInventory.getWarehouseId());
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
		List<ItemDailyInventory> ItemDailyInventoryList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ItemDailyInventory.class));
		return ItemDailyInventoryList;
	}

	@Override
	public Long countItemDailyInventory(ItemDailyInventory ItemDailyInventory, Map<String, String> moreParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_s_item_daily_inventory where 1=1 ");
		if (ItemDailyInventory != null) {
			if (StringUtil.isNotNull(ItemDailyInventory.getSku())) {
				sb.append(" and sku = '" + ItemDailyInventory.getSku() + "' ");
			}
			if (ItemDailyInventory.getQuantity() != null) {
				sb.append(" and quantity = " + ItemDailyInventory.getQuantity());
			}
			if (ItemDailyInventory.getAvailableQuantity() != null) {
				sb.append(" and available_quantity = " + ItemDailyInventory.getAvailableQuantity());
			}
			if (ItemDailyInventory.getInventoryDate() != null) {
				sb.append(" and inventory_date = '" + ItemDailyInventory.getInventoryDate() + "'");
			}
			if (ItemDailyInventory.getUserIdOfCustomer() != null) {
				sb.append(" and user_id_of_customer = " + ItemDailyInventory.getUserIdOfCustomer());
			}
			if (ItemDailyInventory.getId() != null) {
				sb.append(" and id = " + ItemDailyInventory.getId());
			}
			if (ItemDailyInventory.getWarehouseId() != null) {
				sb.append(" and warehouse_id = " + ItemDailyInventory.getWarehouseId());
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
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}
}
