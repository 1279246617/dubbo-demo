package com.coe.wms.task;

public interface IOnShelvesTask {

	/**
	 * 定时更新入库上架状态
	 * 
	 * 部分上架,全未上架,上架完成
	 */
	public void updateInWarehouseRecordOnShelvesStatus();
}
