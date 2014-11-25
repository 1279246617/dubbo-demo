package com.coe.wms.task;

public interface IGenerateReportTask {

	/**
	 * 
	 * 生成入库日报表
	 */
	public void inWarehouseReport();

	/**
	 * 
	 * 生成出库日报表
	 */
	public void outWarehouseReport();

	/**
	 * 库存报表
	 */
	public void inventoryReport();

	/**
	 * 日结库存
	 */
	public void dailyInventory();
}
