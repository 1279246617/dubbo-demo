package com.coe.wms.dao.warehouse.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;

@Repository("warehouseDao")
public class WarehouseDaoImpl implements IWarehouseDao {

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	private Logger logger = Logger.getLogger(WarehouseDaoImpl.class);

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.WAREHOUSE, expiration = 36000)
	public Warehouse getWarehouseById(@ParameterValueKeyProvider Long warehouseId) {
		String sql = "select id,length_unit_code,meters,width,height,length,address_line2,address_line1,postal_code,city,state_or_province,country_name,country_code,warehouse_name,warehouse_no,remark,created_time,created_by_user_id,last_modifie_by_user_id,last_modifie_time from w_w_warehouse where id = ?";
		Warehouse warehouse = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Warehouse>(Warehouse.class), warehouseId);
		logger.debug("查询仓库:" + sql + " 参数:主键:" + warehouseId);
		return warehouse;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public Warehouse getWarehouseByNo(String warehouseNo) {
		String sql = "select id,length_unit_code,meters,width,height,length,address_line2,address_line1,postal_code,city,state_or_province,country_name,country_code,warehouse_name,warehouse_no,remark,created_time,created_by_user_id,last_modifie_by_user_id,last_modifie_time from w_w_warehouse where warehouse_no = '"+warehouseNo+"'";
		logger.debug("查询仓库:" + sql + " 参数:编号:" + warehouseNo);
		List<Warehouse> warehouseList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Warehouse.class));
		if (warehouseList.size() > 0) {
			return warehouseList.get(0);
		}
		return null;
	}
}
