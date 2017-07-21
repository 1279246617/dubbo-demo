package com.coe.wms.web.symgmt;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.coe.wms.common.model.Session;
import com.coe.wms.common.web.AbstractController;
import com.coe.wms.common.web.model.Result;
import com.coe.wms.common.web.session.CacheSession;
import com.coe.wms.facade.symgmt.entity.Menu;
import com.coe.wms.facade.symgmt.entity.vo.AdminVo;
import com.coe.wms.facade.symgmt.service.MenuService;
import com.google.gson.Gson;

/**
 * 菜单控制器
* @ClassName: MenuController  
* @author lqg  
* @date 2017年7月18日 下午2:26:29  
* @Description: TODO
 */
@RestController
@RequestMapping("/symgmt/menu")
public class MenuController extends AbstractController{

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Reference
	private MenuService menuService;
	
	@Autowired
	private CacheSession session;
	
	/**
	 * 获取头部菜单
	 * 获取主菜单
	 * @return
	 */
	@RequestMapping("getHeaderMenu")
	public Result getHeaderMenu(){
		List<Menu> headerMenuList = menuService.getHeaderMenuList();
		return Result.success(headerMenuList);
	}
	/**
	 * 获取所有菜单
	 * @return
	 */
	@RequestMapping("getAllMenu")
	public Result getAllMenu(){
		Gson gson=new Gson();
		AdminVo adminVo = gson.fromJson(session.getAttr(Session.USER_ADMINVO_KEY), AdminVo.class);
		return Result.success(menuService.getMenuAllByAdmin(adminVo));
	}
	/**
	 * 根据pid获取按钮
	 * @param pid
	 * @return
	 */
	@RequestMapping("getButtonByPid/{pid}")
	public Result getButtonByPid(@PathVariable("pid") Long pid){
		Gson gson=new Gson();
		AdminVo adminVo = gson.fromJson(session.getAttr(Session.USER_ADMINVO_KEY), AdminVo.class);
		return Result.success(menuService.getButtonByPid(pid, adminVo));
	}
	
}
