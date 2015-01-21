package com.coe.wms.service.storage;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderReceiver;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderSender;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.record.OutWarehousePackage;
import com.coe.wms.util.Pagination;

/**
 * 
 * @author Administrator
 * 
 */
public interface IOutWarehouseOrderService {

	static final Logger logger = Logger.getLogger(IOutWarehouseOrderService.class);

	/**
	 * 获取建包记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getOutWarehousePackageData(OutWarehousePackage outWarehousePackage, Map<String, String> moreParam, Pagination page);

	/**
	 * 获取出库订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getOutWarehouseOrderData(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination page);

	public List<Map<String, String>> getOutWarehouseRecordItemMapByRecordId(Long recordId);

	public List<Map<String, String>> getOutWarehouseRecordItemByPackageId(Long packageId);

	/**
	 * 获取出库订单物品
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OutWarehouseOrderItem> getOutWarehouseOrderItem(Long orderId);
	
	public OutWarehouseOrderReceiver getOutWarehouseOrderReceiverByOrderId(Long orderId);

	public OutWarehouseOrderSender getOutWarehouseOrderSenderByOrderId(Long orderId);

	/**
	 * 审核出库订单
	 * 
	 * @param orderIds
	 * @param checkResult
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> checkOutWarehouseOrder(String orderIds, Integer checkResult, Long userIdOfOperator) throws ServiceException;

	/**
	 * 获取所有出库状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<OutWarehouseOrderStatus> findAllOutWarehouseOrderStatus() throws ServiceException;

	public OutWarehouseOrderStatus getOutWarehouseOrderStatusByCode(String stataus) throws ServiceException;

	/**
	 * 获取coe跟踪单号供出库发货界面使用
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public TrackingNo getCoeTrackingNoforOutWarehousePackage() throws ServiceException;

	public Map<String, String> checkOutWarehouseOrderPrintShipLabel(Long outWarehouseOrderId) throws ServiceException;

	/**
	 * 出库确认
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> outWarehouseShippingConfirm(String coeTrackingNo, Long userIdOfOperator) throws ServiceException;

	/**
	 * 出库建包
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> outWarehousePackageConfirm(String coeTrackingNo, Long coeTrackingNoId, Long userIdOfOperator, boolean isReturnNewCoeNo) throws ServiceException;

	/**
	 * 绑定
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> bindingOutWarehouseOrder(String trackingNo, Long userIdOfOperator, Long coeTrackingNoId, String coeTrackingNo) throws ServiceException;

	/**
	 * 解除绑定
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> unBindingOutWarehouseOrder(String trackingNo, Long userIdOfOperator, Long coeTrackingNoId, String coeTrackingNo) throws ServiceException;

	/**
	 * 出货重新输入coe交接单号
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> outWarehouseShippingEnterCoeTrackingNo(String coeTrackingNo) throws ServiceException;

	/**
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> outWarehouseSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator) throws ServiceException;

	public Map<String, Object> outWarehouseSubmitWeight(String customerReferenceNo, Double weight, Long userIdOfOperator) throws ServiceException;

	public Map<String, String> saveOutWarehousePackageRemark(String remark, Long id) throws ServiceException;

	public Map<String, String> executeSearchOutWarehouseOrder(String nos, String noType) throws ServiceException;

	public Map<String, String> applyTrackingNo(Long orderId) throws ServiceException;

}
