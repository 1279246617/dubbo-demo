package com.coe.wms.common.core.db;

import com.coe.wms.common.core.cache.redis.RedisClient;

public class IdWorkerAide {

	private static IdWorker idWorker;

	public static long nextId() {
		if (idWorker == null) {
			initIdWorker();
		}
		return idWorker.nextId();
	}

	/**
	 * 实例化idWorker
	 */
	private static void initIdWorker() {
		String workerIdKey = "sequence-workerId";
		// 机器id
		Integer workerId = (Integer) RedisClient.getInstance().getObject(workerIdKey);
		if (workerId == null) {
			workerId = 0;
		}
		String datacenterIdKey = "sequence-datacenterId" + workerId;

		// 数据中心id
		Integer datacenterId = (Integer) RedisClient.getInstance().getObject(datacenterIdKey);
		if (datacenterId == null) {
			datacenterId = 0;
		} else {
			datacenterId++;
		}

		RedisClient.getInstance().setObject(workerIdKey, workerId);
		RedisClient.getInstance().setObject(datacenterIdKey, datacenterId);

		if (datacenterId > 31) {
			datacenterId = 0;
			workerId++;

	 		// 每32 个数据中心id 换一个机器id
			RedisClient.getInstance().setObject(workerIdKey, workerId);
			datacenterIdKey = "sequence-datacenterId" + workerId;
			RedisClient.getInstance().setObject(datacenterIdKey, datacenterId);
		}

		idWorker = new IdWorker(workerId, datacenterId);
	}

}
