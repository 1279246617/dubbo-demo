package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.common.core.cache.annot.SetCache;
import com.coe.wms.common.core.cache.redis.RedisClient;
import com.coe.wms.common.utils.Beanut;
import com.coe.wms.common.utils.GsonUtil;
import com.coe.wms.constant.SymgmtConstant;
import com.coe.wms.facade.symgmt.entity.Menu;
import com.coe.wms.facade.symgmt.entity.MenuCriteria;
import com.coe.wms.facade.symgmt.entity.MenuCriteria.Criteria;
import com.coe.wms.facade.symgmt.entity.vo.AdminVo;
import com.coe.wms.facade.symgmt.entity.vo.MenuVo;
import com.coe.wms.facade.symgmt.service.MenuService;
import com.coe.wms.service.symgmt.mapper.MenuMapper;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuService")
@com.alibaba.dubbo.config.annotation.Service
public class MenuServiceImpl implements MenuService {
	@Autowired
	private MenuMapper menuMapper;

	private RedisClient redisClient = RedisClient.getInstance();

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	public Menu add(Menu record) {
		record.setCreateTime(System.currentTimeMillis());
		if (this.menuMapper.insertSelective(record) == 1)
			return record;
		return null;
	}

	public boolean delete(Long id) {
		return this.menuMapper.deleteByPrimaryKey(id) == 1;
	}

	public Menu update(Menu record) {
		record.setUpdateTime(System.currentTimeMillis());
		if (this.menuMapper.updateByPrimaryKeySelective(record) == 1)
			return record;
		return null;
	}

	public Menu get(Long id) {
		return this.menuMapper.selectByPrimaryKey(id);
	}

	public Pager<Menu> list(int page, int limit) {
		MenuCriteria criteria = new MenuCriteria();
		criteria.setPage(page);
		criteria.setLimit(limit);
		Criteria cri = criteria.createCriteria();
		List<Menu> list = menuMapper.selectByConditionList(criteria);
		return PagerUtil.getPager(list, criteria);
	}

	@Override
	@SetCache(key = SymgmtConstant.MENU_HEADER_KEY, expire = 5 * 24 * 60 * 60)
	public List<Menu> getHeaderMenuList() {

		// 判断缓存是否存在菜单主信息
		/*
		 * try { String headerMenuListStr = redisClient.getString(SymgmtConstant.MENU_HEADER_KEY); if(headerMenuListStr!=null){ return
		 * GsonUtil.toObject(headerMenuListStr,new TypeToken<List<Menu>>() { }.getType()); } } catch (Exception e) { }
		 */

		MenuCriteria criteria = new MenuCriteria();
		Criteria selectCriteria = criteria.createCriteria();
		selectCriteria.andParentIdEqualTo(-1l);
		criteria.getOredCriteria().clear();
		criteria.getOredCriteria().add(selectCriteria);
		List<Menu> menuList = menuMapper.selectByConditionList(criteria);

		// 把主菜单信息放进缓存中
		/*
		 * try { if(menuList!=null && menuList.size()>0){ redisClient.setString(SymgmtConstant.MENU_HEADER_KEY, GsonUtil.toJson(menuList),
		 * 5*24*60*60); } } catch (Exception e) { }
		 */

		return menuList;
	}

	@SetCache(key = SymgmtConstant.MENU_ALL_KEY, expire = 2 * 60 * 60)
	public List<MenuVo> getMenuAll() {

		/*
		 * // 缓存出错不用处理 try { String menuAllStr = redisClient.getString(SymgmtConstant.MENU_ALL_KEY); if (menuAllStr != null) { List<MenuVo> menuVoList
		 * = GsonUtil.toObject(menuAllStr, new TypeToken<List<MenuVo>>() { }.getType()); return menuVoList; } } catch (Exception e) {
		 * 
		 * }
		 */
		MenuCriteria criteria = new MenuCriteria();
		Criteria selectCriteria = criteria.createCriteria();
		selectCriteria.andParentIdEqualTo(-1l);
		selectCriteria.andDictMenuStatusEqualTo("menu_status_1");
		criteria.getOredCriteria().clear();
		criteria.getOredCriteria().add(selectCriteria);
		// 获取head
		List<Menu> menuList = menuMapper.selectByConditionList(criteria);

		List<MenuVo> menuVoList = new ArrayList<MenuVo>();

		for (Menu menu : menuList) {
			// 转换成vo
			MenuVo menuVo = new MenuVo();
			Beanut.copy(menu, menuVo);
			// 如果是父级菜单 递归封装
			if ("menu_type_1".equals(menuVo.getDictMenuType()))
				packageMenuVo(menuVo);
			menuVoList.add(menuVo);
		}
		// String menuVoListStr = GsonUtil.toJson(menuVoList);
		// 缓存两个小时
		/*
		 * try { if(menuVoListStr!=null) redisClient.setString(SymgmtConstant.MENU_ALL_KEY, menuVoListStr, 2 * 60 * 60); } catch (Exception e) { }
		 */

		return menuVoList;
	}

	/**
	 * 递归封装子菜单
	 * 
	 * @param menuVo
	 */
	private void packageMenuVo(MenuVo menuVo) {
		MenuCriteria criteria = new MenuCriteria();
		Criteria selectCriteria = criteria.createCriteria();
		// 根据父id查询
		selectCriteria.andParentIdEqualTo(menuVo.getId());
		selectCriteria.andDictMenuStatusEqualTo("menu_status_1");
		criteria.getOredCriteria().clear();
		criteria.getOredCriteria().add(selectCriteria);
		List<Menu> menuList = menuMapper.selectByConditionList(criteria);
		List<MenuVo> menuVoList = new ArrayList<MenuVo>();

		for (Menu menuChild : menuList) {
			MenuVo menuVoChild = new MenuVo();
			Beanut.copy(menuChild, menuVoChild);
			if ("menu_type_1".equals(menuVoChild.getDictMenuType()))
				packageMenuVo(menuVoChild);
			menuVoList.add(menuVoChild);
		}
		menuVo.setChildrenMenuVo(menuVoList);

	}

	@Override
	@SetCache(key = SymgmtConstant.BUTTON_PREFIX, expire = 2 * 60 * 60)
	public List<Menu> getButtonByPid(Long pid, AdminVo adminVo) {
		/*
		 * // 从缓存处取数据 try { String menuAllStr = redisClient.getString(SymgmtConstant.BUTTON_PREFIX + pid); if (menuAllStr != null) { List<Menu>
		 * buttonList = GsonUtil.toObject(menuAllStr, new TypeToken<List<Menu>>() { }.getType()); return buttonList; } } catch (Exception e) {
		 * 
		 * }
		 */

		MenuCriteria criteria = new MenuCriteria();
		Criteria selectCriteria = criteria.createCriteria();
		// 根据父id查询
		selectCriteria.andParentIdEqualTo(pid);
		// 状态为生效
		selectCriteria.andDictMenuStatusEqualTo("menu_status_1");
		// 类型为 button
		selectCriteria.andDictMenuTypeEqualTo("menu_type_3");
		criteria.getOredCriteria().clear();
		criteria.getOredCriteria().add(selectCriteria);
		// 获取按钮
		List<Menu> buttonList = menuMapper.selectByConditionList(criteria);

		// 过滤掉不属于自己的按钮
		// 获取用户权限
		List<Long> listMenuIdByAdminVo = getListMenuIdByAdminVo(adminVo);
		// 过滤
		for (int i = 0; i < buttonList.size(); i++) {
			Menu button = buttonList.get(i);
			//
			if (!listMenuIdByAdminVo.contains(button.getId())) {
				buttonList.remove(i);
				i--;
			}
		}

		/*
		 * try {
		 * 
		 * if (buttonList != null && buttonList.size() > 0) { String buttonListStr = GsonUtil.toJson(buttonList); // 缓存两个小时
		 * redisClient.setString(SymgmtConstant.BUTTON_PREFIX + pid, buttonListStr, 2 * 60 * 60); } } catch (Exception e) { }
		 */

		return buttonList;
	}

	@Override
	public List<MenuVo> getMenuAllByAdmin(AdminVo adminVo) {
		// 获取所有菜单
		List<MenuVo> menuAll = getMenuAll();
		// 根据用户获取菜单权限
		List<Long> listMenuByAdminVo = getListMenuIdByAdminVo(adminVo);
		// 递归过滤菜单
		for (int i = 0; i < menuAll.size(); i++) {
			MenuVo menuVo = menuAll.get(i);
			if (listMenuByAdminVo.contains(menuVo.getId())) {
				if (menuVo.getChildrenMenuVo() != null && menuVo.getChildrenMenuVo().size() > 0) {
					// 递归过滤
					filterMenuVo(menuVo, listMenuByAdminVo);
				}
			} else {
				menuAll.remove(i);
				i--;
			}
		}

		return menuAll;
	}

	/**
	 * 过滤菜单
	 * 
	 * @param menuVo
	 * @param listMenuByAdminVo
	 */
	private void filterMenuVo(MenuVo pMenuVo, List<Long> listMenuByAdminVo) {

		List<MenuVo> childrenMenuVoList = pMenuVo.getChildrenMenuVo();
		for (int i = 0; i < childrenMenuVoList.size(); i++) {
			MenuVo menuVo = childrenMenuVoList.get(i);
			if (listMenuByAdminVo.contains(menuVo.getId())) {
				if (menuVo.getChildrenMenuVo() != null && menuVo.getChildrenMenuVo().size() > 0) {
					// 递归过滤
					filterMenuVo(menuVo, listMenuByAdminVo);
				}
			} else {
				listMenuByAdminVo.remove(i);
				i--;
			}

		}
	}

	/**
	 * 此缓存手动新增,删除
	 * 存放用户所有权限 
	 */
	@Override
	public List<Long> getListMenuIdByAdminVo(AdminVo adminVo) {
		// 尝试从缓存取数据
		try {
			String menuIdListStr = redisClient.getString(SymgmtConstant.USER_MENU_PREFIX + adminVo.getLoginName());
			if (menuIdListStr != null) {
				return GsonUtil.toObject(menuIdListStr, new TypeToken<List<Long>>() {
				}.getType());
			}
		} catch (Exception e) {

		}
		List<Long> listMenuByAdminVo = menuMapper.getListMenuByAdminVo(adminVo);
		// 把数据放入缓存中 时间不设置 当用户权限发生修改时会delete
		try {
			String listMenuByAdminVoStr = GsonUtil.toJson(listMenuByAdminVo);
			if (listMenuByAdminVoStr != null) {
				redisClient.setString(SymgmtConstant.USER_MENU_PREFIX + adminVo.getLoginName(), listMenuByAdminVoStr);
			}
		} catch (Exception e) {
		}
		return listMenuByAdminVo;
	}

}