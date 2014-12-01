package com.coe.wms.service.product.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coe.wms.dao.product.IProductTypeDao;
import com.coe.wms.model.product.ProductType;
import com.coe.wms.service.product.IProductTypeService;

@Service("productTypeService")
public class ProductTypeSerivceImpl implements IProductTypeService {
	
	@Resource(name="productTypeDao")
	private IProductTypeDao productTypeDao;

	@Override
	public ProductType getProductTypeById(Long typeId) {
		return productTypeDao.getProductTypeById(typeId);
	}

	@Override
	public Long getProductTypeIdByName(String productType) {
		return productTypeDao.getProductTypeIdByName(productType);
	}

}
