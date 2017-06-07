package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Aadmin;
import org.mybatis.plugin.model.Pager;

public interface AadminService {
    Aadmin add(Aadmin record);

    boolean delete(Long id);

    Aadmin update(Aadmin record);

    Aadmin get(Long id);

    Pager<Aadmin> list(int page, int limit);
}