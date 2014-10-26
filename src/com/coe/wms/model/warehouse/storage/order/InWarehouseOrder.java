package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 
 * 大包 入库订单(客户发预报通知将会入库,但不是真实入库记录)
 * 
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
public class InWarehouseOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8782311124874987640L;

	private Long id;

	/**
	 * 订单所属客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 订单所属操作员id
	 */
	private Long userIdOfOperator;

	/**
	 * 如果物流类型为 2或3系统会生成一个虚拟承运商编号
	 */
	private String carrierCode;

	/**
	 * 运单类型(运单,空运,自运)
	 */
	private String logisticsType;
	/**
	 * 大包到货时,贴的运单号
	 * 
	 * 要求客户预报的时候 大包头程 跟踪单号 对应一个大包
	 */
	private String trackingNo;
		
	/**
	 * 客户订单号 (跟踪号和客户订单号 同时重复时 不允许新建)
	 */
	private String customerReferenceNo;
	/**
	 * 大包重量
	 */
	private Double weight;

	private String remark;
	/**
	 * 大包状态: 客户预报的大包 要知道 是 已经预报, 部分入库, 全部已入库 无需知道出库状态;
	 */
	private String status;

	/**
	 * 创建时间(不代表收货时间)
	 */
	private Long createdTime;

	/**
	 * 预报仓库id
	 */
	private Long warehouseId;

	/**
	 * 入库订单物品明细
	 * 
	 */
	private List<InWarehouseOrderItem> itemList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
 
	public String getCustomerReferenceNo() {
		return customerReferenceNo;
	}

	public void setCustomerReferenceNo(String customerReferenceNo) {
		this.customerReferenceNo = customerReferenceNo;
	}
	
	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getRemark() {
		return remark;
	}

	public String getLogisticsType() {
		return logisticsType;
	}

	public void setLogisticsType(String logisticsType) {
		this.logisticsType = logisticsType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public List<InWarehouseOrderItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<InWarehouseOrderItem> itemList) {
		this.itemList = itemList;
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

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
}
