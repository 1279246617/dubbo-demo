package com.coe.wms.service.product;

import java.util.Map;

import com.coe.wms.model.product.Product;
import com.coe.wms.model.product.ProductType;
import com.coe.wms.util.Pagination;

public interface IProductService {
	public Pagination findProduct(Product product,Map<String, String> moreParam, Pagination page);
	
	public Product getProductById(Long id);
	
	public Map<String, String> deleteProductById(Long productId);
	
	public int updateProductById(Product product);
	
	public Map<String, String> saveAddProduct(String productName,Long productTypeId,Long userIdOfCustomer,String isNeedBatchNo,String sku,String warehouseSku,String model,Double volume,Double customsWeight,String currency,Double customsValue,String taxCode,String origin,String remark);
}
