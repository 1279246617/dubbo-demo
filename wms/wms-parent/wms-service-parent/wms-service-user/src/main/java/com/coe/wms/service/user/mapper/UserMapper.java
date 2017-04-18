package com.coe.wms.service.user.mapper;

import java.util.List;

import com.coe.wms.facade.user.criteria.UserCriteria;
import com.coe.wms.facade.user.entity.User;
import com.coe.wms.facade.user.queryvo.UserQueryVo;
import com.coe.wms.facade.user.resultvo.UserResultVo;

public interface UserMapper {
    int deleteByCondition(UserCriteria condition);

    int deleteByPrimaryKey(Long id);

    int insertSelective(User record);

    List<User> selectByConditionList(UserCriteria condition);

    User selectByPrimaryKey(Long id);

    int countByCondition(UserCriteria condition);

    int updateByPrimaryKeySelective(User record);

	List<UserResultVo> selectByVoList(UserQueryVo userVo);

	/**
	 * 查询用户组下的所有用户，不分页
	 * @param vo
	 * @return
	 */
	List<UserResultVo> listUserNotPage(UserQueryVo vo);
	
	UserResultVo getUserByUserCode(String userCode);
	
	List<String> getAllUserCode();
}