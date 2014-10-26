package com.coe.wms.task;

public interface IInWarehouseTask {

	/**
	 * 定时更新入库订单状态 部分入库,全未入库,入库完成
	 */
	public void updateInWarehouseOrderStatus();
}
