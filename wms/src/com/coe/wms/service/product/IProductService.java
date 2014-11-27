package com.coe.wms.service.product;

import java.util.Map;

import com.coe.wms.model.product.Product;
import com.coe.wms.model.product.ProductType;
import com.coe.wms.util.Pagination;

public interface IProductService {
	public Pagination findAllProduct(Product product,Map<String, String> moreParam, Pagination page);
	
	public Product getProductById(Long id);
}
