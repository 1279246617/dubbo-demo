package com.coe.wms.model.warehouse.storage.order;

import java.io.Serializable;
import java.util.Date;

import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;

/**
 * 出库详情单,打印捡货单时生成的货位信息
 * 
 * @author Administrator
 * 
 */
public class OutWarehouseOrderItemShelf implements Serializable {

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
	 * 批次
	 */
	private String batchNo;

	/**
	 * 可用于报关描述(申报品名)
	 */
	private String skuName;

	/**
	 * 商品规格
	 */
	private String specification;
	/**
	 * 可用于报关币种
	 */
	private String skuPriceCurrency;

	/**
	 * sku单价 可用于报关价值
	 */
	private Double skuUnitPrice;

	/**
	 * sku 重量
	 */
	private Double skuNetWeight;

	/**
	 * 货位
	 */
	private String seatCode;

	/**
	 * 是否已下架
	 */
	private String isDone;

	public Long getId() {
		return id;
	}

	public String getSkuName() {
		return skuName;
	}

	public Double getSkuNetWeight() {
		return skuNetWeight;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public void setSkuNetWeight(Double skuNetWeight) {
		this.skuNetWeight = skuNetWeight;
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

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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

	public String getSeatCode() {
		return seatCode;
	}

	public void setSeatCode(String seatCode) {
		this.seatCode = seatCode;
	}

	public String getIsDone() {
		return isDone;
	}

	public void setIsDone(String isDone) {
		this.isDone = isDone;
	}

	public static OutWarehouseOrderItemShelf createOutWarehouseOrderItemShelf(Long outWarehouseOrderId, int quantity, String seatCode, String sku, String skuName, Double skuNetWeight, String skuPriceCurrency, Double skuUnitPrice, String batchNo,
			String specification) {
		OutWarehouseOrderItemShelf itemShelf = new OutWarehouseOrderItemShelf();
		itemShelf.setOutWarehouseOrderId(outWarehouseOrderId);
		itemShelf.setQuantity(quantity);
		itemShelf.setSeatCode(seatCode);
		itemShelf.setSku(sku);
		itemShelf.setSkuName(skuName);
		itemShelf.setSkuNetWeight(skuNetWeight);
		itemShelf.setSkuPriceCurrency(skuPriceCurrency);
		itemShelf.setSkuUnitPrice(skuUnitPrice);
		itemShelf.setBatchNo(batchNo);
		itemShelf.setSpecification(specification);
		itemShelf.setIsDone(Constant.N);
		return itemShelf;
	}
}
