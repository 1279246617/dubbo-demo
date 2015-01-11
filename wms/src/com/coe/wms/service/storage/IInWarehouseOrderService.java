package com.coe.wms.service.storage;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.util.Pagination;

/**
 * 
 * @author Administrator
 * 
 */
public interface IInWarehouseOrderService {

	static final Logger logger = Logger.getLogger(IInWarehouseOrderService.class);

	/**
	 * 保存入库订单 返回成功,失败,错误信息
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Map<String, String> saveInWarehouseRecord(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long inWarehouseOrderId);

	public Map<String, String> submitInWarehouseRecord(Long inWarehouseRecordId);

	/**
	 * 保存入库明细 返回成功,失败,错误信息
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Map<String, String> saveInWarehouseRecordItem(String itemSku, Integer itemQuantity, String itemRemark, Long warehouseId, Long inWarehouseRecordId, Long userIdOfOperator, String isConfirm);

	/**
	 * 检查收货数量
	 * 
	 * @param itemSku
	 * @param itemQuantity
	 * @param itemRemark
	 * @param warehouseId
	 * @param inWarehouseRecordId
	 * @param userIdOfOperator
	 * @return
	 */
	public Map<String, String> checkInWarehouseRecordItem(String itemSku, Integer itemQuantity, Long warehouseId, Long inWarehouseRecordId, Long userIdOfOperator);

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
	 * 获取入库订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseOrderData(InWarehouseOrder inWarehouseOrder, Map<String, String> moreParam, Pagination page);

	/**
	 * 获取入库记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseRecordData(InWarehouseRecord inWarehouseRecord, Map<String, String> moreParam, Pagination page);

	/**
	 * 获取入库记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseRecordOnShelfData(Long userIdOfCustomer, Long warehouseId, String trackingNo, String batchNo, String receivedTimeStart, String receivedTimeEnd, Pagination page);

	/**
	 * 获取入库明细记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getInWarehouseRecordItemListData(Map<String, String> moreParam, Pagination page);

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
	 * 所有入库订单状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<InWarehouseOrderStatus> findAllInWarehouseOrderStatus() throws ServiceException;

	public Map<String, String> saveInWarehouseOrderRemark(String remark, Long id) throws ServiceException;

	public Map<String, String> saveInWarehouseRecordRemark(String remark, Long id) throws ServiceException;

	public Map<String, String> saveInWarehouseOrderItemSku(Long id, String sku) throws ServiceException;

}
