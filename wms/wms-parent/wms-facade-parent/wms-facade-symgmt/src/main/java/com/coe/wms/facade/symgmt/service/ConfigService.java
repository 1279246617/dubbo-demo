package com.coe.wms.facade.symgmt.service;

import java.util.Map;

import com.coe.wms.facade.symgmt.entity.Config;

import org.mybatis.plugin.model.Pager;

public interface ConfigService {
    Config add(Config record);

    boolean delete(Long id);

    Config update(Config record);

    Config get(Long id);

    Pager<Config> list(int page, int limit);
    
    /**
     * 根据标识获取配置  k,v
     * @param identification
     * @return
     */
    Map<String, String> getConfigByIdentification(String identification);
}