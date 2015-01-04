package com.coe.wms.service.transport;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.transport.OrderPackage;
import com.coe.wms.model.warehouse.transport.OrderPackageStatus;
import com.coe.wms.util.Pagination;

/**
 * 转运 api service层
 * 
 * @author Administrator
 * 
 */
public interface IOrderPackageService {

	static final Logger logger = Logger.getLogger(IOrderPackageService.class);

	/**
	 * 获取所有转运订单状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<OrderPackageStatus> findAllOrderPackageStatus() throws ServiceException;

	/**
	 * 获取转运订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getOrderPackageData(OrderPackage orderPackage, Map<String, String> moreParam, Pagination page) throws ServiceException;
}
