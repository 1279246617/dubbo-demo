package com.coe.message.service.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coe.message.dao.mapper.LoginUserMapper;
import com.coe.message.entity.LoginUser;
import com.coe.message.entity.LoginUserExample;
import com.coe.message.entity.LoginUserExample.Criteria;
import com.coe.message.service.ILoginUserService;

/**登录用户接口实现类*/
@Service
public class LoginUserServiceImpl implements ILoginUserService {
	@Autowired
	private LoginUserMapper loginUserMapper;

	/**根据用户名和密码查询用户*/
	public List<LoginUser> getLoginUser(LoginUser loginUser) {
		LoginUserExample userExample = generateExample(loginUser);
		List<LoginUser> userList = loginUserMapper.selectByExample(userExample);
		return userList;
	}

	/**生成查询样例*/
	public LoginUserExample generateExample(LoginUser loginUser) {
		String userName = loginUser.getUserName();
		String password = loginUser.getPassword();
		Integer isValid = loginUser.getIsValid();
		String mobile = loginUser.getMobile();
		Integer sex = loginUser.getSex();
		Integer age = loginUser.getAge();
		String realName = loginUser.getRealName();
		String createUserName = loginUser.getCreateUserName();
		LoginUserExample userExample = new LoginUserExample();
		Criteria criteria = userExample.createCriteria();
		if (isValid != null) {
			criteria.andIsValidEqualTo(isValid);
		}
		if (sex != null) {
			criteria.andSexEqualTo(sex);
		}
		if(age!=null){
			criteria.andAgeEqualTo(age);
		}
		if(StringUtils.isNotBlank(userName)){
			criteria.andUserNameEqualTo(userName);
		}
		if(StringUtils.isNotBlank(password)){
			criteria.andPasswordEqualTo(password);
		}
		if(StringUtils.isNotBlank(mobile)){
			criteria.andMobileEqualTo(mobile);
		}
		if(StringUtils.isNotBlank(realName)){
			criteria.andRealNameEqualTo(realName);
		}
		if(StringUtils.isNotBlank(createUserName)){
			criteria.andCreateUserNameEqualTo(createUserName);
		}
		return userExample;
	}

}
