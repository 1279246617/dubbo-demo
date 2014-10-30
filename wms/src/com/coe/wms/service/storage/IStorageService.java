package com.coe.wms.service.storage;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.pojo.api.warehouse.EventBody;
import com.coe.wms.util.Pagination;

/**
 * 仓配 api service层
 * 
 * @author Administrator
 * 
 */
public interface IStorageService {

	static final Logger logger = Logger.getLogger(IStorageService.class);

	/**
	 * 保存入库订单 返回成功,失败,错误信息
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long inWarehouseOrderId);

	/**
	 * 保存入库明细 返回成功,失败,错误信息
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Map<String, String> saveInWarehouseRecordItem(String itemSku, Integer itemQuantity, String itemRemark, Long warehouseId, Long inWarehouseRecordId, Long userIdOfOperator);

	/**
	 * 保存上架
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Map<String, String> saveOnShelvesItem(String itemSku, Integer itemQuantity, String seatCode, Long inWarehouseRecordId, Long userIdOfOperator);

	/**
	 * 获取入库记录物品
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseRecordItemData(Long inWarehouseRecordId, Pagination page);

	/**
	 * 找入库订单
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public List<InWarehouseOrder> findInWarehouseOrder(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination page);

	/**
	 * 收货时 输入跟踪号 后查询入库订单
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public List<Map<String, String>> checkInWarehouseOrder(InWarehouseOrder inWarehouseOrder);

	/**
	 * 上架时 输入跟踪号 后查询入库记录
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public List<Map<String, String>> checkInWarehouseRecord(InWarehouseRecord inWarehouseRecord);

	/**
	 * 下架时 输入跟踪号 后查询入库记录
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public Map<String, String> checkOutWarehouseOrderByCustomerReferenceNo(String customerReferenceNo);

	/**
	 * 提交下架
	 * 
	 * @param customerReferenceNo
	 * @param outShelfItems
	 * @return
	 */
	public Map<String, String> submitOutShelfItems(String customerReferenceNo, String outShelfItems);

	/**
	 * 获取入库订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseOrderData(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination page);

	public Pagination getOnShelvesData(OnShelf onShelf, Map<String, String> moreParam, Pagination page);

	/**
	 * 获取入库记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseRecordData(InWarehouseRecord inWarehouseRecord, Map<String, String> moreParam, Pagination page);

	/**
	 * 获取入库明细记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseRecordItemListData(Map<String, String> moreParam, Pagination page);

	/**
	 * 获取出库订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getOutWarehouseOrderData(OutWarehouseOrder outWarehouseOrder, Map<String, String> moreParam, Pagination page);

	/**
	 * 找入库订单中包含的用户信息
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public List<User> findUserByInWarehouseOrder(List<InWarehouseOrder> inWarehouseOrderList);

	/**
	 * 获取入库订单物品
	 * 
	 * @param orderId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseOrderItemData(Long orderId, Pagination page);

	/**
	 * 获取入库订单物品
	 * 
	 * @param orderId
	 * @param page
	 * @return
	 */
	public List<InWarehouseOrderItem> getInWarehouseOrderItem(Long orderId);

	public List<Map<String, String>> getInWarehouseOrderItemMap(Long orderId);

	public List<Map<String, String>> getInWarehouseRecordItemMapByRecordId(Long recordId);

	/**
	 * 获取出库订单物品
	 * 
	 * @param orderId
	 * @return
	 */
	public List<OutWarehouseOrderItem> getOutWarehouseOrderItem(Long orderId);

	/**
	 * 
	 * 
	 * @param logisticsInterface
	 *            消息内容
	 * @param token
	 *            签名(签名参数名 顺丰未指定)
	 * @param dataDigest
	 *            消息签名
	 * @param msgType
	 *            消息类型,根据消息类型判断业务类型
	 * @param msgId
	 *            消息ID
	 * @param version
	 * @return
	 */
	public Map<String, Object> warehouseInterfaceEventType(String logisticsInterface, Long userIdOfCustomer, String dataDigest, String msgType, String msgId, String version);

	/**
	 * 接口验证
	 * 
	 * @param logisticsInterface
	 * @param token
	 * @param dataDigest
	 * @param msgType
	 * @param msgId
	 * @param version
	 * @return
	 */
	public Map<String, String> warehouseInterfaceValidate(String logisticsInterface, String token, String dataDigest, String msgType, String msgId, String version);

	/**
	 * 创建入库订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveInWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 创建出库订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceSaveOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

	/**
	 * 顺丰确认出库订单
	 * 
	 * @param eventBody
	 * @param userIdOfCustomer
	 * @return
	 */
	public String warehouseInterfaceConfirmOutWarehouseOrder(EventBody eventBody, Long userIdOfCustomer, String warehouseNo) throws ServiceException;

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

	/**
	 * 获取所有仓库
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Warehouse> findAllWarehouse() throws ServiceException;

	/**
	 * 获取所有仓库,并指定排在第一位的仓库id
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<Warehouse> findAllWarehouse(Long firstWarehouseId) throws ServiceException;

	/**
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> outWarehouseShippingConfirm(String TrackingNo, Long userIdOfOperator) throws ServiceException;

	/**
	 * 
	 * @param TrackingNo
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Object> outWarehouseSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator) throws ServiceException;

	public Map<String, Object> outWarehouseSubmitWeight(String customerReferenceNo, Double weight, Long userIdOfOperator) throws ServiceException;

}
