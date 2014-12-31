package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 
 * 
 * 转运订单(到货信息.跟踪号)
 * 
 * @author Administrator
 * 
 */
public class FirstWaybill implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6990111084701745718L;

	private Long id;

	/**
	 * 大包id
	 */
	private Long orderId;
	/**
	 * 订单所属客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 订单所属操作员id
	 */
	private Long userIdOfOperator;

	/**
	 * 预报仓库id
	 */
	private Long warehouseId;

	/**
	 * 承运商编号
	 */
	private String carrierCode;

	/**
	 * 到货时,贴的运单号
	 * 
	 */
	private String trackingNo;

	/**
	 * 客户销售订单号 等于顺丰文档中的销售订单号(poNo) 回传入库时,需要
	 * trackingNo,poNo,carrierCode,tradeOrderId
	 */
	private String poNo;

	/**
	 * 货架号, 收货时分配的货位号 要求上架操作时必须按此货位上架
	 */
	private String seatCode;
	
	/**
	 * 未入库,已入库,未上架,已上架,已下架
	 */
	private String status;

	/**
	 * 创建时间(不代表收货时间)
	 */
	private Long createdTime;

	/**
	 * 收货时间
	 */
	private Long receivedTime;

	private String remark;

	/**
	 * 回调是否成功 Y 成功 N或者空失败
	 */
	private String callbackIsSuccess;

	/**
	 * 回调次数
	 */
	private Integer callbackCount;

	/**
	 * 转运类型:Z: 直接转运, J:集货转运
	 */
	private String transportType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
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

	public String getCallbackIsSuccess() {
		return callbackIsSuccess;
	}

	public void setCallbackIsSuccess(String callbackIsSuccess) {
		this.callbackIsSuccess = callbackIsSuccess;
	}

	public Integer getCallbackCount() {
		return callbackCount;
	}

	public void setCallbackCount(Integer callbackCount) {
		this.callbackCount = callbackCount;
	}

	public String getTransportType() {
		return transportType;
	}

	public void setTransportType(String transportType) {
		this.transportType = transportType;
	}

	public void setUserIdOfOperator(Long userIdOfOperator) {
		this.userIdOfOperator = userIdOfOperator;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getPoNo() {
		return poNo;
	}

	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Long receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
