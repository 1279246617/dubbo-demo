package com.coe.wms.service.inventory.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.coe.wms.dao.product.IProductDao;
import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.ISeatDao;
import com.coe.wms.dao.warehouse.IShelfDao;
import com.coe.wms.dao.warehouse.ITrackingNoDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordStatusDao;
import com.coe.wms.dao.warehouse.storage.IItemInventoryDao;
import com.coe.wms.dao.warehouse.storage.IItemShelfInventoryDao;
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderAdditionalSfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseRecordItemDao;
import com.coe.wms.model.product.Product;
import com.coe.wms.model.user.User;
import com.coe.wms.model.warehouse.Seat;
import com.coe.wms.model.warehouse.Shelf;
import com.coe.wms.model.warehouse.Warehouse;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrder;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderItemShelf;
import com.coe.wms.model.warehouse.storage.order.OutWarehouseOrderStatus.OutWarehouseOrderStatusCode;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus;
import com.coe.wms.model.warehouse.storage.record.ItemShelfInventory;
import com.coe.wms.model.warehouse.storage.record.OnShelf;
import com.coe.wms.model.warehouse.storage.record.OutShelf;
import com.coe.wms.service.inventory.IShelfService;
import com.coe.wms.util.Constant;
import com.coe.wms.util.DateUtil;
import com.coe.wms.util.Pagination;
import com.coe.wms.util.StringUtil;

/**
 * 仓配服务
 * 
 * @author Administrator
 * 
 */
@Service("shelfService")
public class ShelfServiceImpl implements IShelfService {

	private static final Logger logger = Logger.getLogger(ShelfServiceImpl.class);
	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "trackingNoDao")
	private ITrackingNoDao trackingNoDao;

	@Resource(name = "seatDao")
	private ISeatDao seatDao;

	@Resource(name = "shelfDao")
	private IShelfDao shelfDao;

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	@Resource(name = "outShelfDao")
	private IOutShelfDao outShelfDao;
	@Resource(name = "inWarehouseOrderDao")
	private IInWarehouseOrderDao inWarehouseOrderDao;

	@Resource(name = "inWarehouseOrderStatusDao")
	private IInWarehouseOrderStatusDao inWarehouseOrderStatusDao;

	@Resource(name = "inWarehouseRecordStatusDao")
	private IInWarehouseRecordStatusDao inWarehouseRecordStatusDao;

	@Resource(name = "outWarehouseOrderItemShelfDao")
	private IOutWarehouseOrderItemShelfDao outWarehouseOrderItemShelfDao;

	@Resource(name = "inWarehouseOrderItemDao")
	private IInWarehouseOrderItemDao inWarehouseOrderItemDao;

	@Resource(name = "outWarehouseOrderDao")
	private IOutWarehouseOrderDao outWarehouseOrderDao;
	@Resource(name = "outWarehouseRecordDao")
	private IOutWarehouseRecordDao outWarehouseRecordDao;

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

	@Resource(name = "outWarehouseRecordItemDao")
	private IOutWarehouseRecordItemDao outWarehouseRecordItemDao;

	@Resource(name = "userDao")
	private IUserDao userDao;

	@Resource(name = "itemInventoryDao")
	private IItemInventoryDao itemInventoryDao;

	@Resource(name = "itemShelfInventoryDao")
	private IItemShelfInventoryDao itemShelfInventoryDao;

	@Resource(name = "outWarehouseOrderAdditionalSfDao")
	private IOutWarehouseOrderAdditionalSfDao outWarehouseOrderAdditionalSfDao;

	@Resource(name = "productDao")
	private IProductDao productDao;

	/**
	 * 获取下架订单数据
	 */
	@Override
	public Pagination getOutShelvesData(OutShelf outShelf, Map<String, String> moreParam, Pagination page) {
		List<OutShelf> outShelfList = outShelfDao.findOutShelf(outShelf, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (OutShelf outShelfTemp : outShelfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", outShelfTemp.getId());
			if (outShelfTemp.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(outShelfTemp.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(outShelfTemp.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());

			if (outShelfTemp.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(outShelfTemp.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("customerReferenceNo", outShelfTemp.getCustomerReferenceNo());
			map.put("batchNo", outShelfTemp.getBatchNo());
			map.put("seatCode", outShelfTemp.getSeatCode());
			map.put("sku", outShelfTemp.getSku());// 商品条码
			// 待从产品库获取 商品条码
			String barcode = outShelfTemp.getSku();
			String sku = productDao.findProductSkuByBarcode(user.getId(), barcode);
			map.put("skuNo", sku);
			map.put("quantity", outShelfTemp.getQuantity());
			map.put("outWarehouseOrderId", outShelfTemp.getOutWarehouseOrderId());
			// 查询用户名
			User userOfOperator = userDao.getUserById(outShelfTemp.getUserIdOfOperator());
			map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			list.add(map);
		}
		page.total = outShelfDao.countOutShelf(outShelf, moreParam);
		page.rows = list;
		return page;
	}

	/**
	 * 上架时 输入跟踪号 后查询入库记录
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	public List<Map<String, String>> checkInWarehouseRecord(InWarehouseRecord inWarehouseRecord) {
		List<InWarehouseRecord> inWarehouseRecordList = inWarehouseRecordDao.findInWarehouseRecord(inWarehouseRecord, null, null);
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		for (InWarehouseRecord record : inWarehouseRecordList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("recordId", record.getId() + "");
			// 客户
			Long userId = record.getUserIdOfCustomer();
			User user = userDao.getUserById(userId);
			map.put("userLoginName", user.getLoginName());
			// 操作员
			Long userIdOfOperator = record.getUserIdOfOperator();
			User userOfOperator = userDao.getUserById(userIdOfOperator);
			map.put("userLoginNameOfOperator", userOfOperator.getLoginName());

			map.put("trackingNo", record.getTrackingNo());
			map.put("batchNo", record.getBatchNo());
			String status = "";
			if (record.getStatus() != null) {
				InWarehouseRecordStatus inWarehouseRecordStatus = inWarehouseRecordStatusDao.findInWarehouseRecordStatusByCode(record.getStatus());
				if (inWarehouseRecordStatus != null) {
					status = inWarehouseRecordStatus.getCn();
				}
			}
			map.put("status", status);
			String time = DateUtil.dateConvertString(new Date(record.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss);
			map.put("createdTime", time);
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 保存上架
	 */
	@Override
	public Map<String, String> saveOnShelvesItem(String itemSku, Integer itemQuantity, String seatCode, Long inWarehouseRecordId, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(itemSku)) {
			map.put(Constant.MESSAGE, "请输入商品条码.");
			return map;
		}
		if (itemQuantity == null) {
			map.put(Constant.MESSAGE, "请输入商品数量.");
			return map;
		}
		InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(inWarehouseRecordId);
		// 检查该SKU是否存在入库订单收货中
		int countInWarehouseItemSkuQuantityByRecordId = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByRecordId(inWarehouseRecordId, itemSku);
		if (countInWarehouseItemSkuQuantityByRecordId <= 0) {
			map.put(Constant.MESSAGE, "该商品条码在此收货记录未找到.");
			return map;
		}
		// 先统计该入库订单收货记录中,是否包含此,SKU,数量
		int countOnShelfSkuQuantity = onShelfDao.countOnShelfSkuQuantity(inWarehouseRecordId, itemSku);
		if (countOnShelfSkuQuantity >= countInWarehouseItemSkuQuantityByRecordId) {
			map.put(Constant.MESSAGE, "该商品条码在此收货记录已经完全上架.");
			return map;
		}
		// 计算全部已上架数
		int allQuantity = onShelfDao.countOnShelfSkuQuantity(inWarehouseRecordId, itemSku) + itemQuantity;
		if (allQuantity > countInWarehouseItemSkuQuantityByRecordId) {
			map.put(Constant.MESSAGE, "上架数量不能大于收货数量.");
			return map;
		}
		// 保存新的上架记录
		OnShelf onShelf = new OnShelf();
		onShelf.setBatchNo(inWarehouseRecord.getBatchNo());
		onShelf.setCreatedTime(System.currentTimeMillis());
		onShelf.setInWarehouseRecordId(inWarehouseRecordId);
		onShelf.setQuantity(itemQuantity);
		onShelf.setSeatCode(seatCode);
		onShelf.setSku(itemSku);
		onShelf.setTrackingNo(inWarehouseRecord.getTrackingNo());
		onShelf.setUserIdOfCustomer(inWarehouseRecord.getUserIdOfCustomer());
		onShelf.setUserIdOfOperator(userIdOfOperator);
		onShelf.setWarehouseId(inWarehouseRecord.getWarehouseId());
		Long id = onShelfDao.saveOnShelf(onShelf);
		// 添加库位库存
		int upateCount = itemShelfInventoryDao.addItemShelfInventory(inWarehouseRecord.getWarehouseId(), inWarehouseRecord.getUserIdOfCustomer(), seatCode, itemSku, itemQuantity, inWarehouseRecord.getBatchNo());
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	/**
	 * 获取上架订单数据
	 */
	@Override
	public Pagination getOnShelvesData(OnShelf onShelf, Map<String, String> moreParam, Pagination page) {
		List<OnShelf> onShelfList = onShelfDao.findOnShelf(onShelf, moreParam, page);
		List<Object> list = new ArrayList<Object>();
		for (OnShelf onShelfTemp : onShelfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", onShelfTemp.getId());
			if (onShelfTemp.getCreatedTime() != null) {
				map.put("createdTime", DateUtil.dateConvertString(new Date(onShelfTemp.getCreatedTime()), DateUtil.yyyy_MM_ddHHmmss));
			}
			// 查询用户名
			User user = userDao.getUserById(onShelfTemp.getUserIdOfCustomer());
			map.put("userLoginNameOfCustomer", user.getLoginName());

			if (onShelfTemp.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(onShelfTemp.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse", warehouse.getWarehouseName());
				}
			}
			map.put("trackingNo", onShelfTemp.getTrackingNo());
			map.put("batchNo", onShelfTemp.getBatchNo());
			map.put("seatCode", onShelfTemp.getSeatCode());
			map.put("sku", onShelfTemp.getSku());
			// 待从产品库获取 原定义的sku=商品条码和skuNo=商品条码
			String barcode = onShelfTemp.getSku();// 2014-12-12
			String sku = productDao.findProductSkuByBarcode(user.getId(), barcode);
			map.put("skuNo", sku);
			map.put("quantity", onShelfTemp.getQuantity());
			map.put("inWarehouseRecordId", onShelfTemp.getInWarehouseRecordId());
			int receivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByRecordId(onShelfTemp.getInWarehouseRecordId(), onShelfTemp.getSku());
			map.put("receivedQuantity", receivedQuantity);
			// 查询用户名
			User userOfOperator = userDao.getUserById(onShelfTemp.getUserIdOfOperator());
			map.put("userLoginNameOfOperator", userOfOperator.getLoginName());
			list.add(map);
		}
		page.total = onShelfDao.countOnShelf(onShelf, moreParam);
		page.rows = list;
		return page;
	}

	/**
	 * 提交下架
	 * 
	 * @param customerReferenceNo
	 * @param outShelfItems
	 * @return
	 */
	@Override
	public Map<String, String> submitOutShelfItems(String customerReferenceNo, String outShelfItems, Long userIdOfOperator) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(customerReferenceNo)) {
			map.put(Constant.MESSAGE, "请输入客户订单号");
			return map;
		}
		if (StringUtil.isNull(outShelfItems) || StringUtil.isNull(customerReferenceNo)) {
			map.put(Constant.MESSAGE, "请输入下架明细");
			return map;
		}
		OutWarehouseOrder param = new OutWarehouseOrder();
		param.setCustomerReferenceNo(customerReferenceNo);
		List<OutWarehouseOrder> outWarehouseOrderList = outWarehouseOrderDao.findOutWarehouseOrder(param, null, null);
		if (outWarehouseOrderList == null || outWarehouseOrderList.size() < 0) {
			map.put(Constant.MESSAGE, "该客户订单号找不到出库订单");
			return map;
		}
		OutWarehouseOrder outWarehouseOrder = outWarehouseOrderList.get(0);
		if (!StringUtil.isEqual(OutWarehouseOrderStatusCode.WOS, outWarehouseOrder.getStatus())) {
			map.put(Constant.MESSAGE, "该客户订单号对应订单非待捡货下架状态");
			return map;
		}
		// 查找预分配的货位,对比下架是否准确
		OutWarehouseOrderItemShelf outWarehouseOrderItemShelfParam = new OutWarehouseOrderItemShelf();
		outWarehouseOrderItemShelfParam.setOutWarehouseOrderId(outWarehouseOrder.getId());
		List<OutWarehouseOrderItemShelf> outWarehouseOrderItemShelfList = outWarehouseOrderItemShelfDao.findOutWarehouseOrderItemShelf(outWarehouseOrderItemShelfParam, null, null);
		String[] outShelfItemArry = outShelfItems.split("\\|\\|");
		Pattern p = Pattern.compile("seatCode:(\\w+),sku:(\\w+),quantity:(\\w+)");
		for (String outShelfIten : outShelfItemArry) {
			Matcher m = p.matcher(outShelfIten);
			if (!m.find()) {
				continue;
			}
			String seatCode = m.group(1);
			String sku = m.group(2);
			String quantity = m.group(3);
			// 循环outWarehouseOrderItemShelfList
			int subQuantity = -1;
			for (OutWarehouseOrderItemShelf oItemShelf : outWarehouseOrderItemShelfList) {
				if (StringUtil.isEqualIgnoreCase(seatCode, oItemShelf.getSeatCode()) && StringUtil.isEqualIgnoreCase(sku, oItemShelf.getSku())) {
					// 库位号和sku相同,数量相减,最后outWarehouseOrderItemShelfList的内容数量都等于0
					subQuantity = oItemShelf.getQuantity() - Integer.valueOf(quantity);
					break;
				}
			}
			if (subQuantity != 0) {
				// 下架数量不准确
				map.put(Constant.MESSAGE, "下架货位,商品条码,数量和捡货单上的数量不对应,请重新下架");
				return map;
			}
		}
		// 下架准确,开始执行下架
		for (OutWarehouseOrderItemShelf oItemShelf : outWarehouseOrderItemShelfList) {
			OutShelf outShelf = new OutShelf();
			outShelf.setBatchNo(oItemShelf.getBatchNo());
			outShelf.setCreatedTime(System.currentTimeMillis());
			outShelf.setCustomerReferenceNo(customerReferenceNo);
			outShelf.setOutWarehouseOrderId(outWarehouseOrder.getId());
			outShelf.setQuantity(Integer.valueOf(oItemShelf.getQuantity()));
			outShelf.setSeatCode(oItemShelf.getSeatCode());
			outShelf.setSku(oItemShelf.getSku());
			outShelf.setUserIdOfCustomer(outWarehouseOrder.getUserIdOfCustomer());
			outShelf.setUserIdOfOperator(userIdOfOperator);
			outShelf.setWarehouseId(outWarehouseOrder.getWarehouseId());
			outShelfDao.saveOutShelf(outShelf);
			// 改变库位库存(ItemShelfInventory) ,不改变仓库sku库存(出货时改变ItemInventory)

			ItemShelfInventory itemShelfInventoryParam = new ItemShelfInventory();
			itemShelfInventoryParam.setBatchNo(oItemShelf.getBatchNo());
			itemShelfInventoryParam.setSeatCode(oItemShelf.getSeatCode());
			itemShelfInventoryParam.setSku(oItemShelf.getSku());
			itemShelfInventoryParam.setWarehouseId(outWarehouseOrder.getWarehouseId());
			itemShelfInventoryParam.setUserIdOfCustomer(outWarehouseOrder.getUserIdOfCustomer());
			List<ItemShelfInventory> itemShelfInventoryList = itemShelfInventoryDao.findItemShelfInventory(itemShelfInventoryParam, null, null);
			if (itemShelfInventoryList != null && itemShelfInventoryList.size() > 0) {
				ItemShelfInventory itemShelfInventory = itemShelfInventoryList.get(0);
				int outQuantity = outShelf.getQuantity();
				itemShelfInventoryDao.updateItemShelfInventoryQuantity(itemShelfInventory.getId(), itemShelfInventory.getQuantity() - outQuantity);
				int updateCount = outWarehouseOrderDao.updateOutWarehouseOrderStatus(outWarehouseOrder.getId(), OutWarehouseOrderStatusCode.WWW);
				if (updateCount > 0) {
					map.put(Constant.STATUS, Constant.SUCCESS);
				} else {
					map.put(Constant.MESSAGE, "执行数据库更新失败,请重试保存");// 待添加事务回滚
				}
			} else {
				map.put(Constant.MESSAGE, "找不到库位库存记录,出库订单Id:" + outWarehouseOrder.getId());// 待添加事务回滚
				// ServiceException("找不到库位库存记录,出库订单Id:"+outWarehouseOrder.getId());
			}
		}
		return map;
	}

	/**
	 * 下架时 输入跟踪号 后查询入库记录
	 * 
	 * @param inWarehouseOrder
	 * @return
	 */
	@Override
	public Map<String, String> checkOutWarehouseOrderByCustomerReferenceNo(String customerReferenceNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		OutWarehouseOrder outWarehouseOrder = new OutWarehouseOrder();
		outWarehouseOrder.setCustomerReferenceNo(customerReferenceNo);
		outWarehouseOrder.setStatus(OutWarehouseOrderStatusCode.WOS);
		long count = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrder, null);
		if (count <= 0) {
			outWarehouseOrder.setStatus(null);
			count = outWarehouseOrderDao.countOutWarehouseOrder(outWarehouseOrder, null);
			// 区分是找不到出库订单还是找不到等待下架状态的出库订单
			if (count > 0) {
				map.put(Constant.MESSAGE, "根据该客户订单号找不到待捡货下架的出库订单");
			} else {
				map.put(Constant.MESSAGE, "根据该客户订单号找不到出库订单");
			}
		} else {
			// 成功
			map.put(Constant.STATUS, Constant.SUCCESS);
		}
		return map;
	}

	@Override
	public Pagination getSeatData(Seat seat, Map<String, String> moreParam, Pagination page) {
		List<Seat> seatList = seatDao.findSeat(seat, page);
		List<Object> list = new ArrayList<Object>();
		for (Seat seatTemp : seatList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", seatTemp.getId());
			if (seatTemp.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(seatTemp.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouseId", warehouse.getWarehouseName());
				}
			}
			map.put("shelfCode", seatTemp.getShelfCode());
			map.put("seatCode", seatTemp.getSeatCode());

			ItemShelfInventory itemShelfInventoryParam = new ItemShelfInventory();
			itemShelfInventoryParam.setSeatCode(seatTemp.getSeatCode());
			Map<String, String> seatMoreParam = new HashMap<String, String>();
			seatMoreParam.put("minQuantity", "1");
			// 查找库存记录,库位,数量>=1
			String skus = "";
			List<ItemShelfInventory> itemShelfInventoryList = itemShelfInventoryDao.findItemShelfInventory(itemShelfInventoryParam, seatMoreParam, null);
			if (itemShelfInventoryList != null && itemShelfInventoryList.size() > 0) {
				map.put("status", Seat.SEAT_STATUS_USED);
				for (ItemShelfInventory itemShelfInventory : itemShelfInventoryList) {
					skus += (itemShelfInventory.getSku() + "*" + itemShelfInventory.getQuantity() + "  ;   ");
				}
			} else {
				// 空闲
				map.put("status", Seat.SEAT_STATUS_IDLE);
			}
			map.put("skus", skus);
			map.put("remark", seatTemp.getRemark());
			list.add(map);
		}
		page.total = seatDao.countSeat(seat);
		page.rows = list;
		return page;
	}

	@Override
	public Pagination getShelfData(Shelf shelf, Map<String, String> moreParam, Pagination page) {
		List<Shelf> shelfList = shelfDao.findShelf(shelf, page);
		List<Object> list = new ArrayList<Object>();
		for (Shelf shelfTemp : shelfList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", shelfTemp.getId());
			if (shelfTemp.getWarehouseId() != null) {
				Warehouse warehouse = warehouseDao.getWarehouseById(shelfTemp.getWarehouseId());
				if (warehouse != null) {
					map.put("warehouse_id", warehouse.getWarehouseName());
				}
			}
			map.put("shelf_code", shelfTemp.getShelfCode());
			if (StringUtil.isEqual(shelfTemp.getBusinessType(), Shelf.BUSINESS_TYPE_STORAGE)) {
				map.put("business_type", "仓配业务");
			} else if (StringUtil.isEqual(shelfTemp.getBusinessType(), Shelf.BUSINESS_TYPE_TRANSPORT_J)) {
				map.put("business_type", "集货转运");
			} else if (StringUtil.isEqual(shelfTemp.getBusinessType(), Shelf.BUSINESS_TYPE_TRANSPORT_Z)) {
				map.put("business_type", "直接转运");
			}
			if (StringUtil.isEqual(shelfTemp.getShelfType(), Shelf.TYPE_BUILD)) {
				map.put("shelf_type", "立体货架");
				map.put("rows", shelfTemp.getRows());// 行数/起始
				map.put("cols", shelfTemp.getCols());// 列数/终止
			} else if (StringUtil.isEqual(shelfTemp.getShelfType(), Shelf.TYPE_GROUND)) {
				map.put("shelf_type", "地面货架");
				map.put("rows", shelfTemp.getSeatStart());
				map.put("cols", shelfTemp.getSeatEnd());
			}
			map.put("remark", shelfTemp.getRemark());
			list.add(map);
		}
		page.total = shelfDao.countShelf(shelf);
		page.rows = list;
		return page;
	}

	@Override
	public Map<String, String> saveAddShelf(Long warehouseId, String shelfType, String shelfTypeName, Integer start, Integer end, Integer rows, Integer cols, Integer shelfNoStart, Integer shelfNoEnd, String remark, String businessType) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constant.STATUS, Constant.FAIL);
		if (StringUtil.isNull(shelfType)) {
			map.put(Constant.MESSAGE, "请输入货架类型");
			return map;
		}

		if (StringUtil.isNull(shelfTypeName)) {
			map.put(Constant.MESSAGE, "请输入货架编号类型");
			return map;
		}
		if (shelfNoStart == null || shelfNoEnd == null || shelfNoStart <= 0 || shelfNoEnd <= 0) {
			map.put(Constant.MESSAGE, "货架编号起始截止数必须是正整数");
			return map;
		}

		if (StringUtil.isEqual(shelfType, Shelf.TYPE_BUILD)) {
			if (rows == null || cols == null || rows <= 0 || cols <= 0) {
				map.put(Constant.MESSAGE, "层数列数必须是正整数");
				return map;
			}
			if (rows > 26) {
				map.put(Constant.MESSAGE, "货位层数不能大于26");
				return map;
			}
			if (cols > 99) {
				map.put(Constant.MESSAGE, "货位列数能超过2位数");
				return map;
			}
			if (shelfNoEnd > 999) {
				map.put(Constant.MESSAGE, "货架编号不能超过3位数");
				return map;
			}
		}

		if (StringUtil.isEqual(shelfType, Shelf.TYPE_GROUND)) {
			if (start == null || end == null || start <= 0 || end <= 0) {
				map.put(Constant.MESSAGE, "起始截止数必须是正整数");
				return map;
			}
			if (end > 999) {
				map.put(Constant.MESSAGE, "货位截止数不能超过3位数");
				return map;
			}
			if (shelfNoEnd > 9) {
				map.put(Constant.MESSAGE, "货架编号不能超过1位数");
				return map;
			}
		}

		// 循环检查货架
		for (int s = shelfNoStart; s <= shelfNoEnd; s++) {
			String str = "" + s;
			if (StringUtil.isEqual(shelfType, Shelf.TYPE_BUILD)) {
				if (str.length() == 1) {
					str = "00" + str;
				}
				if (str.length() == 2) {
					str = "0" + str;
				}
			}
			String shelfCode = shelfTypeName + str;
			Shelf shelf = new Shelf();
			shelf.setShelfCode(shelfCode);
			Long count = shelfDao.countShelf(shelf);
			if (count > 0) {
				map.put(Constant.MESSAGE, "货架编号" + shelfCode + "已经存在,此次创建全部失败");
				return map;
			}
		}
		int totalShelf = 0;
		int totalSeat = 0;
		// 循环创建货架
		for (int s = shelfNoStart; s <= shelfNoEnd; s++) {
			Shelf shelf = new Shelf();
			String str = "" + s;
			if (StringUtil.isEqual(shelfType, Shelf.TYPE_BUILD)) {
				if (str.length() == 1) {
					str = "00" + str;
				}
				if (str.length() == 2) {
					str = "0" + str;
				}
			}
			String shelfCode = shelfTypeName + str;
			shelf.setShelfCode(shelfCode);
			shelf.setCols(cols);
			shelf.setRemark(remark);
			shelf.setRows(rows);
			shelf.setSeatStart(start);
			shelf.setSeatEnd(end);
			shelf.setShelfType(shelfType);
			shelf.setWarehouseId(warehouseId);
			shelf.setBusinessType(businessType);
			// 创建货位
			List<Seat> seatList = Shelf.createSeatsByShelf(shelf);
			long shelfId = shelfDao.saveShelf(shelf);
			int seatQuantity = seatDao.saveBatchSeat(seatList);
			totalSeat += seatQuantity;
			totalShelf++;
		}
		map.put(Constant.MESSAGE, "创建成功" + totalShelf + "个货架, 总共生成" + totalSeat + "个货位");
		map.put(Constant.STATUS, Constant.SUCCESS);
		return map;
	}

	@Override
	public Shelf getShelfById(Long shelfId) {
		Shelf param = new Shelf();
		param.setId(shelfId);
		List<Shelf> shelfList = shelfDao.findShelf(param, null);
		if (shelfList != null && shelfList.size() > 0) {
			return shelfList.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, String>> getSeatItemInventory(Long seatId) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		Seat seatParam = new Seat();
		seatParam.setId(seatId);
		List<Seat> seatList = seatDao.findSeat(seatParam, null);
		if (seatList == null || seatList.size() <= 0) {
			return mapList;
		}
		Seat seat = seatList.get(0);
		ItemShelfInventory itemShelfInventoryParam = new ItemShelfInventory();
		itemShelfInventoryParam.setSeatCode(seat.getSeatCode());
		Map<String, String> seatMoreParam = new HashMap<String, String>();
		seatMoreParam.put("minQuantity", "1");
		List<ItemShelfInventory> itemShelfInventoryList = itemShelfInventoryDao.findItemShelfInventory(itemShelfInventoryParam, seatMoreParam, null);
		for (ItemShelfInventory item : itemShelfInventoryList) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("availableQuantity", item.getAvailableQuantity() + "");
			map.put("quantity", item.getQuantity() + "");
			map.put("sku", item.getSku());
			// 查询产品
			Product productParam = new Product();
			productParam.setUserIdOfCustomer(item.getUserIdOfCustomer());
			productParam.setBarcode(item.getSku());
			List<Product> productList = productDao.findProduct(productParam, null, null);
			if (productList != null && productList.size() > 0) {
				Product product = productList.get(0);
				map.put("skuNo", product.getSku());
				map.put("skuName", product.getProductName());
			}
			mapList.add(map);
		}
		return mapList;
	}
}
