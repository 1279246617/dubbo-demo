package com.coe.wms.task;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.coe.wms.service.storage.IStorageService;

public class InWarehouseTask extends QuartzJobBean {
	
	@Resource(name = "storageService")
	private IStorageService storageService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		//发送仓配入库信息给客户
		storageService.sendInWarehouseInfoToCustomer();
	}
	
	public void setStorageService(IStorageService storageService) {
		this.storageService = storageService;
	}
}
