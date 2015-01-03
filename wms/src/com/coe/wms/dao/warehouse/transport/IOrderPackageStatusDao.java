package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.OrderPackageStatus;

public interface IOrderPackageStatusDao {

	public OrderPackageStatus findOrderPackageStatusByCode(String code);

	public List<OrderPackageStatus> findAllOrderPackageStatus();
}
