package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Menu;
import org.mybatis.plugin.model.Pager;

public interface MenuService {
    Menu add(Menu record);

    boolean delete(Long id);

    Menu update(Menu record);

    Menu get(Long id);

    Pager<Menu> list(int page, int limit);
}