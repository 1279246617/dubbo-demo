package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;

/**
 * 出库详情单
 * 
 * @author Administrator
 * 
 */
public class OutWarehouseOrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2079149253927077125L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 入库主单id
	 */
	private Long outWarehouseOrderId;

	/**
	 * 数量
	 */
	private Integer quantity;

	/**
	 * sku
	 */
	private String sku;

	/**
	 * 可用于报关描述(申报品名)
	 */
	private String skuName;

	/**
	 * 可用于报关币种
	 */
	private String skuPriceCurrency;

	/**
	 * sku单价 可用于报关价值
	 */
	private Double skuUnitPrice;

	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getSkuPriceCurrency() {
		return skuPriceCurrency;
	}

	public void setSkuPriceCurrency(String skuPriceCurrency) {
		this.skuPriceCurrency = skuPriceCurrency;
	}

	public Double getSkuUnitPrice() {
		return skuUnitPrice;
	}

	public void setSkuUnitPrice(Double skuUnitPrice) {
		this.skuUnitPrice = skuUnitPrice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOutWarehouseOrderId() {
		return outWarehouseOrderId;
	}

	public void setOutWarehouseOrderId(Long outWarehouseOrderId) {
		this.outWarehouseOrderId = outWarehouseOrderId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
