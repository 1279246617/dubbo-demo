package com.coe.wms.service.storage;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Seat;
import com.coe.wms.model.warehouse.Shelf;
import com.coe.wms.model.warehouse.Shipway;
import com.coe.wms.model.warehouse.TrackingNo;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.report.Report;
import com.coe.wms.model.warehouse.report.ReportType;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.model.warehouse.storage.record.OutWarehousePackage;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.OutWarehouseRecordItem;
import com.coe.wms.pojo.api.warehouse.EventBody;
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

	public Warehouse getWarehouseById(Long fwarehouseId) throws ServiceException;

	public Map<String, String> saveInWarehouseOrderRemark(String remark, Long id) throws ServiceException;

	public Map<String, String> saveInWarehouseRecordRemark(String remark, Long id) throws ServiceException;

	public Map<String, String> saveInWarehouseOrderItemSku(Long id, String sku) throws ServiceException;

	public TrackingNo getTrackingNoById(Long id) throws ServiceException;

	public List<Shipway> findAllShipway() throws ServiceException;
}
