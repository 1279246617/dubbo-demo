package com.coe.wms.task;

public interface IStorageTask {

	/**
	 * 发送入库仓配信息给客户
	 */
	public void sendInWarehouseInfoToCustomer();

	/**
	 * 回传出库称重给客户
	 */
	public void sendOutWarehouseWeightToCustomer();

	/**
	 * 回传出库状态给客户(出库的最后步骤)
	 */
	public void sendOutWarehouseStatusToCustomer();
}
