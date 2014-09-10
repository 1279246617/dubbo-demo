package com.coe.wms.pojo.api.warehouse;

import java.io.Serializable;

import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;

/**
 * pojo 商品信息, 顺丰入库 物品信息
 * 
 * @author Administrator
 * 
 */
public class InOrderItem implements Serializable {
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
	 * 供应商
	 * 
	 * 入库时提供
	 */
	private String supplier;

	/**
	 * 商品数量
	 * 
	 * 入库时提供. 至于和itemQuantity 重复, 是因为 顺丰的xml格式如此
	 */
	private int count;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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

	public InWarehouseOrderItem changeToPackageItem(Long packageId) {
		InWarehouseOrderItem packageItem = new InWarehouseOrderItem();
		packageItem.setPackageId(packageId);
		packageItem.setQuantity(this.getCount());
		packageItem.setSku(this.getItemId());
		return packageItem;
	}
}
