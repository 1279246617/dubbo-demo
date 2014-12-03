package com.coe.wms.dao.product;

import java.util.List;

import com.coe.wms.model.product.ProductType;

public interface IProductTypeDao {

	public ProductType getProductTypeById(Long id);

	public List<ProductType> getAllProductType();

	public List<ProductType> getProductTypeByCustomerId(Long userIdOfCustomer);
}
