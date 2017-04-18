package com.coe.wms.web.user;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coe.wms.common.core.db.IdWorker;
import com.coe.wms.common.utils.GsonUtil;
import com.coe.wms.common.web.AbstractController;
import com.coe.wms.common.web.model.Result;
import com.coe.wms.facade.user.entity.User;
import com.coe.wms.facade.user.queryvo.UserQueryVo;
import com.coe.wms.facade.user.resultvo.UserResultVo;
import com.coe.wms.facade.user.service.UserFacade;

@Controller
@RequestMapping("/user/userMgr")
public class UserMgrController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserMgrController.class);

	@Reference(version = "1.1.0")
	private UserFacade userFacade;

	@ResponseBody
	@RequestMapping(value = "/listUser", method = RequestMethod.POST)
	public Result listUser(UserQueryVo vo) {
		vo.setLimit(50);
		Pager<UserResultVo> pager = userFacade.selectUserListByVo(vo);
		if (null != pager && pager.getList().size() > 0) {
			logger.debug("查询用户分页信息", pager);
			System.out.println(GsonUtil.toJson(pager));
			return Result.success(pager);
		}
		logger.debug("查询失败");
		return Result.error("没有查到对应信息！");
	}

	/**
	 * 新增用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public Result addUser(@RequestBody User user) {
		IdWorker idWorker = new IdWorker(0, 12);
		user.setId(idWorker.nextId());
		User resultUser = userFacade.add(user);
		if (null == resultUser) {
			return Result.error("新增失败");
		}
		return Result.success(resultUser);
	}

	/**
	 * 获取要修改的用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUpdUser", method = RequestMethod.GET)
	public Result getUpdUser(@RequestParam String id) {
		Long userId = Long.valueOf(id);
		User updUser = userFacade.get(userId);
		if (null == updUser) {
			logger.debug("更新用户信息", updUser);
			return Result.error("获取修改用户失败");
		}
		return Result.success(updUser);
	}

	/**
	 * 修改用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updUser", method = RequestMethod.POST)
	public Result updUser(@RequestBody User user) {
		User resultUser = userFacade.update(user);
		if (null == resultUser) {
			return Result.error("修改失败");
		}
		return Result.success(resultUser);
	}

	/**
	 * 删除用户
	 * 
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	public Result delUser(@RequestParam(required = true) String id) {
		Long userId = Long.valueOf(id);
		User user = userFacade.get(userId);
		boolean isDel = userFacade.delete(userId);
		if (false == isDel) {
			return Result.error("删除失败");
		}
		return Result.success("删除用户" + user.getUserName() + "成功");
	}

	/**
	 * 查询符合条件的用户，不分页
	 * 
	 * @param vo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/listUserNotPage", method = RequestMethod.POST)
	public Result listUserNotPage(UserQueryVo vo) {
		List<UserResultVo> resultList = userFacade.listUserNotPage(vo);
		if (null != resultList && resultList.size() > 0) {
			logger.debug("查询用户成功", resultList);
			return Result.success(resultList);
		}
		logger.debug("查询失败");
		return Result.error("没有查到对应信息！");
	}

	/**
	 * 根据用户代码查询用户
	 * 
	 * @param userCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserByUserCode", method = RequestMethod.GET)
	public Result getUserByUserCode(@RequestParam("userCode") String userCode) {
		UserResultVo userResultVo = userFacade.getUserByUserCode(userCode);
		if (userResultVo != null) {

			return Result.success(userResultVo);
		} else {

			return Result.error("未查到符合条件的数据");
		}
	}

	/**
	 * 查询所有用户代码
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllUserCode", method = RequestMethod.POST)
	public Result getAllUserCode() {
		List<String> list = userFacade.getAllUserCode();
		if (list != null && list.size() > 0) {
			return Result.success(list);
		} else {
			return Result.error("未查到符合条件的数据");
		}
	}

}
