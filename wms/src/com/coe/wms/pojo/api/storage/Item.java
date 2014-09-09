package com.coe.wms.pojo.api.storage;

import java.io.Serializable;

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

}
