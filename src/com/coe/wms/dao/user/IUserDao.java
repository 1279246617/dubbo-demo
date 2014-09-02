package com.coe.wms.dao.user;

import com.coe.wms.model.user.User;

public interface IUserDao {

	public long saveUser(User user);

	public User getUserById(Long userId);

	public int updateUser(User user);

	public int deleteUserById(Long userId);
}
