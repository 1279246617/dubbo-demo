package com.coe.wms.pojo.api.warehouse;

public class EventType {

	/**
	 * 下发SKU入库指令
	 */
	public static final String LOGISTICS_SKU_STOCKIN_INFO = "LOGISTICS_SKU_STOCKIN_INFO";

	/**
	 * 回传SKU入库信息
	 */
	public static final String WMS_SKU_STOCKIN_INFO = "WMS_SKU_STOCKIN_INFO";

	/**
	 * SKU出库指令
	 */
	public static final String LOGISTICS_SKU_PAID = "LOGISTICS_SKU_PAID";

	/**
	 * 仓库检货后回传称重
	 */
	public static final String WMS_GOODS_WEIGHT = "WMS_GOODS_WEIGHT";

	/**
	 * SF 确定出库
	 */
	public static final String LOGISTICS_SEND_SKU = "LOGISTICS_SEND_SKU";

	/**
	 * 仓库回传出库状态
	 */
	public static final String WMS_STOCKOUT_INFO = "WMS_STOCKOUT_INFO";
}
