package com.coe.wms.facade.user.service.impl;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.coe.wms.facade.user.entity.User;
import com.coe.wms.facade.user.queryvo.UserQueryVo;
import com.coe.wms.facade.user.resultvo.UserResultVo;
import com.coe.wms.facade.user.service.UserFacade;
import com.coe.wms.service.user.biz.UserBiz;

@Service(version="1.1.1",retries=0)
public class UserServiceImpl implements UserFacade {
    @Autowired
    private UserBiz userBiz;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public User add(User record) {
        return userBiz.add(record);
    }

    public boolean delete(Long id) {
        return userBiz.delete(id);
    }

    public User update(User record) {
        return userBiz.update(record);
    }

    public User get(Long id) {
        return userBiz.get(id);
    }

    public Pager<User> list(int page, int limit) {
        return userBiz.list(page, limit);
    }

	@Override
	public Pager<UserResultVo> selectUserListByVo(UserQueryVo vo) {
		return userBiz.selectUserListByVo(vo);
	}

	
	/* (non-Javadoc)
	 * @see com.coe.wms.facade.user.service.UserFacade#listUserNotPage(com.coe.wms.facade.user.queryvo.UserQueryVo)
	 */
	@Override
	public List<UserResultVo> listUserNotPage(UserQueryVo vo) {
		return userBiz.listUserNotPage(vo);
	}

	@Override
	public UserResultVo getUserByUserCode(String userCode) {
		
		return userBiz.getUserByUserCode(userCode);
	}

	@Override
	public List<String> getAllUserCode() {
		
		return userBiz.getAllUserCode();
	}
}