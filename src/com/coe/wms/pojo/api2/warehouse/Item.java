package com.coe.wms.pojo.api2.warehouse;

public class Item {
	/**
	 * 商品sku
	 */
	private String sku;
	/**
	 * 商品数量
	 */
	private Integer quantity;
	private String skuName;
	private Double skuUnitPrice;
	private String skuPriceCurrency;
	private Double skuNetWeight;
	private String remark;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Double getSkuUnitPrice() {
		return skuUnitPrice;
	}

	public void setSkuUnitPrice(Double skuUnitPrice) {
		this.skuUnitPrice = skuUnitPrice;
	}

	public String getSkuPriceCurrency() {
		return skuPriceCurrency;
	}

	public void setSkuPriceCurrency(String skuPriceCurrency) {
		this.skuPriceCurrency = skuPriceCurrency;
	}

	public Double getSkuNetWeight() {
		return skuNetWeight;
	}

	public void setSkuNetWeight(Double skuNetWeight) {
		this.skuNetWeight = skuNetWeight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
