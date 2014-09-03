package com.coe.wms.model.warehouse.storage;

import java.io.Serializable;

/**
 * 大包
 * 
 * 仓配服务
 * @author Administrator
 *
 */
public class Package implements Serializable {

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
	 * 大包下的产品数量
	 */
	private int smallPackageQuantity;

	/**
	 * 大包重量
	 */
	private double weight;

	private String remark;

	/**
	 * 创建时间(不代表收货时间)
	 */
	private Long createdTime;

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

	public double getWeight() {
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
}
