package com.coe.wms.service.transport;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coe.wms.exception.ServiceException;
import com.coe.wms.model.warehouse.Shipway;
import com.coe.wms.model.warehouse.transport.BigPackage;
import com.coe.wms.model.warehouse.transport.BigPackageStatus;
import com.coe.wms.model.warehouse.transport.LittlePackage;
import com.coe.wms.model.warehouse.transport.LittlePackageItem;
import com.coe.wms.model.warehouse.transport.LittlePackageOnShelf;
import com.coe.wms.model.warehouse.transport.LittlePackageStatus;
import com.coe.wms.model.warehouse.transport.PackageRecord;
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
	public List<BigPackageStatus> findAllBigPackageStatus() throws ServiceException;

	/**
	 * 获取所有转运订单状态
	 * 
	 * @return
	 * @throws ServiceException
	 */
	public List<LittlePackageStatus> findAllLittlePackageStatus() throws ServiceException;

	public List<Shipway> findAllShipway() throws ServiceException;

	/**
	 * 获取转运订单
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getBigPackageData(BigPackage bigPackage, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public Pagination getLittlePackageData(LittlePackage littlePackage, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public Pagination getLittlePackageOnShelfData(LittlePackageOnShelf param, Map<String, String> moreParam, Pagination page) throws ServiceException;

	public List<Map<String, Object>> getLittlePackageItems(Long bigPackageId) throws ServiceException;

	public List<LittlePackageItem> getLittlePackageItemsByLittlePackageId(Long littlePackageId) throws ServiceException;

	/**
	 * 审核转运订单
	 * 
	 * @param orderIds
	 * @param checkResult
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, String> checkBigPackage(String bigPackageIds, String checkResult, Long userIdOfOperator) throws ServiceException;

	/**
	 * 收货时 输入跟踪号 后查询转运订单
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public List<Map<String, String>> checkReceivedLittlePackage(LittlePackage littlePackage);

	public Map<String, String> submitInWarehouse(String trackingNo, String remark, Long userIdOfOperator, Long warehouseId, Long inWarehouseOrderId);

	public Map<String, String> bigPackageSubmitWeight(Long userIdOfOperator, Long bigPackageId, Double weight);

	public Map<String, String> saveLittlePackageOnShelves(Long userIdOfOperator, Long littlePackageId, String seatCode);

	public Map<String, String> bigPackageWeightSubmitCustomerReferenceNo(String customerReferenceNo, Long userIdOfOperator);

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
	public Map<String, String> checkLittlePackage(Long bigPackageId, String trackingNo) throws ServiceException;

	/**
	 * 获取建包记录
	 * 
	 * @param inWarehouseRecordId
	 * @param page
	 * @return
	 */
	public Pagination getPackageRecordData(PackageRecord outWarehousePackage, Map<String, String> moreParam, Pagination page);

	public List<Map<String, String>> getPackageRecordItemByPackageRecordId(Long packageId);

	public Map<String, String> savePackageRecordRemark(String remark, Long id) throws ServiceException;

	public Map<String, String> applyTrackingNo(Long orderId) throws ServiceException;
}
