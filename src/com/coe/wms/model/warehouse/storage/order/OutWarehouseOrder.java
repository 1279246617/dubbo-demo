package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;

/**
 * 
 * 出库主单
 * 
 * @author Administrator
 * 
 */
public class OutWarehouseOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1475638002960975692L;
	/**
	 * 销售出库单
	 */
	public static final String ORDER_TYPE_SALE = "1";

	public static final String ORDER_TYPE_SALE_CN = "销售出库";

	/**
	 * 盘点出库单
	 */
	public static final String ORDER_TYPE_CHECK = "2";

	public static final String ORDER_TYPE_CHECK_CN = "盘点出库";
	/**
	 * 调拨出库单
	 */
	public static final String ORDER_TYPE_ALLOCATION = "3";

	public static final String ORDER_TYPE_ALLOCATION_CN = "调拨出库";

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 仓库id
	 */
	private Long warehouseId;

	/**
	 * 订单所属客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 订单所属操作员id
	 */
	private Long userIdOfOperator;
	/**
	 * 出货渠道
	 */
	private String shipwayCode;

	/**
	 * 运输渠道附加字段1 不同渠道,对此字段用法不一样,命名为附加字段
	 */
	private String shipwayExtra1;

	/**
	 * 运输渠道附加字段2
	 */
	private String shipwayExtra2;
	/**
	 * 出货跟踪号
	 */
	private String trackingNo;

	/**
	 * 创建时间 (收货时间)
	 */
	private Long createdTime;

	/**
	 * 出库摘要 COE操作备注
	 */
	private String remark;

	/**
	 * 客户订单号, 用于客户对该出库指令进行修改,确认等
	 * 
	 * 对应 顺丰 tradeOrderId
	 */
	private String customerReferenceNo;

	/**
	 * 顺丰API,字段, 等于买家的备注,目前仅用于打印捡货单上的买家备注
	 */
	private String tradeRemark;

	/**
	 * 顺丰API字段,等于卖家备注,用于打印捡货单上的卖家备注
	 */
	private String logisticsRemark;

	/**
	 * 
	 * 标识是否已经打印捡货单 打印次数
	 */
	private Integer printedCount;

	/**
	 * 状态
	 */
	private String status;
	// 当状态是等待回传出库称重给客户并且callbackSendWeightIsSuccess=N或Null 回传称重,
	// 当状态是等待回传出库状态给客户并且callbackSendStatusIsSuccess=N或Null 回传状态
	/**
	 * 回调称重是否成功 Y 成功 N或者空失败
	 */
	private String callbackSendWeightIsSuccess;

	/**
	 * 回调次数
	 */
	private Integer callbackSendWeighCount;

	/**
	 * 回调出库状态是否成功 Y 成功 N或者空失败
	 */
	private String callbackSendStatusIsSuccess;

	/**
	 * 回调次数
	 */
	private Integer callbackSendStatusCount;

	/**
	 * 出库重量
	 * 
	 */
	private Double outWarehouseWeight;

	/**
	 * 重量代码 KG/G
	 */
	private String weightCode;
	/**
	 * 订单类型:调拨,销售,盘点
	 */
	private String orderType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getWeightCode() {
		return weightCode;
	}

	public void setWeightCode(String weightCode) {
		this.weightCode = weightCode;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getTradeRemark() {
		return tradeRemark;
	}

	public void setTradeRemark(String tradeRemark) {
		this.tradeRemark = tradeRemark;
	}

	public String getLogisticsRemark() {
		return logisticsRemark;
	}

	public String getShipwayExtra1() {
		return shipwayExtra1;
	}

	public void setShipwayExtra1(String shipwayExtra1) {
		this.shipwayExtra1 = shipwayExtra1;
	}

	public String getShipwayExtra2() {
		return shipwayExtra2;
	}

	public void setShipwayExtra2(String shipwayExtra2) {
		this.shipwayExtra2 = shipwayExtra2;
	}

	public void setLogisticsRemark(String logisticsRemark) {
		this.logisticsRemark = logisticsRemark;
	}

	public Integer getPrintedCount() {
		return printedCount;
	}

	public void setPrintedCount(Integer printedCount) {
		this.printedCount = printedCount;
	}

	public Double getOutWarehouseWeight() {
		return outWarehouseWeight;
	}

	public void setOutWarehouseWeight(Double outWarehouseWeight) {
		this.outWarehouseWeight = outWarehouseWeight;
	}

	public String getCustomerReferenceNo() {
		return customerReferenceNo;
	}

	public void setCustomerReferenceNo(String customerReferenceNo) {
		this.customerReferenceNo = customerReferenceNo;
	}

	public String getCallbackSendWeightIsSuccess() {
		return callbackSendWeightIsSuccess;
	}

	public void setCallbackSendWeightIsSuccess(String callbackSendWeightIsSuccess) {
		this.callbackSendWeightIsSuccess = callbackSendWeightIsSuccess;
	}

	public Integer getCallbackSendWeighCount() {
		return callbackSendWeighCount;
	}

	public void setCallbackSendWeighCount(Integer callbackSendWeighCount) {
		this.callbackSendWeighCount = callbackSendWeighCount;
	}

	public String getCallbackSendStatusIsSuccess() {
		return callbackSendStatusIsSuccess;
	}

	public void setCallbackSendStatusIsSuccess(String callbackSendStatusIsSuccess) {
		this.callbackSendStatusIsSuccess = callbackSendStatusIsSuccess;
	}

	public Integer getCallbackSendStatusCount() {
		return callbackSendStatusCount;
	}

	public void setCallbackSendStatusCount(Integer callbackSendStatusCount) {
		this.callbackSendStatusCount = callbackSendStatusCount;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShipwayCode() {
		return shipwayCode;
	}

	public void setShipwayCode(String shipwayCode) {
		this.shipwayCode = shipwayCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public Long getUserIdOfOperator() {
		return userIdOfOperator;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}
}
