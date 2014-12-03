package com.coe.wms.service.product;

import java.util.Map;

import com.coe.wms.model.product.Product;
import com.coe.wms.util.Pagination;

public interface IProductService {
	public Pagination findProduct(Product product, Map<String, String> moreParam, Pagination page);

	public Product getProductById(Long id);

	public Map<String, String> deleteProductById(Long productId);

	public Map<String, String> deleteProductByIds(String ids);

	public Map<String, String> updateProductById(Product product);

	public Map<String, String> saveAddProduct(Product product);

}
