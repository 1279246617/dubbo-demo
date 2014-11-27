package com.coe.wms.service.product.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.model.product.Product;
import com.coe.wms.model.product.ProductType;
import com.coe.wms.model.user.User;
import com.coe.wms.service.product.IProductService;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;

@Service("productService")
public class ProductServiceImpl implements IProductService {

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Resource(name = "productDao")
	private IProductDao productDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Override
	public Pagination findAllProduct(Product product, Map<String, String> moreParam, Pagination page) {
		List<Product> productList = productDao.findAllProduct(product, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (Product pro : productList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", pro.getId());
			User user = userDao.getUserById(pro.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("productName", pro.getProductName());
			ProductType productType = productDao.getProductTypeById(pro.getProductTypeId());
			map.put("productTypeName", productType.getProductTypeName());
			map.put("sku", pro.getSku());
			map.put("warehouseSku", pro.getWarehouseSku());
			map.put("remark", pro.getRemark());
			map.put("currency", pro.getCurrency());
			map.put("customsWeight", pro.getCustomsWeight());
			map.put("isNeedBatchNo", pro.getIsNeedBatchNo());
			map.put("model", pro.getModel());
			map.put("customsValue", pro.getCustomsValue());
			map.put("origin", pro.getOrigin());
			if (pro.getLastUpdateTime() != null) {
				map.put("lastUpdateTime", DateUtil.dateConvertString(new Date(pro.getLastUpdateTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (pro.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(pro.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("taxCode", pro.getTaxCode());
			map.put("volume", pro.getVolume());
			list.add(map);
		}
		page.total = productDao.countProduct(product, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public ProductType getProductTypeById(Long typeId) {
		return productDao.getProductTypeById(typeId);
	}

	@Override
	public Product getProductById(Long id) {
		return productDao.getProductById(id);
	}

}
