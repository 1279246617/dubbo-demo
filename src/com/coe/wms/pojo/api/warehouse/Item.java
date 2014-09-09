package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

/**
 * pojo 商品信息, 把顺丰入库和出库时 物品信息合并了
 * 
 * @author Administrator
 * 
 */
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9168041737254441815L;
	/**
	 * 商品ID(SKU)
	 */
	private String itemId;

	/**
	 * 商品件数
	 */
	private Integer itemQuantity;

	/**
	 * 商品单价
	 */
	private Double itemPrice;

	/**
	 * 商品单价币种（默认CNY）
	 */
	private String itemCurrency;

	/**
	 * 商品单件重量（单位G）
	 */
	private Double itemWeight;
	/**
	 * 商品税号
	 */
	private String itemTax;

	/**
	 * 供应商
	 */
	private String supplier;

	/**
	 * 商品数量
	 */
	private int count;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemCurrency() {
		return itemCurrency;
	}

	public void setItemCurrency(String itemCurrency) {
		this.itemCurrency = itemCurrency;
	}

	public Double getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(Double itemWeight) {
		this.itemWeight = itemWeight;
	}

	public String getItemTax() {
		return itemTax;
	}

	public void setItemTax(String itemTax) {
		this.itemTax = itemTax;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
