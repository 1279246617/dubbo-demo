package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.util.Pagination;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ItemInventory> findItemInventory(ItemInventory itemInventory, Pagination page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addItemInventory(final Long wareHouseId, final Long userIdOfCustomer, final String batchNo, final String sku,
			final Integer addQuantity) {
		// 查询库存记录是否已存在
		String sql = "select id from w_s_item_inventory where user_id_of_customer = ? and warehouse_id = ? and sku = ? and batch_no =?";
		List<Long> idList = jdbcTemplate.queryForList(sql, Long.class, userIdOfCustomer, wareHouseId, sku, batchNo);
		if (idList.size() > 0) {
			Long id = idList.get(0);
			logger.info("已存在库存记录id:" + id);
			// 更新已有库存记录
			sql = "update w_s_item_inventory set quantity = quantity+" + addQuantity + " ,available_quantity = available_quantity+"
					+ addQuantity + " where id = " + id;
			return jdbcTemplate.update(sql);
		}
		// 插入新库存记录
		final String newSql = "insert into w_s_item_inventory (user_id_of_customer,warehouse_id,sku,batch_no,quantity,available_quantity) values (?,?,?,?,?,?)";
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
				return ps;
			}
		}, keyHolder);
		Long id = keyHolder.getKey().longValue();
		logger.info("新建库存记录id:" + id);
		return 1;
	}
}
