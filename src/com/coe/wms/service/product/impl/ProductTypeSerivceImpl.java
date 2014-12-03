package com.coe.wms.service.product.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.coe.wms.dao.product.IProductTypeDao;
import com.coe.wms.model.product.ProductType;
import com.coe.wms.service.product.IProductTypeService;

@Service("productTypeService")
public class ProductTypeSerivceImpl implements IProductTypeService {

	@Resource(name = "productTypeDao")
	private IProductTypeDao productTypeDao;

	@Override
	public ProductType getProductTypeById(Long typeId) {
		return productTypeDao.getProductTypeById(typeId);
	}

	@Override
	public List<ProductType> getAllProductType() {
		return productTypeDao.getAllProductType();
	}

	@Override
	public List<ProductType> getProductTypeByCustomerId(Long userIdOfCustomer) {
		return productTypeDao.getProductTypeByCustomerId(userIdOfCustomer);
	}
}
