package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IPackageDao;
import com.coe.wms.model.warehouse.storage.PackageStatus;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.mysql.jdbc.Statement;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("packageDao")
public class PackageDaoImpl implements IPackageDao {

	Logger logger = Logger.getLogger(PackageDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long savePackage(final com.coe.wms.model.warehouse.storage.Package pag) {
		final String sql = "insert into w_package (user_id,package_no,package_tracking_no,weight,small_package_quantity,created_time,remark) values (?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, pag.getUserId());
				ps.setString(2, pag.getPackageNo());
				ps.setString(3, pag.getPackageTrackingNo());
				ps.setDouble(4, pag.getWeight());
				ps.setInt(5, pag.getSmallPackageQuantity());
				ps.setLong(6, pag.getCreatedTime());
				ps.setString(7, pag.getRemark());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		return id;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public PackageStatus findPackageStatusByCode(@ParameterValueKeyProvider String code) {
		String sql = "select id,code,cn,en from w_package_status where code ='" + code + "'";
		PackageStatus packageStatus = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<PackageStatus>(
				PackageStatus.class));
		return packageStatus;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}