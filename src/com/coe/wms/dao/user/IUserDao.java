package com.coe.wms.dao.user;

import com.coe.wms.model.user.Index;
import com.coe.wms.model.user.User;

public interface IUserDao {

	public long saveUser(User user);

	public User getUserById(Long userId);

	/**
	 * 供登录校验使用, 不会查询全部字段
	 * 
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName);

	public int updateUser(User user);

	public int deleteUserById(Long userId);

	/**
	 * 用户首页 根据用户类型查找用户登录后进入的首页
	 * 
	 * @param userType
	 * @return
	 */
	public Index findIndexByUserType(String userType);
}
