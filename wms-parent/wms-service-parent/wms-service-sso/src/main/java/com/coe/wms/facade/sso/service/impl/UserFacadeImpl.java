/**    
* @Title: UserFacadeImpl.java
* @Package com.coe.wms.facade.sso.service.impl
* @author yechao
* @date 2017年2月3日 下午11:17:25
* @version V1.0
* @Description: TODO   
*/
package com.coe.wms.facade.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.sso.service.UserFacade;
import com.coe.wms.service.sso.biz.UserBiz;

/**
 * @ClassName: UserFacadeImpl
 * @author yechao
 * @date 2017年2月3日 下午11:17:25
 * @Description: TODO
 */
@Service
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserBiz userBiz;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.coe.wms.facade.sso.service.UserFacade#create()
	 */
	@Override
	public void create() {
		userBiz.create();
	}
}
