package com.coe.wms.task.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.coe.wms.dao.user.IUserDao;
import com.coe.wms.dao.warehouse.IWarehouseDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseOrderStatusDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordDao;
import com.coe.wms.dao.warehouse.storage.IInWarehouseRecordItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderItem;
import com.coe.wms.model.warehouse.storage.order.InWarehouseOrderStatus.InWarehouseOrderStatusCode;
import com.coe.wms.task.IInWarehouseTask;

@Component
public class InWarehouseTaskImpl implements IInWarehouseTask {

	private static final Logger logger = Logger.getLogger(InWarehouseTaskImpl.class);

	@Resource(name = "warehouseDao")
	private IWarehouseDao warehouseDao;

	@Resource(name = "inWarehouseOrderDao")
	private IInWarehouseOrderDao inWarehouseOrderDao;

	@Resource(name = "inWarehouseOrderStatusDao")
	private IInWarehouseOrderStatusDao inWarehouseOrderStatusDao;

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

	/**
	 * 定时更新入库订单状态 部分入库,全未入库,入库完成
	 */
//	@Scheduled(cron = "0 0/15 8-23 * * ? ")
	@Override
	public void updateInWarehouseOrderStatus() {
		List<Long> orderIdList = inWarehouseOrderDao.findUnCompleteInWarehouseOrderId();
		logger.info("找到待更新状态的入库订单,订单总数:" + orderIdList.size());
		for (int i = 0; i < orderIdList.size(); i++) {
			Long inWarehouseOrderId = orderIdList.get(i);
			// 查出入库订单的物品明细
			InWarehouseOrderItem itemParam = new InWarehouseOrderItem();
			itemParam.setOrderId(inWarehouseOrderId);
			List<InWarehouseOrderItem> itemList = inWarehouseOrderItemDao.findInWarehouseOrderItem(itemParam, null, null);
			String status = InWarehouseOrderStatusCode.NONE;
			// 查看入库订单每个sku ,收货数量
			boolean isComplete = true;
			for (InWarehouseOrderItem item : itemList) {
				String sku = item.getSku();
				int skuQuantity = item.getQuantity();
				int receivedQuantity = inWarehouseRecordItemDao.countInWarehouseItemSkuQuantityByOrderId(inWarehouseOrderId, sku);
				if (receivedQuantity >= skuQuantity && isComplete) {
					status = InWarehouseOrderStatusCode.COMPLETE;
				} else {
					isComplete = false;
					// 只要任何一个sku 不是全部收货, 后续的sku就只需区分,部分收货和全未收货
					if (receivedQuantity > 0) {
						status = InWarehouseOrderStatusCode.PART;
						// 如果已经有收货,也无需再区分部分收货和全未收货
						break;
					}
				}
			}
			int updateCount = inWarehouseOrderDao.updateInWarehouseOrderStatus(inWarehouseOrderId, status);
			if (updateCount > 0) {
				logger.info("更新入库订单状态成功:orderId:" + inWarehouseOrderId + ",status:" + status);
			} else {
				logger.info("更新入库订单状态失败:orderId:" + inWarehouseOrderId + ",status:" + status);
			}
		}
	}
}
