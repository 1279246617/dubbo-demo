package com.coe.wms.service.transport;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.transport.BigPackage;
import com.coe.wms.model.warehouse.transport.BigPackageStatus;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.util.Pagination;

/**
 * 转运 api service层
 * 
 * @author Administrator
 * 
 */
public interface ITransportService {

	static final Logger logger = Logger.getLogger(ITransportService.class);

	/**
	 * 创建转运订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 获取所有转运订单状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<BigPackageStatus> findAllBigPackageStatus() throws ServiceException;

	/**
	 * 获取转运订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getBigPackageData(BigPackage bigPackage, Map<String, String> moreParam, Pagination page);
}
