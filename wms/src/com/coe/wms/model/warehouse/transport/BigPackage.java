package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 大包 等于顺丰 tradeOrder
 * 
 * 回传称重以大包为单位
 * 
 * 回传收货以小包为单位
 * 
 * 回传出库以大包为单位
 */
public class BigPackage implements Serializable {
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
	 * 创建时间
	 */
	private Long createdTime;

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
	 * 出库摘要 COE操作备注
	 */
	private String remark;
	/**
	 * 出货渠道
	 */
	private String shipwayCode;
	/**
	 * 出货跟踪号
	 */
	private String trackingNo;
	/**
	 * 状态
	 * 
	 * 全未入库, 部分入库 全部入库
	 * 
	 * 上架(以小包为单位)
	 * 
	 * 待合包称重(等于下架) 待回传称重 待客户确认 待出库操作
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
