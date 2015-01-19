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

import com.coe.wms.dao.datasource.DataSource;
import com.coe.wms.dao.datasource.DataSourceCode;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.model.user.Index;
import com.coe.wms.model.user.User;
import com.coe.wms.util.SsmNameSpace;
import com.coe.wms.util.StringUtil;
import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterDataUpdateContent;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughAssignCache;
import com.google.code.ssm.api.ReadThroughSingleCache;
import com.google.code.ssm.api.UpdateSingleCache;
import com.mysql.jdbc.Statement;

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
		final String sql = "insert into u_user (parent_id,login_name,password,user_name,user_type,phone,email,created_time,status,secret_key,token,opposite_secret_key,opposite_token,opposite_service_url,default_warehouse_id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, user.getParentId());
				ps.setString(2, user.getLoginName());
				ps.setString(3, user.getPassword());
				ps.setString(4, user.getUserName());
				ps.setString(5, user.getUserType());
				ps.setString(6, user.getPhone());
				ps.setString(7, user.getEmail());
				ps.setLong(8, user.getCreatedTime());
				ps.setInt(9, user.getStatus());
				ps.setString(10, user.getSecretKey());
				ps.setString(11, user.getToken());
				ps.setString(12, user.getOppositeSecretKey());
				ps.setString(13, user.getOppositeToken());
				ps.setString(14, user.getOppositeServiceUrl());
				ps.setLong(15, user.getDefaultWarehouseId());
				return ps;
			}
		}, keyHolder);
		long id = keyHolder.getKey().longValue();
		logger.debug("保存用户:" + sql + " 返回主键:" + id);
		return id;
	}

	@Override
	public Long findUserIdByLoginName(String loginName) {
		String sql = "select id from u_user where login_name=?";
		List<Long> idList = jdbcTemplate.queryForList(sql, Long.class, loginName);
		if (idList.size() > 0) {
			return idList.get(0);
		}
		return null;
	}

	/**
	 * 根据用户id查询用户. 如果查询不到结果会抛异常
	 */
	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughSingleCache(namespace = SsmNameSpace.USER, expiration = 3600)
	public User getUserById(@ParameterValueKeyProvider Long userId) {
		String sql = "select id,parent_id,login_name,password,user_name,user_type,phone,email,created_time,status,secret_key,token,opposite_secret_key,opposite_token,opposite_service_url,default_warehouse_id from u_user where id=" + userId;
		User user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class));
		logger.debug("从数据库查询用户:" + sql + " 参数:主键:" + userId);
		return user;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public User findUserByLoginName(String loginName) {
		String sql = "select id,parent_id,login_name,password,user_name,user_type,status,secret_key,default_warehouse_id from u_user where login_name=?";
		List<User> userList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class), loginName);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public List<User> findUserByLikeLoginName(String loginName) {
		String sql = "select id,parent_id,login_name,user_name from u_user where login_name like '%" + loginName + "%'";
		List<User> userList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class));
		return userList;
	}

	@Override
	public User findUserByToken(String token) {
		String sql = "select id,parent_id,login_name,user_name,secret_key,token,opposite_secret_key,opposite_token,opposite_service_url,default_warehouse_id from u_user where token = ?";
		List<User> userList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class), token);
		if (userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	@ReadThroughAssignCache(assignedKey = "AllUser", namespace = SsmNameSpace.USER, expiration = 3600)
	public List<User> findAllUser() {
		String sql = "select id,parent_id,login_name,password,user_name,user_type,phone,email,created_time,status,secret_key,token,opposite_secret_key,opposite_token,opposite_service_url,default_warehouse_id from u_user ";
		List<User> userList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(User.class));
		return userList;
	}

	/**
	 * 当执行updateUser方法时，系统会更新缓存中userId对应的实体 将实体内容更新成@*DataUpdateContent标签所描述的实体
	 */
	@Override
	@UpdateSingleCache(namespace = SsmNameSpace.USER, expiration = 3600)
	@DataSource(DataSourceCode.WMS)
	public int updateUser(@ParameterValueKeyProvider @ParameterDataUpdateContent User user) {
		String sql = "update u_user set password =?,user_uame=?,phone=?,email=?,status=?,user_type=?,secret_key=?,token=?,opposite_secret_key=?,opposite_token=?,opposite_service_url=?,default_warehouse_id=? where id=? ";
		int count = jdbcTemplate.update(sql, user.getParentId(), user.getUserName(), user.getPhone(), user.getEmail(), user.getStatus(), user.getUserType(), user.getSecretKey(), user.getToken(), user.getOppositeSecretKey(), user.getOppositeToken(),
				user.getId(), user.getOppositeServiceUrl(), user.getDefaultWarehouseId());
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

	@Override
	@ReadThroughSingleCache(namespace = SsmNameSpace.INDEX, expiration = 36000)
	@DataSource(DataSourceCode.WMS)
	public Index findIndexByUserType(@ParameterValueKeyProvider String userType) {
		Index index = null;
		String sql = "select id,index_name,index_url,user_type  from u_index where user_type=?";
		List<Index> indexList = jdbcTemplate.query(sql, ParameterizedBeanPropertyRowMapper.newInstance(Index.class), userType);
		if (indexList.size() > 0) {
			index = indexList.get(0);
			logger.debug("userType :" + userType + " index:" + index.getIndexUrl());
			return index;
		}
		return index;
	}

	@Override
	@DataSource(DataSourceCode.WMS)
	public List<User> findUser(User user) {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,parent_id,login_name,user_name from u_user where 1=1");
		if (user.getId() != null) {
			sb.append(" and id = " + user.getId());
		}
		if (StringUtil.isNotNull(user.getLoginName())) {
			sb.append(" and login_name = '" + user.getLoginName() + "'");
		}
		if (StringUtil.isNotNull(user.getUserType())) {
			sb.append(" and user_type = '" + user.getUserType() + "'");
		}
		if (user.getStatus() != null) {
			sb.append(" and status = " + user.getStatus());
		}
		List<User> userList = jdbcTemplate.query(sb.toString(), ParameterizedBeanPropertyRowMapper.newInstance(User.class));
		return userList;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}