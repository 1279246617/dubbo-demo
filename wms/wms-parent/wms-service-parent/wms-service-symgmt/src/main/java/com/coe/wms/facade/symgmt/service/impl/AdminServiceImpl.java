package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.common.core.db.IdWorker;
import com.coe.wms.common.core.db.IdWorkerAide;
import com.coe.wms.common.core.db.IdWorkerForRedis;
import com.coe.wms.common.utils.Beanut;
import com.coe.wms.common.utils.StringUtil;
import com.coe.wms.facade.symgmt.entity.Admin;
import com.coe.wms.facade.symgmt.entity.AdminCriteria;
import com.coe.wms.facade.symgmt.entity.AdminCriteria.Criteria;
import com.coe.wms.facade.symgmt.entity.vo.AdminVo;
import com.coe.wms.facade.symgmt.service.AdminService;
import com.coe.wms.service.symgmt.mapper.AdminMapper;

import java.util.List;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
@com.alibaba.dubbo.config.annotation.Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    
   

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    public Admin add(Admin record) {
    	record.setId(IdWorkerForRedis.netId("admin"));
    	if(this.adminMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.adminMapper.deleteByPrimaryKey(id)==1;
    }

    public Admin update(Admin record) {
        if(this.adminMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Admin get(Long id) {
        return this.adminMapper.selectByPrimaryKey(id);
    }

    public Pager<Admin> list(int page, int limit) {
        AdminCriteria criteria = new AdminCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Admin> list = adminMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }

	@Override
	public AdminVo login(AdminVo adminVo) {
		if(StringUtil.isEmpty(adminVo.getLoginName())||StringUtil.isEmpty(adminVo.getPassword())){
			return null;
		}
         AdminCriteria criteria = new AdminCriteria();
		 Criteria cri = criteria.createCriteria();
		 cri.andLoginNameEqualTo(adminVo.getLoginName());
		 cri.andPasswordEqualTo(adminVo.getPassword());
		 criteria.getOredCriteria().clear();
		 criteria.getOredCriteria().add(cri);
		 List<Admin> adminList = adminMapper.selectByConditionList(criteria);
		 //登录成功
		 if(adminList!=null && adminList.size()>0){
		     Admin admin = adminList.get(0);
		     //把基本用户信息拷贝至vo
		     Beanut.copy(admin, adminVo);
		     return adminVo;
		 }
		return null;
	}

	@Override
	public Pager<Admin> getFromFront(AdminVo adminVo) {
		 AdminCriteria criteria = new AdminCriteria();
		 criteria.setPage(adminVo.getPage());
	      criteria.setLimit(adminVo.getLimit());
		 Criteria cri = criteria.createCriteria();
		 if(!StringUtil.isEmpty(adminVo.getUserName()))
		   cri.andUserNameEqualTo(adminVo.getUserName());
		 if(!StringUtil.isEmpty(adminVo.getLoginName()))
		  cri.andLoginNameEqualTo(adminVo.getLoginName());
		 criteria.getOredCriteria().clear();
		 criteria.getOredCriteria().add(cri);
		 List<Admin> adminList = adminMapper.selectByConditionList(criteria);
		 return PagerUtil.getPager(adminList, criteria);
	}
}