package com.coe.wms.dao.api;

import com.coe.wms.model.api.ScannerVersion;

public interface IScannerVersionDao {
	public ScannerVersion getLatestScannerVersion();
}
