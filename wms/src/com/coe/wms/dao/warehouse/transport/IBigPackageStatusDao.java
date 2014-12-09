package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.BigPackageStatus;

public interface IBigPackageStatusDao {

	public BigPackageStatus findBigPackageStatusByCode(String code);

	public List<BigPackageStatus> findAllBigPackageStatus();
}
