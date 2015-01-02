package com.coe.wms.service.transport;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.shipway.Shipway;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderStatus;
import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.util.Pagination;

/**
 * 转运 api service层
 * 
 * @author Administrator
 * 
 */
public interface IFirstWaybillService {

	static final Logger logger = Logger.getLogger(IFirstWaybillService.class);

	/**
	 * 获取所有转运订单状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<FirstWaybillStatus> findAllFirstWaybillStatus() throws ServiceException;

	public Pagination getFirstWaybillData(FirstWaybill firstWaybill, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public Pagination getFirstWaybillOnShelfData(FirstWaybillOnShelf param, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public List<Map<String, Object>> getFirstWaybillItems(Long orderId) throws ServiceException;

	public List<FirstWaybillItem> getFirstWaybillItemsByFirstWaybillId(Long firstWaybillId) throws ServiceException;

	/**
	 * 收货时 输入跟踪号 后查询转运订单
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public List<Map<String, String>> checkReceivedFirstWaybill(FirstWaybill firstWaybill);

	public Map<String, String> saveFirstWaybillOnShelves(Long userIdOfOperator, Long firstWaybillId, String seatCode);

	/**
	 * 复核小包
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> checkFirstWaybill(Long orderId, String trackingNo) throws ServiceException;

	public List<Map<String, String>> getOutWarehousePackageItemByOutWarehousePackageId(Long packageId);

}
