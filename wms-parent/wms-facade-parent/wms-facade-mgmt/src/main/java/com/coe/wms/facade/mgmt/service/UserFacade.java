package com.coe.wms.facade.mgmt.service;

import java.util.List;

import org.mybatis.plugin.model.Pager;

import com.coe.wms.facade.mgmt.entity.User;
import com.coe.wms.facade.mgmt.queryvo.UserQueryVo;
import com.coe.wms.facade.mgmt.resultvo.UserResultVo;

public interface UserFacade {
    User add(User record);

    boolean delete(Long id);

    User update(User record);

    User get(Long id);

    Pager<User> list(int page, int limit);

	Pager<UserResultVo> selectUserListByVo(UserQueryVo vo);

	/**
	 * 查询某个用户组下面的所有用户
	 * @param vo
	 * @return
	 */
	List<UserResultVo> listUserNotPage(UserQueryVo vo);
	
	/**
	 * 根据代码查询对账员、销售员、维护员
	 * @param userCode
	 * @return
	 */
	UserResultVo getUserByUserCode(String userCode);
	/**
	 * 查询所有对账员、销售员、维护员代码，即user_code
	 * @return
	 */
	List<String> getAllUserCode();
}