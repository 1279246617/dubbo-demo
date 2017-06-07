package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Server;
import org.mybatis.plugin.model.Pager;

public interface ServerService {
    Server add(Server record);

    boolean delete(Long id);

    Server update(Server record);

    Server get(Long id);

    Pager<Server> list(int page, int limit);
}