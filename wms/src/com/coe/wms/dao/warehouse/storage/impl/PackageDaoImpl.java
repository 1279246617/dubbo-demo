package com.coe.wms.dao.warehouse.storage.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.warehouse.storage.IPackageDao;
import com.coe.wms.model.user.User;

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
	public long savePackage(final Package p) {
//		final String sql = "insert into u_user (parent_id,login_name,password,user_name,user_type,phone,email,created_time,status) values (?,?,?,?,?,?,?,?,?)";
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		jdbcTemplate.update(new PreparedStatementCreator() {
//			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
//				PreparedStatement ps = conn.prepareStatement(sql);
//				ps.setLong(1, user.getParentId());
//				ps.setString(2, user.getLoginName());
//				ps.setString(3, user.getPassword());
//				ps.setString(4, user.getUserName());
//				ps.setString(5, user.getUserType());
//				ps.setString(6, user.getPhone());
//				ps.setString(7, user.getEmail());
//				ps.setLong(8, user.getCreatedTime());
//				ps.setInt(9, user.getStatus());
//				return ps;
//			}
//		}, keyHolder);
//		long id = keyHolder.getKey().longValue();
//		logger.debug("保存用户:" + sql + " 返回主键:" + id);
		return 0l;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}