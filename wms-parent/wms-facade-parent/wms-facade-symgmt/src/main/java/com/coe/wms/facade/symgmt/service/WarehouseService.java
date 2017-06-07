package com.coe.wms.facade.symgmt.service;

import com.coe.wms.facade.symgmt.entity.Warehouse;
import org.mybatis.plugin.model.Pager;

public interface WarehouseService {
    Warehouse add(Warehouse record);

    boolean delete(Long id);

    Warehouse update(Warehouse record);

    Warehouse get(Long id);

    Pager<Warehouse> list(int page, int limit);
}