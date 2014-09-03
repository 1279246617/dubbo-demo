package com.coe.wms.model.warehouse;

import java.io.Serializable;

public class ShelvesType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5266286196108854637L;

	/**
	 * 主键
	 */
	private Long id;

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
	 * 货架类型名称
	 */
	private String name;

	/**
	 * 货位数
	 */
	private int seats;

	/**
	 * 货架类型品牌
	 */
	private String brand;

	/**
	 * 货架类型备注
	 */
	private String remark;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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
}
