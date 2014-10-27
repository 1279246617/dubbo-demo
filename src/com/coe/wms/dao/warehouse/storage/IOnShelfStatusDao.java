package com.coe.wms.dao.warehouse.storage;

import com.coe.wms.model.warehouse.storage.record.OnShelfStatus;

public interface IOnShelfStatusDao {

	public OnShelfStatus findOnShelfStatusByCode(String code);
}
