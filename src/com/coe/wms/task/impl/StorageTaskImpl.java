package com.coe.wms.task.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.coe.wms.task.IStorageTask;


@Component
public class StorageTaskImpl implements IStorageTask {
	
	@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次 
	@Override
	public void sendInWarehouseInfoToCustomer() {
			System.out.println("哈哈哈哈哈哈哈哈哈哈");
	}
}
