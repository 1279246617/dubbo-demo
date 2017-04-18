package com.coe.wms.service.user.biz;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coe.wms.facade.user.criteria.UserCriteria;
import com.coe.wms.facade.user.criteria.UserCriteria.Criteria;
import com.coe.wms.facade.user.entity.User;
import com.coe.wms.facade.user.queryvo.UserQueryVo;
import com.coe.wms.facade.user.resultvo.UserResultVo;
import com.coe.wms.service.user.mapper.UserMapper;

/**
 * 用户逻辑类
 * 
 * @ClassName: UserBiz
 * @author yechao
 * @date 2017年3月6日 下午5:37:20
 * @Description: TODO
 */
@Component
public class UserBiz {

	@Autowired
	private UserMapper userMapper;

	private static final Logger logger = LoggerFactory.getLogger(UserBiz.class);

	public User add(User record) {
		if (this.userMapper.insertSelective(record) == 1)
			return record;
		return null;
	}

	public boolean delete(Long id) {
		return this.userMapper.deleteByPrimaryKey(id) == 1;
	}

	public User update(User record) {
		if (this.userMapper.updateByPrimaryKeySelective(record) == 1)
			return record;
		return null;
	}

	public User get(Long id) {
		return this.userMapper.selectByPrimaryKey(id);
	}

	public Pager<User> list(int page, int limit) {
		UserCriteria criteria = new UserCriteria();
		criteria.setPage(page);
		criteria.setLimit(limit);
		Criteria cri = criteria.createCriteria();
		List<User> list = userMapper.selectByConditionList(criteria);
		return PagerUtil.getPager(list, criteria);
	}
	
	public Pager<UserResultVo> selectUserListByVo(UserQueryVo userVo) {
        List<UserResultVo> list = userMapper.selectByVoList(userVo);
        return PagerUtil.getPager(list, userVo);
	}

	/**
	 * 查询符合条件的用户，不分页
	 * @param vo
	 * @return
	 */
	public List<UserResultVo> listUserNotPage(UserQueryVo vo) {
		return userMapper.listUserNotPage(vo);
	}
	
	/**
	 * 根据代码查询对账员、销售员、维护员
	 * @param userCode
	 * @return
	 */
	public UserResultVo getUserByUserCode(String userCode) {
		return userMapper.getUserByUserCode(userCode);
		
	}
	/**
	 * 查询所有对账员、销售员、维护员代码，即user_code
	 * @return
	 */
	public List<String> getAllUserCode(){
		
		return userMapper.getAllUserCode();
	}
}
