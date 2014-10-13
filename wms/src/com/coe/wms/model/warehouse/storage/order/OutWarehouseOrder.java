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
	 * 创建时间 (收货时间)
	 */
	private Long createdTime;

	/**
	 * 出库摘要
	 */
	private String remark;

	/**
	 * 客户参考号, 用于客户对该出库指令进行修改,确认等
	 */
	private String customerReferenceNo;
	
	/**
	 * 状态
	 */
	private String status;
	//当状态是等待回传出库称重给客户并且callbackSendWeightIsSuccess=N或Null 回传称重,  
	//当状态是等待回传出库状态给客户并且callbackSendStatusIsSuccess=N或Null 回传状态
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

	public String getWeightCode() {
		return weightCode;
	}

	public void setWeightCode(String weightCode) {
		this.weightCode = weightCode;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
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
