package com.coe.wms.service.product;

import java.util.List;

import com.coe.wms.model.product.ProductType;

public interface IProductTypeService {

	public ProductType getProductTypeById(Long typeId);

	public List<ProductType> getAllProductType();

	public List<ProductType> getProductTypeByCustomerId(Long userIdOfCustomer);
}
