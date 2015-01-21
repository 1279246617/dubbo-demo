package com.coe.wms.dao.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.api.IScannerVersionDao;
import com.coe.wms.dao.user.impl.UserDaoImpl;
import com.coe.wms.model.api.ScannerVersion;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("scannerVersionDao")
public class ScannerVersionDaoImpl implements IScannerVersionDao {

	Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	public ScannerVersion getLatestScannerVersion() {
		String sql = "select id,version,is_must_update,url from api_scanner_version ";
		List<ScannerVersion> scannerVersionList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(ScannerVersion.class));
		if (scannerVersionList != null && scannerVersionList.size() > 0) {
			return scannerVersionList.get(0);
		}
		return null;
	}
}