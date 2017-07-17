package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.facade.symgmt.entity.Menu;
import com.coe.wms.facade.symgmt.entity.MenuCriteria;
import com.coe.wms.facade.symgmt.entity.MenuCriteria.Criteria;
import com.coe.wms.facade.symgmt.entity.vo.MenuVo;
import com.coe.wms.facade.symgmt.service.MenuService;
import com.coe.wms.service.symgmt.mapper.MenuMapper;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    public Menu add(Menu record) {
        record.setCreateTime(System.currentTimeMillis());
        if(this.menuMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.menuMapper.deleteByPrimaryKey(id)==1;
    }

    public Menu update(Menu record) {
        record.setUpdateTime(System.currentTimeMillis());
        if(this.menuMapper.updateByPrimaryKeySelective(record)==1)
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
	public List<Menu> getHeaderMenuList() {
		 MenuCriteria criteria = new MenuCriteria();
		 Criteria selectCriteria = criteria.createCriteria();
		 selectCriteria.andParentIdEqualTo(-1l);
		 criteria.getOredCriteria().clear();
		 criteria.getOredCriteria().add(selectCriteria);
		 List<Menu> menuList = menuMapper.selectByConditionList(criteria);
		 return menuList;
	}

	@Override
	public List<MenuVo> getMenuAll() {
		 MenuCriteria criteria = new MenuCriteria();
		 Criteria selectCriteria = criteria.createCriteria();
		 selectCriteria.andParentIdEqualTo(-1l);
		 criteria.getOredCriteria().clear();
		 criteria.getOredCriteria().add(selectCriteria);
		 //获取head
		 List<Menu> menuList = menuMapper.selectByConditionList(criteria);
		 
		 //如果uri==null,
		
		
		return null;
	}
	
	
	
	
}