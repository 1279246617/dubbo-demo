package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 订单
 * 
 * 转运服务 以订单为单位, 在预报时就要求有订单的收件人信息
 * 
 * 仓配服务 以大包为单位, 大包下只有SKU和数量, 无收件人信息, 出库指令时有收件人信息
 * 
 * @author yechao
 * @date 2013年11月2日
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -95644842282452189L;
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
	 * 状态
	 */
	private String status;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
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

	public String getShipwayCode() {
		return shipwayCode;
	}

	public void setShipwayCode(String shipwayCode) {
		this.shipwayCode = shipwayCode;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
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

	public String getCustomerReferenceNo() {
		return customerReferenceNo;
	}

	public void setCustomerReferenceNo(String customerReferenceNo) {
		this.customerReferenceNo = customerReferenceNo;
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

	public void setLogisticsRemark(String logisticsRemark) {
		this.logisticsRemark = logisticsRemark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Double getOutWarehouseWeight() {
		return outWarehouseWeight;
	}

	public void setOutWarehouseWeight(Double outWarehouseWeight) {
		this.outWarehouseWeight = outWarehouseWeight;
	}

	public String getWeightCode() {
		return weightCode;
	}

	public void setWeightCode(String weightCode) {
		this.weightCode = weightCode;
	}
}
