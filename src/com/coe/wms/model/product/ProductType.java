package com.coe.wms.model.product;

import java.io.Serializable;

/**
 * 商品类型
 * 
 * 
 * @author Administrator
 * 
 */
public class ProductType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5471504971504492367L;

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 客户id
	 */
	private Long userIdOfCustomer;
	
	private String productTypeName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserIdOfCustomer() {
		return userIdOfCustomer;
	}

	public void setUserIdOfCustomer(Long userIdOfCustomer) {
		this.userIdOfCustomer = userIdOfCustomer;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
}
