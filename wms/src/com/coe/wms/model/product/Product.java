package com.coe.wms.model.product;

import java.io.Serializable;

/**
 * 产品信息
 * 
 * 
 * @author Administrator
 * 
 */
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5471504971504492367L;

	/**
	 * 主键
	 */
	private Long id;

	private String productName;

	private Long productTypeId;

	private String sku;

	private String color;

	private String mainImage;

	private String images;

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
	 * 重量 KG
	 */
	private Double weight;

	/**
	 * 价值 元
	 */
	private Double value;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 价值币种
	 */
	private String currency;

	/**
	 * 报关重量 KG
	 */
	private Double customsWeight;

	/**
	 * 报关价值 元
	 */
	private Double customsValue;

	/**
	 * 海关编码
	 */
	private String hsCode;

	/**
	 * 行邮税号 (ETK B2C渠道)
	 */
	private String taxCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(Long productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCustomsWeight() {
		return customsWeight;
	}

	public void setCustomsWeight(Double customsWeight) {
		this.customsWeight = customsWeight;
	}

	public Double getCustomsValue() {
		return customsValue;
	}

	public void setCustomsValue(Double customsValue) {
		this.customsValue = customsValue;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
}
