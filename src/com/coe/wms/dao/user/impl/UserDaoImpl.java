package com.coe.wms.dao.user.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coe.wms.dao.constants.SsmNameSpace;
import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.model.user.User;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;

/**
 * 
 * @author Administrator
 * 
 */
@Repository("userDao")
public class UserDaoImpl implements IUserDao {

	Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Resource(name = "jdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Override
	@DataSource(DataSourceCode.WMS)
	public long saveUser(final User user) {
		final String sql = "insert into u_user (parent_id,login_name,password,user_name,user_type,phone,email,created_time,status) values (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setLong(1, user.getParentId());
				ps.setString(2, user.getLoginName());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getUserName());
				ps.setString(5, user.getUserType());
				ps.setString(6, user.getPhone());
				ps.setString(7, user.getEmail());
				ps.setLong(8, user.getCreatedTime());
				ps.setInt(9, user.getStatus());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		logger.debug("保存用户:" + sql + " 返回主键:" + id);
		return id;
	}

	/**
	 * 当执行getById查询方法时，系统首先会从缓存中获取userId对应的实体 如果实体还没有被缓存，则执行查询方法并将查询结果放入缓存中
	 */
	@Override
	@ReadThroughSingleCache(namespace = SsmNameSpace.USER, expiration = 3600)
	@DataSource(DataSourceCode.WMS)
	public User getUserById(@ParameterValueKeyProvider Long userId) {
		String sql = "select id,parent_id,login_name,password,user_name,user_type,phone,email,created_time,status from u_user where id="
				+ userId;
		User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class));
		logger.debug("从数据库查询用户:" + sql + " 参数:主键:" + userId);
		return user;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public User findUserByLoginName(String loginName) {
		String sql = "select id,parent_id,login_name,password,user_name,user_type,status from u_user where login_name=?";
		List<User> userList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class),
				loginName);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	/**
	 * 当执行updateUser方法时，系统会更新缓存中userId对应的实体 将实体内容更新成@*DataUpdateContent标签所描述的实体
	 */
	@Override
	@UpdateSingleCache(namespace = SsmNameSpace.USER, expiration = 3600)
	@DataSource(DataSourceCode.WMS)
	public int updateUser(@ParameterValueKeyProvider @ParameterDataUpdateContent User user) {
		String sql = "update u_user set password =?,user_uame=?,phone=?,email=?,status=?,user_type=? where id=? ";
		int count = jdbcTemplate.update(sql, user.getParentId(), user.getUserName(), user.getPhone(), user.getEmail(),
				user.getStatus(), user.getUserType(), user.getId());
		logger.debug("更新用户:" + sql + " 影响行数:" + count);
		return count;
	}

	/**
	 * 当执行deleteUser方法时，系统会删除缓存中userId对应的实体
	 */
	@Override
	@InvalidateSingleCache(namespace = SsmNameSpace.USER)
	@DataSource(DataSourceCode.WMS)
	public int deleteUserById(@ParameterValueKeyProvider Long userId) {
		String sql = "delete u_user  where id=? ";
		int count = jdbcTemplate.update(sql, userId);
		logger.debug("删除用户:" + sql + " 参数:主键:" + userId + " 影响行数:" + count);
		return count;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}