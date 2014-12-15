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
import com.coe.wms.dao.product.IProductTypeDao;
import com.coe.wms.dao.unit.ICurrencyDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.model.product.Product;
import com.coe.wms.model.product.ProductType;
import com.coe.wms.model.unit.Currency;
import com.coe.wms.model.user.User;
import com.coe.wms.service.product.IProductService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;

@Service("productService")
public class ProductServiceImpl implements IProductService {

	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Resource(name = "productDao")
	private IProductDao productDao;

	@Resource(name = "productTypeDao")
	private IProductTypeDao productTypeDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "currencyDao")
	private ICurrencyDao currencyDao;

	@Override
	public Pagination findProduct(Product param, Map<String, String> moreParam, Pagination page) {
		List<Product> productList = productDao.findProduct(param, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (Product product : productList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", product.getId());
			User user = userDao.getUserById(product.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("productName", product.getProductName());
			if (product.getProductTypeId() != null) {
				ProductType productType = productTypeDao.getProductTypeById(product.getProductTypeId());
				map.put("productTypeName", productType.getProductTypeName());
			}
			map.put("sku", product.getSku());
			map.put("barcode", product.getBarcode());
			map.put("warehouseSku", product.getWarehouseSku());
			map.put("remark", product.getRemark());
			if (StringUtil.isNotNull(product.getCurrency())) {
				Currency currency = currencyDao.findCurrencyByCode(product.getCurrency());
				if (currency != null) {
					map.put("currency", currency.getCn());
				}
			}
			map.put("customsWeight", product.getCustomsWeight());
			map.put("isNeedBatchNo", product.getIsNeedBatchNo());
			map.put("model", product.getModel());
			map.put("customsValue", product.getCustomsValue());
			map.put("origin", product.getOrigin());
			if (product.getLastUpdateTime() != null) {
				map.put("lastUpdateTime", DateUtil.dateConvertString(new Date(product.getLastUpdateTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			if (product.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(product.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			map.put("taxCode", product.getTaxCode());
			map.put("volume", product.getVolume());
			list.add(map);
		}
		page.total = productDao.countProduct(param, moreParam);
		page.rows = list;
		return page;
	}

	@Override
	public Product getProductById(Long id) {
		return productDao.getProductById(id);
	}

	@Override
	public Map<String, String> deleteProductById(Long productId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (productId == null) {
			map.put(Constant.MESSAGE, "商品id为空，无法处理");
			return map;
		}
		int updateCount = productDao.deleteProductById(productId);
		if (updateCount > 0) {
			map.put(Constant.STATUS, Constant.SUCCESS);
			map.put(Constant.MESSAGE, "删除商品成功");
		} else {
			map.put(Constant.MESSAGE, "执行数据库更新失败，删除失败");
		}
		return map;
	}

	/**
	 * 更新商品
	 * 
	 */
	@Override
	public Map<String, String> updateProduct(Product product) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(product.getSku())) {
			map.put(Constant.MESSAGE, "请输入商品SKU");
			return map;
		}
		if (StringUtil.isNull(product.getProductName())) {
			map.put(Constant.MESSAGE, "请输入商品名称");
			return map;
		}
		if (StringUtil.isNull(product.getWarehouseSku())) {
			map.put(Constant.MESSAGE, "请输入仓库SKU");
			return map;
		}
		if (product.getUserIdOfCustomer() == null) {
			map.put(Constant.MESSAGE, "请输入正确的客户帐号");
			return map;
		}
		Product oldProduct = productDao.getProductById(product.getId());
		if (!StringUtil.isEqual(oldProduct.getBarcode(), product.getBarcode())) {
			// 如果更改商品条码,需判断该商品条码是否已被其他商品使用
			Product productParam = new Product();
			productParam.setBarcode(product.getBarcode());
			productParam.setUserIdOfCustomer(product.getUserIdOfCustomer());
			if (productDao.countProduct(productParam, null) >= 1) {
				map.put(Constant.MESSAGE, "修改失败,该商品条码已存在");
				return map;
			}
		}
		long count = productDao.updateProduct(product);
		if (count > 0) {
			map.put(Constant.MESSAGE, "更新商品成功");
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.MESSAGE, "更新商品失败");
		}
		return map;
	}

	/**
	 * 新增商品
	 */
	@Override
	public Map<String, String> addProduct(Product product) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(product.getSku())) {
			map.put(Constant.MESSAGE, "请输入商品SKU");
			return map;
		}
		if (StringUtil.isNull(product.getProductName())) {
			map.put(Constant.MESSAGE, "请输入商品名称");
			return map;
		}
		if (StringUtil.isNull(product.getWarehouseSku())) {
			map.put(Constant.MESSAGE, "请输入仓库SKU");
			return map;
		}
		if (product.getUserIdOfCustomer() == null) {
			map.put(Constant.MESSAGE, "请输入正确的客户帐号");
			return map;
		}
		// 查商品条码是否重复 (一个sku对应多个商品条码,所以sku可以重复,商品条码不可以重复)
		Product productParam = new Product();
		productParam.setBarcode(product.getBarcode());
		productParam.setUserIdOfCustomer(product.getUserIdOfCustomer());
		if (productDao.countProduct(productParam, null) >= 1) {
			map.put(Constant.MESSAGE, "添加失败,该商品条码已存在");
			return map;
		}
		long count = productDao.addProduct(product);
		if (count > 0) {
			map.put(Constant.MESSAGE, "新增商品成功");
			map.put(Constant.STATUS, Constant.SUCCESS);
		} else {
			map.put(Constant.MESSAGE, "新增商品失败");
		}
		return map;
	}

	@Override
	public Map<String, String> deleteProductByIds(String ids) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.FAIL, Constant.FAIL);
		if (StringUtil.isNull(ids)) {
			map.put(Constant.MESSAGE, "请最少选择一条数据");
			return map;
		}
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		int count = productDao.deleteProductByIds(ids);
		map.put(Constant.STATUS, Constant.SUCCESS);
		map.put(Constant.MESSAGE, "删除成功" + count + "个商品");
		return map;
	}
}
