package com.coe.wms.dao.warehouse.storage;

import java.util.List;

import com.coe.wms.model.warehouse.storage.PackageItem;

public interface IPackageItemDao {

	public long savePackageItem(PackageItem item);

	public int saveBatchPackageItem(List<PackageItem> itemList);
}
