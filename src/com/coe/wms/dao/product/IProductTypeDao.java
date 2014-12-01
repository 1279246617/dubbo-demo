package com.coe.wms.dao.product;

import com.coe.wms.model.product.ProductType;

public interface IProductTypeDao {
	public ProductType getProductTypeById(Long id);
	
	public Long getProductTypeIdByName(String productType);

}
