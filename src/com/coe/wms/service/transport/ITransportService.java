package com.coe.wms.service.transport;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.Shipway;
import com.coe.wms.model.warehouse.transport.Order;
import com.coe.wms.model.warehouse.transport.OrderStatus;
import com.coe.wms.model.warehouse.transport.FirstWaybill;
import com.coe.wms.model.warehouse.transport.FirstWaybillItem;
import com.coe.wms.model.warehouse.transport.FirstWaybillOnShelf;
import com.coe.wms.model.warehouse.transport.FirstWaybillStatus;
import com.coe.wms.model.warehouse.transport.OutWarehousePackage;
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
	 * 转运订单确认出库
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceConfirmTransportOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 获取所有转运订单状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<OrderStatus> findAllOrderStatus() throws ServiceException;

	/**
	 * 获取所有转运订单状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<FirstWaybillStatus> findAllFirstWaybillStatus() throws ServiceException;

	public List<Shipway> findAllShipway() throws ServiceException;

	/**
	 * 获取转运订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getOrderData(Order order, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public Pagination getFirstWaybillData(FirstWaybill firstWaybill, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public Pagination getFirstWaybillOnShelfData(FirstWaybillOnShelf param, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public List<Map<String, Object>> getFirstWaybillItems(Long orderId) throws ServiceException;

	public List<FirstWaybillItem> getFirstWaybillItemsByFirstWaybillId(Long firstWaybillId) throws ServiceException;

	/**
	 * 审核转运订单
	 * 
	 * @param orderIds
	 * @param checkResult
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> checkOrder(String orderIds, String checkResult, Long userIdOfOperator) throws ServiceException;

	/**
	 * 收货时 输入跟踪号 后查询转运订单
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public List<Map<String, String>> checkReceivedFirstWaybill(FirstWaybill firstWaybill);

	public Map<String, String> submitInWarehouse(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long inWarehouseOrderId);

	public Map<String, String> orderSubmitWeight(Long userIdOfOperator, Long orderId, Double weight);

	public Map<String, String> saveFirstWaybillOnShelves(Long userIdOfOperator, Long firstWaybillId, String seatCode);

	public Map<String, String> orderWeightSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator);

	public Map<String, Object> outWarehousePackageEnterCoeTrackingNo(String coeTrackingNo);

	public Map<String, String> checkOutWarehousePackage(String trackingNo, Long userIdOfOperator, Long coeTrackingNoId, String coeTrackingNo, String addOrSub, String orderIds) throws ServiceException;

	/**
	 * 出库建包
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> outWarehousePackageConfirm(String coeTrackingNo, Long coeTrackingNoId, String orderIds, Long userIdOfOperator) throws ServiceException;

	/**
	 * 出库确认
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> outWarehouseShippingConfirm(String coeTrackingNo, Long userIdOfOperator) throws ServiceException;

	/**
	 * 复核小包
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> checkFirstWaybill(Long orderId, String trackingNo) throws ServiceException;

	/**
	 * 获取建包记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getOutWarehousePackageData(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam, Pagination page);

	public List<Map<String, String>> getOutWarehousePackageItemByOutWarehousePackageId(Long packageId);

	public Map<String, String> saveOutWarehousePackageRemark(String remark, Long id) throws ServiceException;

	public Map<String, String> applyTrackingNo(Long orderId) throws ServiceException;
}
