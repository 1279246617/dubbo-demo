package com.coe.wms.dao.warehouse.transport;

import java.util.List;

import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;

public interface ILittlePackageStatusDao {

	public FirstWaybillStatus findLittlePackageStatusByCode(String code);

	public List<FirstWaybillStatus> findAllLittlePackageStatus();
}
