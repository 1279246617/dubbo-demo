package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 
 * @author yechao
 * @date 2014年11月3日
 */
public class LittlePackageItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6403535606524196497L;

	/**
	 * 主键
	 */
	private Long id;

	private Long bigPackageId;

	private Long littlePackageId;
	/**
	 * sku
	 */
	private String sku;
	/**
	 * sku编号 等于顺丰的商品编号
	 */
	private String skuNo;
	/**
	 * 数量
	 */
	private Integer quantity;
	/**
	 * 商品规格
	 */
	private String specification;
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
	 * sku 重量
	 */
	private Double skuNetWeight;

	/**
	 * 备注
	 */
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBigPackageId() {
		return bigPackageId;
	}

	public void setBigPackageId(Long bigPackageId) {
		this.bigPackageId = bigPackageId;
	}

	public Long getLittlePackageId() {
		return littlePackageId;
	}

	public void setLittlePackageId(Long littlePackageId) {
		this.littlePackageId = littlePackageId;
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

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
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
