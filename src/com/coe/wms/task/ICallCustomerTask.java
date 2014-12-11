package com.coe.wms.task;

public interface ICallCustomerTask {

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

	/**
	 * 回传转运订单审核结果给客户
	 */
	public void sendBigPackageCheckResultToCustomer();

	/**
	 * 回传转运订单小包收货结果给客户
	 */
	public void sendLittlePackageReceivedToCustomer();

	/**
	 * 回传转运订单重量给客户
	 */
	public void sendBigPackageWeightToCustomer();

	/**
	 * 回传转运订单出库状态给客户(出库的最后步骤)
	 */
	public void sendBigPackageStatusToCustomer();
}
