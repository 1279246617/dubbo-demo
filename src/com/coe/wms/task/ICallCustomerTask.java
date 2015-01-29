package com.coe.wms.task;

import com.coe.wms.model.warehouse.transport.OrderPackage;

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
	public void sendOrderCheckResultToCustomer();

	/**
	 * 回传转运大包审单结果给客户
	 */
	public void sendOrderPackageCheckResultToCustomer();

	/**
	 * 回传转运头程运单收货结果给客户
	 */
	public void sendFirstWaybillReceivedToCustomer();

	/**
	 * 回传转运订单重量给客户
	 */
	public void sendOrderWeightToCustomer();

	/**
	 * 回传转运订单出库状态给客户(出库的最后步骤)
	 */
	public void sendOrderStatusToCustomer();
}
