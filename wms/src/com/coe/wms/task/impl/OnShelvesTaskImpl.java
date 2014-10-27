package com.coe.wms.task.impl;

import java.util.Date;
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
import com.coe.wms.dao.warehouse.storage.IOnShelfDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderItemDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderReceiverDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderSenderDao;
import com.coe.wms.dao.warehouse.storage.IOutWarehouseOrderStatusDao;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecord;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordItem;
import com.coe.wms.model.warehouse.storage.record.InWarehouseRecordStatus.InWarehouseRecordStatusCode;
import com.coe.wms.task.IOnShelvesTask;
import com.coe.wms.util.DateUtil;

@Component
public class OnShelvesTaskImpl implements IOnShelvesTask {

	private static final Logger logger = Logger.getLogger(OnShelvesTaskImpl.class);

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

	@Resource(name = "onShelfDao")
	private IOnShelfDao onShelfDao;

	/**
	 * 定时更新入库上架状态 部分上架,全未上架,上架完成
	 */
	@Scheduled(cron = "0 0/45 * * * ? ")
	@Override
	public void updateInWarehouseRecordOnShelvesStatus() {
		List<Long> recordIdList = inWarehouseRecordDao.findUnCompleteInWarehouseRecordId();
		logger.info("找到待更新状态的入库记录总数:" + recordIdList.size());
		for (int i = 0; i < recordIdList.size(); i++) {
			Long inWarehouseRecordId = recordIdList.get(i);
			// 查出入库的物品明细
			InWarehouseRecordItem itemParam = new InWarehouseRecordItem();
			itemParam.setInWarehouseRecordId(inWarehouseRecordId);
			List<InWarehouseRecordItem> itemList = inWarehouseRecordItemDao.findInWarehouseRecordItem(itemParam, null, null);
			// 收货主单是否无item
			if (itemList == null || itemList.size() < 1) {
				InWarehouseRecord inWarehouseRecord = inWarehouseRecordDao.getInWarehouseRecordById(inWarehouseRecordId);
				// 时间创建超过1个小时
				long diff = DateUtil.getDiffHours(new Date(), new Date(inWarehouseRecord.getCreatedTime()));
				if (diff > 1) {
					inWarehouseRecordDao.deleteInWarehouseRecordById(inWarehouseRecordId);
				}
				continue;
			}

			String status = InWarehouseRecordStatusCode.NONE;
			// 查看入库订单每个sku ,上架数量
			boolean isComplete = true;
			for (InWarehouseRecordItem item : itemList) {
				String sku = item.getSku();
				// 上架数量
				int skuQuantity = item.getQuantity();
				// 查上架记录
				int onShelfQuantity = onShelfDao.countOnShelfSkuQuantity(inWarehouseRecordId, sku);
				if (onShelfQuantity >= skuQuantity && isComplete) {
					status = InWarehouseRecordStatusCode.COMPLETE;
				} else {
					isComplete = false;
					// 只要任何一个sku 不是全部上架, 后续的sku就只需区分,部分上架和全未上架
					if (onShelfQuantity > 0) {
						status = InWarehouseRecordStatusCode.PART;
						// 如果已经有上架,也无需再区分部分上架和全未上架
						break;
					}
				}
			}
			// 执行更新
			InWarehouseRecord inWarehouseRecordUpdate = new InWarehouseRecord();
			inWarehouseRecordUpdate.setId(inWarehouseRecordId);
			inWarehouseRecordUpdate.setStatus(status);
			int updateCount = inWarehouseRecordDao.updateInWarehouseRecordStatus(inWarehouseRecordUpdate);
			if (updateCount > 0) {
				logger.info("更新入库订单状态成功:recordId:" + inWarehouseRecordId + ",status:" + status);
			} else {
				logger.info("更新入库订单状态失败:recordId:" + inWarehouseRecordId + ",status:" + status);
			}
		}
	}
}
