package com.coe.wms.facade.symgmt.service;


import java.util.List;

import com.coe.wms.facade.symgmt.entity.Menu;
import com.coe.wms.facade.symgmt.entity.vo.AdminVo;
import com.coe.wms.facade.symgmt.entity.vo.MenuVo;

import org.mybatis.plugin.model.Pager;

public interface MenuService {
    Menu add(Menu record);

    boolean delete(Long id);

    Menu update(Menu record);

    Menu get(Long id);

    Pager<Menu> list(int page, int limit);
    
    /**
     * 获取菜单头部
     * @return
     */
    List<Menu> getHeaderMenuList();
    
    
    /**
     * 根据父级id获取按钮
     * @param pid
     * @return
     */
    List<Menu> getButtonByPid(Long pid,AdminVo adminVo);
    
    /**
     * 根据用户获取菜单信息
     * @param admin
     * @return
     */
    List<MenuVo> getMenuAllByAdmin(AdminVo adminVo);
    /**
     * 根据用户获取菜单id
     * @param adminVo
     * @return
     */
    List<Long> getListMenuIdByAdminVo(AdminVo adminVo);
    
    
}