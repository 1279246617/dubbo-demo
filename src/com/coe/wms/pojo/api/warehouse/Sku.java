package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class Sku implements Serializable {

	private static final long serialVersionUID = 551558947068185258L;

	private String skuCode;

	private String skuName;

	private Double skuUnitPrice;

	private String skuPriceCurrency;

	/**
	 * sku 入库数量
	 */
	private Integer skuInBoundQty;

	/**
	 * 入库时间
	 */
	private String skuBoundTime;

	/**
	 * 入库清点时间
	 */
	private String skuCheckTime;

	/**
	 * 入库清点数量
	 */
	private Integer skuCheckQty;

	private Double skuNetWeight;

	public Double getSkuNetWeight() {
		return skuNetWeight;
	}

	public void setSkuNetWeight(Double skuNetWeight) {
		this.skuNetWeight = skuNetWeight;
	}

	/**
	 * 预报数量
	 */
	private Integer skuQty;

	private String skuRemark;

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public String getSkuName() {
		return skuName;
	}

	public Double getSkuUnitPrice() {
		return skuUnitPrice;
	}

	public String getSkuCheckTime() {
		return skuCheckTime;
	}

	public void setSkuCheckTime(String skuCheckTime) {
		this.skuCheckTime = skuCheckTime;
	}

	public Integer getSkuCheckQty() {
		return skuCheckQty;
	}

	public void setSkuCheckQty(Integer skuCheckQty) {
		this.skuCheckQty = skuCheckQty;
	}

	public void setSkuUnitPrice(Double skuUnitPrice) {
		this.skuUnitPrice = skuUnitPrice;
	}

	public String getSkuPriceCurrency() {
		return skuPriceCurrency;
	}

	public Integer getSkuInBoundQty() {
		return skuInBoundQty;
	}

	public void setSkuInBoundQty(Integer skuInBoundQty) {
		this.skuInBoundQty = skuInBoundQty;
	}

	public void setSkuPriceCurrency(String skuPriceCurrency) {
		this.skuPriceCurrency = skuPriceCurrency;
	}

	public String getSkuBoundTime() {
		return skuBoundTime;
	}

	public void setSkuBoundTime(String skuBoundTime) {
		this.skuBoundTime = skuBoundTime;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Integer getSkuQty() {
		return skuQty;
	}

	public void setSkuQty(Integer skuQty) {
		this.skuQty = skuQty;
	}

	public String getSkuRemark() {
		return skuRemark;
	}

	public void setSkuRemark(String skuRemark) {
		this.skuRemark = skuRemark;
	}
}
