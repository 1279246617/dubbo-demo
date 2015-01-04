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
 * 
 * TransportPackage TransportOrder TransportFirstWaybill
 * TransportFirstWaybillItem
 */
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -95644842282452189L;
	/**
	 * 直接转运
	 */
	public static final String TRANSPORT_TYPE_Z = "Z";

	/**
	 * 集货转运
	 */
	public static final String TRANSPORT_TYPE_J = "J";

	/**
	 * 流连大包套小包
	 */
	public static final String TRANSPORT_TYPE_P = "P";

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
	 * 出库摘要 COE操作备注
	 */
	private String remark;
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
	 * SUCCESS：接单 SECURITY：包裹安全监测不通过 OTHER_REASON：其他异常
	 */
	private String checkResult;

	/**
	 * 转运类型:Z: 直接转运, J:集货转运
	 */
	private String transportType;
	/**
	 * 交易类型 顺丰专用; 用于区分:海淘;流连
	 * 
	 * LiuLian 流连 HaiTao 海淘 HeiKe 嘿客
	 */
	private String tradeType;

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}

	/**
	 * 回调审核是否成功 Y 成功 N或者空失败
	 */
	private String callbackSendCheckIsSuccess;

	/**
	 * 回调次数
	 */
	private Integer callbackSendCheckCount;

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

	public String getCallbackSendCheckIsSuccess() {
		return callbackSendCheckIsSuccess;
	}

	public void setCallbackSendCheckIsSuccess(String callbackSendCheckIsSuccess) {
		this.callbackSendCheckIsSuccess = callbackSendCheckIsSuccess;
	}

	public Integer getCallbackSendCheckCount() {
		return callbackSendCheckCount;
	}

	public void setCallbackSendCheckCount(Integer callbackSendCheckCount) {
		this.callbackSendCheckCount = callbackSendCheckCount;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public String getShipwayExtra1() {
		return shipwayExtra1;
	}

	public void setShipwayExtra1(String shipwayExtra1) {
		this.shipwayExtra1 = shipwayExtra1;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getShipwayExtra2() {
		return shipwayExtra2;
	}

	public void setShipwayExtra2(String shipwayExtra2) {
		this.shipwayExtra2 = shipwayExtra2;
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

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
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
