package com.coe.wms.model.product;

import java.io.Serializable;

/**
 * 商品信息
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

	/**
	 * 客户id
	 */
	private Long userIdOfCustomer;

	/**
	 * 是否需要生产批次 Y || N
	 */
	private String isNeedBatchNo;

	/**
	 * 规格型号
	 */
	private String model;

	/**
	 * 商品sku (对应顺丰文档中的skuId)
	 * 
	 * 2014-12-12 ,顺丰文档中的skuId才是sku的意思, sku反而是商品条码(barcode)
	 * 系统在入库订单,出库订单,表的sku字段保存的是 顺丰文档的sku字段,即(商品条码), skuNo字段保存的是顺丰skuId字段,即真正的sku
	 */
	private String sku;

	/**
	 * 商品条码(对应顺丰文档中的sku),, 以条码为单位. 一个barcode 只有一个sku 一个sku可能有多个barcode
	 */
	private String barcode;
	
	/**
	 * 仓库为客户的商品sku 再次编制的sku
	 */
	private String warehouseSku;

	/**
	 * 商品名
	 */
	private String productName;

	/**
	 * 商品类型id
	 */
	private Long productTypeId;

	/**
	 * 体积
	 */
	private Double volume;

	/**
	 * 报关重量 KG
	 */
	private Double customsWeight;
	/**
	 * 价值币种
	 */
	private String currency;
	/**
	 * 报关价值 元
	 */
	private Double customsValue;

	/**
	 * 行邮税号 (ETK B2C渠道专用报关字段)
	 */
	private String taxCode;

	/**
	 * 原产地
	 */
	private String origin;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 创建时间
	 */
	private Long createdTime;

	/**
	 * 上次更改时间
	 */
	private Long lastUpdateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public String getIsNeedBatchNo() {
		return isNeedBatchNo;
	}

	public void setIsNeedBatchNo(String isNeedBatchNo) {
		this.isNeedBatchNo = isNeedBatchNo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSku() {
		return sku;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getWarehouseSku() {
		return warehouseSku;
	}

	public void setWarehouseSku(String warehouseSku) {
		this.warehouseSku = warehouseSku;
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

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getCustomsWeight() {
		return customsWeight;
	}

	public void setCustomsWeight(Double customsWeight) {
		this.customsWeight = customsWeight;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getCustomsValue() {
		return customsValue;
	}

	public void setCustomsValue(Double customsValue) {
		this.customsValue = customsValue;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
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

	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
}
