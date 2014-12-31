package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.OrderStatus;

public interface IBigPackageStatusDao {

	public OrderStatus findBigPackageStatusByCode(String code);

	public List<OrderStatus> findAllBigPackageStatus();
}
