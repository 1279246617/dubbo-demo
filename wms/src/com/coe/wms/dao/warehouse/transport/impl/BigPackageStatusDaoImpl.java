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
import com.coe.wms.dao.warehouse.transport.IBigPackageStatusDao;
import com.coe.wms.model.warehouse.transport.BigPackageStatus;
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
@Repository("bigPackageStatusDao")
public class BigPackageStatusDaoImpl implements IBigPackageStatusDao {

	Logger logger = Logger.getLogger(BigPackageStatusDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.TRANSPORT_BIG_PACKAGE_STATUS, expiration = 36000)
	public BigPackageStatus findBigPackageStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_t_big_package_status where code ='" + code + "'";
		BigPackageStatus packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<BigPackageStatus>(BigPackageStatus.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllBigPackageStatus", namespace = SsmNameSpace.TRANSPORT_BIG_PACKAGE_STATUS, expiration = 3600)
	public List<BigPackageStatus> findAllBigPackageStatus() {
		String sql = "select id,code,cn,en from w_t_big_package_status";
		List<BigPackageStatus> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(BigPackageStatus.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}