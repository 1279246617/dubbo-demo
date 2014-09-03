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

	private Long id;

	/**
	 * 订单所属客户id
	 */
	private Long userId;

	/**
	 * 订单所在大包号(等于批次)
	 */
	private String packageNo;

	private Integer status;

	/**
	 * 创建时间(同批次的订单创建时间完成相同)
	 */
	private Long createdTime;

	/**
	 * 参考编号
	 */
	private String referenceNo;

	/**
	 * 追踪号码
	 */
	private String trackingNo;

	private String channelNo;

	/**
	 * 报关信息类型 文件;礼物;包裹;其他
	 */
	private String customsType;

	/**
	 * 发件人id
	 */
	private Long senderId;

	/**
	 * 运输代码
	 */
	private String shipwayCode;

	/**
	 * 用于搜索其他信息的字段 一般放 物品sku 等比较常用 但是字段比较小的信息 目前用于搜索:sku , 国家,邮编
	 */
	private String search;

	/**
	 * 备注.
	 */
	private String remark;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public String getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}

	public String getCustomsType() {
		return customsType;
	}

	public void setCustomsType(String customsType) {
		this.customsType = customsType;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getShipwayCode() {
		return shipwayCode;
	}

	public void setShipwayCode(String shipwayCode) {
		this.shipwayCode = shipwayCode;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
