package com.coe.wms.service.inventory;

import java.util.List;
import java.util.Map;

import com.coe.wms.model.warehouse.Seat;
import com.coe.wms.model.warehouse.Shelf;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.util.Pagination;

/**
 * 
 * @author Administrator
 * 
 */
public interface IShelfService {
	public Pagination getOutShelvesData(OutShelf outShelf, Map<String, String> moreParam, Pagination page);

	/**
	 * 上架时 输入跟踪号 后查询入库记录
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public List<Map<String, String>> checkInWarehouseRecord(InWarehouseRecord inWarehouseRecord);

	/**
	 * 保存上架
	 * 
	 * @param inWarehouseOrder
	 * @param moreParam
	 * @param page
	 * @return
	 */
	public Map<String, String> saveOnShelvesItem(String itemSku, Integer itemQuantity, String seatCode, Long inWarehouseRecordId, Long userIdOfOperator);

	public Pagination getOnShelvesData(OnShelf onShelf, Map<String, String> moreParam, Pagination page);

	public Map<String, String> submitOutShelfItems(String customerReferenceNo, String outShelfItems, Long userIdOfOperator);

	/**
	 * 下架时 输入跟踪号 后查询入库记录
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public Map<String, String> checkOutWarehouseOrderByCustomerReferenceNo(String customerReferenceNo);

	public Pagination getSeatData(Seat seat, Map<String, String> moreParam, Pagination page);

	public Pagination getShelfData(Shelf shelf, Map<String, String> moreParam, Pagination page);

	public Shelf getShelfById(Long shelfId);

	public Map<String, String> saveAddShelf(Long warehouseId, String shelfType, String shelfTypeName, Integer start, Integer end, Integer rows, Integer cols, Integer shelfNoStart, Integer shelfNoEnd, String remark);
}
