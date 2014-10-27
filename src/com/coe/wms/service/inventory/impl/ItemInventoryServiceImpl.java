package com.coe.wms.service.inventory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordStatusDao;
import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.model.unit.Weight;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus;
import com.coe.wms.model.warehouse.storage.record.ItemInventory;
import com.coe.wms.service.inventory.IItemInventoryService;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("inventoryService")
public class ItemInventoryServiceImpl implements IItemInventoryService {

	private static final Logger logger = Logger.getLogger(ItemInventoryServiceImpl.class);

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	@Resource(name = "inWarehouseOrderDao")
	private IInWarehouseOrderDao inWarehouseOrderDao;

	@Resource(name = "inWarehouseOrderStatusDao")
	private IInWarehouseOrderStatusDao inWarehouseOrderStatusDao;

	@Resource(name = "inWarehouseRecordStatusDao")
	private IInWarehouseRecordStatusDao inWarehouseRecordStatusDao;

	@Resource(name = "inWarehouseOrderItemDao")
	private IInWarehouseOrderItemDao inWarehouseOrderItemDao;

	@Resource(name = "outWarehouseOrderDao")
	private IOutWarehouseOrderDao outWarehouseOrderDao;

	@Resource(name = "outWarehouseOrderStatusDao")
	private IOutWarehouseOrderStatusDao outWarehouseOrderStatusDao;

	@Resource(name = "outWarehouseOrderItemDao")
	private IOutWarehouseOrderItemDao outWarehouseOrderItemDao;

	@Resource(name = "outWarehouseOrderSenderDao")
	private IOutWarehouseOrderSenderDao outWarehouseOrderSenderDao;

	@Resource(name = "outWarehouseOrderReceiverDao")
	private IOutWarehouseOrderReceiverDao outWarehouseOrderReceiverDao;

	@Resource(name = "inWarehouseRecordDao")
	private IInWarehouseRecordDao inWarehouseRecordDao;

	@Resource(name = "inWarehouseRecordItemDao")
	private IInWarehouseRecordItemDao inWarehouseRecordItemDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "itemInventoryDao")
	private IItemInventoryDao itemInventoryDao;

	@Resource(name = "outWarehouseOrderAdditionalSfDao")
	private IOutWarehouseOrderAdditionalSfDao outWarehouseOrderAdditionalSfDao;

	@Override
	public Pagination getListInventoryData(ItemInventory param, Map<String, String> moreParam, Pagination pagination) {
		List<ItemInventory> itemInventoryList = itemInventoryDao.findItemInventory(param, moreParam, pagination);
		List<Object> list = new ArrayList<Object>();
		for (ItemInventory itemInventory : itemInventoryList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", itemInventory.getId());
			if (itemInventory.getLastUpdateTime() != null) {
				map.put("lastUpdateTime",DateUtil.dateConvertString(new Date(itemInventory.getLastUpdateTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(itemInventory.getUserIdOfCustomer());
			map.put("userNameOfCustomer", user.getLoginName());
			map.put("batchNo", itemInventory.getBatchNo());
			map.put("quantity", itemInventory.getQuantity());
			map.put("availableQuantity", itemInventory.getAvailableQuantity());
			map.put("sku", itemInventory.getSku());
			if (itemInventory.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(itemInventory.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			list.add(map);
		}
		pagination.total = itemInventoryDao.countItemInventory(param, moreParam);
		pagination.rows = list;
		return pagination;
	}

}
