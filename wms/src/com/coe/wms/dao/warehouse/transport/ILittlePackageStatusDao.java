package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.LittlePackageStatus;

public interface ILittlePackageStatusDao {

	public LittlePackageStatus findLittlePackageStatusByCode(String code);

	public List<LittlePackageStatus> findAllLittlePackageStatus();
}
