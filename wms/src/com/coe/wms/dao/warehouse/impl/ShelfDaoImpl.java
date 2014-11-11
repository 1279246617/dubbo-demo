package com.coe.wms.dao.warehouse.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.IShelfDao;
import com.coe.wms.model.warehouse.Shelf;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;

@Repository("shelfDao")
public class ShelfDaoImpl implements IShelfDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private Logger logger = Logger.getLogger(ShelfDaoImpl.class);

	@Override
	@DataSource(DataSourceCode.WMS)
	public Shelf getShelfByCode(Long code) {
		String sql = "select id,warehouse_id,shelf_code,shelf_code,remark from w_w_shelf where shelf_code = ?";
		List<Shelf> shelfList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Shelf.class));
		if (shelfList.size() > 0) {
			return shelfList.get(0);
		}
		return null;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public List<Shelf> findShelf(Shelf shelf, Pagination page) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,warehouse_id,shelf_code,shelf_code,remark from w_w_shelf where 1=1");
		if (shelf.getId() != null) {
			sb.append(" and id = " + shelf.getId());
		}
		if (StringUtil.isNotNull(shelf.getShelfCode())) {
			sb.append(" and shelf_code = '" + shelf.getShelfCode() + "'");
		}
		if (StringUtil.isNotNull(shelf.getShelfCode())) {
			sb.append(" and shelf_code = '" + shelf.getShelfCode() + "'");
		}
		if (StringUtil.isNotNull(shelf.getRemark())) {
			sb.append(" and remark = '" + shelf.getRemark() + "'");
		}
		if (shelf.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + shelf.getWarehouseId());
		}
		if (page != null) {
			// 分页sql
			sb.append(page.generatePageSql());
		}
		List<Shelf> warehouseList = jdbcTemplate.query(sb.toString(), ParameterizedBeanPropertyRowMapper.newInstance(Shelf.class));
		return warehouseList;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public Long countShelf(Shelf shelf) {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) from w_w_shelf where 1=1");
		if (shelf.getId() != null) {
			sb.append(" and id = " + shelf.getId());
		}
		if (StringUtil.isNotNull(shelf.getShelfCode())) {
			sb.append(" and shelf_code = '" + shelf.getShelfCode() + "'");
		}
		if (StringUtil.isNotNull(shelf.getShelfCode())) {
			sb.append(" and shelf_code = '" + shelf.getShelfCode() + "'");
		}
		if (StringUtil.isNotNull(shelf.getRemark())) {
			sb.append(" and remark = '" + shelf.getRemark() + "'");
		}
		if (shelf.getWarehouseId() != null) {
			sb.append(" and warehouse_id = " + shelf.getWarehouseId());
		}
		String sql = sb.toString();
		Long count = jdbcTemplate.queryForObject(sql, Long.class);
		if (count == null) {
			return 0l;
		}
		return count;
	}

	@Override
	public Integer addShelf(Shelf shelf) {
		// TODO Auto-generated method stub
		return null;
	}
}
