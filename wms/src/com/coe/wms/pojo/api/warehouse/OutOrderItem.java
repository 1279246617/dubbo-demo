package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

/**
 * pojo 商品信息, 出库时 物品信息
 * 
 * @author Administrator
 * 
 */
public class OutOrderItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9168041737254441815L;
	/**
	 * 商品ID(SKU)
	 * 
	 * 在入库和出库时都有
	 */
	private String itemId;

	/**
	 * 商品件数 出库时提供
	 */
	private Integer itemQuantity;

	/**
	 * 商品单价 出库时提供
	 */
	private Double itemPrice;

	/**
	 * 商品单价币种（默认CNY） 出库时提供
	 */
	private String itemCurrency;

	/**
	 * 商品单件重量（单位G） 出库时提供
	 */
	private Double itemWeight;
	/**
	 * 商品税号 出库时提供
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
