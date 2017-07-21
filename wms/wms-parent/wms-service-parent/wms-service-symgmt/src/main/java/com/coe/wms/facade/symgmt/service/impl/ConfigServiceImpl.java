package com.coe.wms.facade.symgmt.service.impl;

import com.coe.wms.common.core.cache.redis.RedisClient;
import com.coe.wms.common.utils.GsonUtil;
import com.coe.wms.constant.SymgmtConstant;
import com.coe.wms.facade.symgmt.entity.Config;
import com.coe.wms.facade.symgmt.entity.ConfigCriteria;
import com.coe.wms.facade.symgmt.entity.ConfigCriteria.Criteria;
import com.coe.wms.facade.symgmt.service.ConfigService;
import com.coe.wms.service.symgmt.mapper.ConfigMapper;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.plugin.model.Pager;
import org.mybatis.plugin.util.PagerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("configService")
@com.alibaba.dubbo.config.annotation.Service
public class ConfigServiceImpl implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    public Config add(Config record) {
        if(this.configMapper.insertSelective(record)==1)
        	return record; 
        return null;
    }

    public boolean delete(Long id) {
        return this.configMapper.deleteByPrimaryKey(id)==1;
    }

    public Config update(Config record) {
        if(this.configMapper.updateByPrimaryKeySelective(record)==1)
        	return record;
        return null;
    }

    public Config get(Long id) {
        return this.configMapper.selectByPrimaryKey(id);
    }

    public Pager<Config> list(int page, int limit) {
        ConfigCriteria criteria = new ConfigCriteria();
        criteria.setPage(page);
        criteria.setLimit(limit);
        Criteria cri = criteria.createCriteria();
        List<Config> list = configMapper.selectByConditionList(criteria);
        return PagerUtil.getPager(list, criteria);
    }

    /**
     * 此缓存手动新增删除
     */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> getConfigByIdentification(String identification) {
		RedisClient redisClient=RedisClient.getInstance();
	    //从缓存获取配置信息
		String configStr = redisClient.getString(SymgmtConstant.CONFIG_PREFIX+identification);
		if(configStr!=null){
			return GsonUtil.toObject(configStr, Map.class);
		}
		 //从数据库加载
		 ConfigCriteria configCriteria = new ConfigCriteria();
		 Criteria criteria = configCriteria.createCriteria();
		 //根据标识查询
		 criteria.andIdentificationEqualTo(identification);
		 configCriteria.getOredCriteria().clear();
		 configCriteria.getOredCriteria().add(criteria);
		 List<Config> configList = configMapper.selectByConditionList(configCriteria);
		//如果没有配置信息
		 if(configList==null||configList.size()==0){
			 return null;
		 }
		 
		 //装换成map
		 Map<String, String>  configMap=new HashMap<String, String>();
		 for (Config config : configList) {
			 configMap.put(config.getsKey(),config.getsValue());
		  }
		 //缓存配置信息  当配置发生更改时自动delete
		 try {
		    String configMapStr = GsonUtil.toJson(configMap);
		    redisClient.setString(SymgmtConstant.CONFIG_PREFIX+identification, configMapStr);
		} catch (Exception e) {
		}
		 
		return  configMap;
	}
}