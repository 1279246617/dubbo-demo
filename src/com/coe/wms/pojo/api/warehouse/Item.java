package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6272810965144780494L;
	private String brand;
	private String itemCategoryName;
	private String itemId;
	private String itemName;
	private Integer itemQuantity;
	private String itemRemark;
	private String itemUnitPrice;
	private String material;

	private String model;

	private String netWeight;

	private String particles;

	private String qtyUnit;

	private String size;

	private String specification;

	private String used;

	private String usedNew;

	public String getBrand() {
		return brand;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public String getItemId() {
		return itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public Integer getItemQuantity() {
		return itemQuantity;
	}

	public String getItemRemark() {
		return itemRemark;
	}

	public String getItemUnitPrice() {
		return itemUnitPrice;
	}

	public String getMaterial() {
		return material;
	}

	public String getModel() {
		return model;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public String getParticles() {
		return particles;
	}

	public String getQtyUnit() {
		return qtyUnit;
	}

	public String getSize() {
		return size;
	}

	public String getSpecification() {
		return specification;
	}

	public String getUsed() {
		return used;
	}

	public String getUsedNew() {
		return usedNew;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemQuantity(Integer itemQuantity) {
		this.itemQuantity = itemQuantity;
	}

	public void setItemRemark(String itemRemark) {
		this.itemRemark = itemRemark;
	}

	public void setItemUnitPrice(String itemUnitPrice) {
		this.itemUnitPrice = itemUnitPrice;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public void setParticles(String particles) {
		this.particles = particles;
	}

	public void setQtyUnit(String qtyUnit) {
		this.qtyUnit = qtyUnit;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public void setUsedNew(String usedNew) {
		this.usedNew = usedNew;
	}
}
