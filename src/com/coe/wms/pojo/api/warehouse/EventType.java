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
	 * SF 取消出库
	 */
	public static final String LOGISTICS_CANCEL = "LOGISTICS_CANCEL";

	/**
	 * 仓库回传出库状态
	 */
	public static final String WMS_STOCKOUT_INFO = "WMS_STOCKOUT_INFO";

	/**
	 * 转运订单入库
	 */
	public static final String LOGISTICS_TRADE_PAID = "LOGISTICS_TRADE_PAID";

	/**
	 * 回传审单
	 */
	public static final String WMS_CHECK_ORDER = "WMS_CHECK_ORDER";

	/**
	 * 回传转运收货
	 */
	public static final String WMS_STOCKIN_INFO = "WMS_STOCKIN_INFO";

	/**
	 * 转运确认出库
	 */
	public static final String LOGISTICS_SEND_GOODS = "LOGISTICS_SEND_GOODS";

	/**
	 * 仓库通过E特快进行清关，若产生关税, 仓库回传关税收取结果
	 */
	public static final String WMS_ORDER_TAXd = "WMS_ORDER_TAX";

}
