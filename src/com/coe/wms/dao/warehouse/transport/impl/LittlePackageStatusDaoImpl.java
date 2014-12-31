package com.coe.wms.dao.warehouse.transport.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.transport.ILittlePackageStatusDao;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 
 * 入库订单状态 DAO
 * 
 * @author Administrator
 * 
 */
@Repository("littlePackageStatusDao")
public class LittlePackageStatusDaoImpl implements ILittlePackageStatusDao {

	Logger logger = Logger.getLogger(LittlePackageStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.TRANSPORT_LITTLE_PACKAGE_STATUS, expiration = 36000)
	public FirstWaybillStatus findLittlePackageStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_t_little_package_status where code ='" + code + "'";
		FirstWaybillStatus packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<FirstWaybillStatus>(FirstWaybillStatus.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllLittlePackageStatus", namespace = SsmNameSpace.TRANSPORT_LITTLE_PACKAGE_STATUS, expiration = 3600)
	public List<FirstWaybillStatus> findAllLittlePackageStatus() {
		String sql = "select id,code,cn,en from w_t_little_package_status";
		List<FirstWaybillStatus> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(FirstWaybillStatus.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}