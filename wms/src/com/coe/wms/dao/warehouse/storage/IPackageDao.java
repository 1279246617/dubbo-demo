package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.PackageStatus;

public interface IPackageDao {

	public long savePackage(com.coe.wms.model.warehouse.storage.Package pag);

	public PackageStatus findPackageStatusByCode(String code);
}
