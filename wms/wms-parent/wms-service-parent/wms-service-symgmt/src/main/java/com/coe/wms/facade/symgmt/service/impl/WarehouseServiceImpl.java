package com.coe.wms.facade.symgmt.service.impl;

import org.mybatis.plugin.model.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coe.wms.facade.symgmt.entity.Warehouse;
import com.coe.wms.facade.symgmt.service.WarehouseService;
import com.coe.wms.service.symgmt.biz.WarehouseBiz;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {

	@Autowired
	private WarehouseBiz warehouseBiz;

	private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	public Warehouse add(Warehouse record) {
		return this.warehouseBiz.add(record);
	}

	public boolean delete(Long id) {
		return this.warehouseBiz.delete(id);
	}

	public Warehouse update(Warehouse record) {
		return this.warehouseBiz.update(record);
	}

	public Warehouse get(Long id) {
		return this.warehouseBiz.get(id);
	}

	public Pager<Warehouse> list(int page, int limit) {
		return this.warehouseBiz.list(page, limit);
	}
}