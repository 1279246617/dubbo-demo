package com.coe.wms.common.core.db;

/**
 * 雪花算法自造全局自增ID
 * 
 * @ClassName: IdWorker
 * @author twitter
 * @date 2017年2月22日 下午5:48:32
 * @Description: TODO自造全局自增ID，适合大数据环境的分布式场景 每秒能够产生26万ID左右
 * 
 * 
 * @ClassName: IdWorker
 * @author yechao
 * @date 2017年5月6日 下午5:44:19
 * @Description: TODO
 */
public class IdWorker {
	private long workerId;
	private long datacenterId;
	private long sequence = 0L;
	// 基准时间2010
	private static long twepoch = 1288834974657L;
	// 机器标识位数
	private static long workerIdBits = 5;
	// 数据中心标识位数
	private static long datacenterIdBits = 5L;
	// 机器标识最大值
	private static long maxWorkerId = -1L ^ (-1L << (int) workerIdBits);
	// 数据中心标识最大值
	private static long maxDatacenterId = -1L ^ (-1L << (int) datacenterIdBits);
	// 毫秒内序列号识位数
	private static long sequenceBits = 12L;

	private long workerIdShift = sequenceBits;
	private long datacenterIdShift = sequenceBits + workerIdBits;
	private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private long sequenceMask = -1L ^ (-1L << (int) sequenceBits);

	private long lastTimestamp = -1L;

	public IdWorker(long workerId, long datacenterId) {
		// sanity check for workerId
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	public synchronized long nextId() {
		long timestamp = timeGen();

		if (timestamp < lastTimestamp) {
			throw new RuntimeException(
					String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		return ((timestamp - twepoch) << (int) timestampLeftShift) | (datacenterId << (int) datacenterIdShift) | (workerId << (int) workerIdShift)
				| sequence;
	}

	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {

		return System.nanoTime()/422;
	}
}