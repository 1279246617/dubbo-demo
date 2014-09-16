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
	private Long userId;

	/**
	 * 大包号 同客户下, packageNo 不可重复
	 */
	private String packageNo;

	/**
	 * 大包到货时,贴的运单号
	 * 
	 * 要求客户预报的时候 大包头程 运单号 对应一个大包
	 */
	private String packageTrackingNo;

	/**
	 * 大包下的产品数量(统计packageItem得到)
	 * 
	 * 可空
	 */
	private int smallPackageQuantity;

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
	 * 已经到货入库的商品数量
	 */
	private Integer receivedQuantity;

	/**
	 * 创建时间(不代表收货时间)
	 */
	private Long createdTime;

	/**
	 * 入库订单物品明细
	 * 
	 */
	private List<InWarehouseOrderItem> itemList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getReceivedQuantity() {
		return receivedQuantity;
	}

	public void setReceivedQuantity(Integer receivedQuantity) {
		this.receivedQuantity = receivedQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getPackageTrackingNo() {
		return packageTrackingNo;
	}

	public void setPackageTrackingNo(String packageTrackingNo) {
		this.packageTrackingNo = packageTrackingNo;
	}

	public int getSmallPackageQuantity() {
		return smallPackageQuantity;
	}

	public void setSmallPackageQuantity(int smallPackageQuantity) {
		this.smallPackageQuantity = smallPackageQuantity;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setWeight(Double weight) {
		this.weight = weight;
	}
}
