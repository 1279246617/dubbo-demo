package com.coe.wms.dao.warehouse.storage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IReportTypeDao;
import com.coe.wms.model.warehouse.report.ReportType;
import com.coe.wms.util.SsmNameSpace;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;

/**
 * 
 * 报表类型 DAO
 * 
 * @author Administrator
 * 
 */
@Repository("reportTypeDao")
public class ReportTypeDaoImpl implements IReportTypeDao {

	Logger logger = Logger.getLogger(ReportTypeDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.REPORT_TYPE, expiration = 36000)
	public ReportType findReportTypeByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_w_report_type where code ='" + code + "'";
		ReportType packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<ReportType>(ReportType.class));
		return packageStatus;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllReportType", namespace = SsmNameSpace.REPORT_TYPE, expiration = 3600)
	public List<ReportType> findAllReportType() {
		String sql = "select id,code,cn,en from w_w_report_type";
		List<ReportType> statusList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ReportType.class));
		return statusList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}