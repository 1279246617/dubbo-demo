package com.coe.wms.web.symgmt;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coe.wms.common.model.Session;
import com.coe.wms.common.utils.CookieUtil;
import com.coe.wms.common.utils.GsonUtil;
import com.coe.wms.common.utils.StringUtil;
import com.coe.wms.common.web.AbstractController;
import com.coe.wms.common.web.model.Result;
import com.coe.wms.common.web.session.CacheSession;
import com.coe.wms.common.web.session.CookieHolder;
import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.entity.vo.AdminVo;
import com.coe.wms.facade.symgmt.service.AdminService;
import com.coe.wms.facade.symgmt.service.MenuService;
@RestController
@RequestMapping("/symgmt/admin")
public class AdminController extends AbstractController{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Reference
	private AdminService adminService;
	
	@Reference
	private MenuService menuService;
	
	@Autowired
	private CacheSession session;
	
	/**
	 * 登录
	 * @param adminVo
	 * @return
	 */
	@RequestMapping("login")
	public Result login(@RequestBody AdminVo adminVo){
		logger.info(adminVo.getLoginName()+":登录");
		 adminVo = adminService.login(adminVo);
		if(adminVo==null){
			return Result.error("登录失败，请检查用户名和密码");
		}
		
		
		//生产唯一cookie
		// 重新设置cookie
		String	sessionId = UUID.randomUUID().toString().replace("-", "");
		CookieHolder.setSessionId(sessionId);

		CookieUtil.addCookie(getHttpServletResponse(), Session.SESSION_ID_KEY, sessionId, session.getSessionTimeOut() * 60);
		// 线上使用 用于共享cookie
		CookieUtil.addCookie(getHttpServletResponse(), Session.SESSION_ID_KEY, sessionId, "coewms.com", false, session.getSessionTimeOut() * 60,
				"/", false);
		//如果登录成功  记录数据
		//session.getAttr(Session.USER_IDENTIFICATION);
		//Session.USER_ADMINVO_KEY
		//已经登录
		session.setAttr(Session.USER_IDENTIFICATION, "0");
		//保存用户信息
		session.setAttr(Session.USER_ADMINVO_KEY, GsonUtil.toJson(adminVo));
		//获取权限
		List<Long> menuList = menuService.getListMenuIdByAdminVo(adminVo);
		//保存权限
		session.setAttr(Session.USER_ROLE_KEY, GsonUtil.toJson(menuList));
		
		return Result.success("登录成功!");
	}

	/**
	 * 根据前端条件查询
	 * @param adminVo
	 * @return
	 */
	@RequestMapping("getFromFront")
	public Result getFromFront( @RequestBody AdminVo adminVo){
		return Result.success(adminService.getFromFront(adminVo));
	}
	/**
	 * 新增来自前端的数据
	 * @param adminVo
	 * @return
	 */
	@RequestMapping("addFromFront")
	public Result addFromFront(@RequestBody AdminVo adminVo){
		Admin addResult = adminService.add(adminVo);
		if(addResult==null){
			return Result.error("新增失败，请稍后重试!");
		}
		return Result.success("新增成功!");
	}
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	@RequestMapping("getById/{id}")
	public Result getById(@PathVariable("id") Long id){
		//根据id获取用户信息
		Admin admin = adminService.get(id);
		//如果用户信息为空
		if(admin==null){
			return Result.error("信息不存在,请稍后重试!");
		}
		//获取成功
		return Result.success(admin);
	}
	
	/**
	 * 更新来自前端的数据
	 * @param adminVo
	 * @return
	 */
	@RequestMapping("updateFromFront")
	public Result updateFromFront(@RequestBody AdminVo adminVo){
		//根据id更新
		Admin admin = adminService.update(adminVo);
		//如果更新结果为空
		if(admin==null){
			return Result.error("更新失败,请稍后重试!");
		}
		return Result.success("更新成功!");
	}

}
