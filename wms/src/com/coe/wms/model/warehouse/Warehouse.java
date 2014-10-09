package com.coe.wms.model.warehouse;

import java.io.Serializable;

public class Warehouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7911370477173333958L;
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 仓库名称
	 */
	private String warehouseName;
	
	/**
	 * 仓库编码(与顺丰API 仓库编码相同)
	 */
	private String warehouseNo;

	private String remark;

	private String countryCode;

	private String countryName;

	private String stateOrProvince;

	private String city;

	private String postalCode;

	private String addressLine1;

	private String addressLine2;

	/**
	 * 长度单位代码
	 */
	private String lengthUnitCode;

	/**
	 * 长
	 */
	private Double length;

	/**
	 * 高
	 */
	private Double height;

	/**
	 * 宽
	 */
	private Double width;

	/**
	 * 平方米
	 */
	private Double meters;

	private Long createdTime;

	private Long createdByUserId;

	private Long lastModifieTime;

	private Long lastModifieByUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStateOrProvince() {
		return stateOrProvince;
	}

	public void setStateOrProvince(String stateOrProvince) {
		this.stateOrProvince = stateOrProvince;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getLengthUnitCode() {
		return lengthUnitCode;
	}

	public void setLengthUnitCode(String lengthUnitCode) {
		this.lengthUnitCode = lengthUnitCode;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getMeters() {
		return meters;
	}

	public void setMeters(Double meters) {
		this.meters = meters;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getCreatedByUserId() {
		return createdByUserId;
	}

	public void setCreatedByUserId(Long createdByUserId) {
		this.createdByUserId = createdByUserId;
	}

	public Long getLastModifieTime() {
		return lastModifieTime;
	}

	public void setLastModifieTime(Long lastModifieTime) {
		this.lastModifieTime = lastModifieTime;
	}

	public Long getLastModifieByUserId() {
		return lastModifieByUserId;
	}

	public void setLastModifieByUserId(Long lastModifieByUserId) {
		this.lastModifieByUserId = lastModifieByUserId;
	}

	public String getWarehouseNo() {
		return warehouseNo;
	}

	public void setWarehouseNo(String warehouseNo) {
		this.warehouseNo = warehouseNo;
	}
}
