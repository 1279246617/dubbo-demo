package org.mybatis.plugin.rw;

import javax.sql.DataSource;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 动态路由数据源代理类
 * 
 * @ClassName: DynamicRoutingDataSourceProxy
 * @author yechao
 * @date 2017年3月2日 上午11:40:49
 * @Description: TODO
 */
public class DynamicRoutingDataSourceProxy extends AbstractDynamicDataSourceProxy {

	private AtomicLong counter = new AtomicLong(0);

	private static final Long MAX_POOL = Long.MAX_VALUE;

	private final Lock lock = new ReentrantLock();

	@Override
	protected DataSource loadReadDataSource() {
		int index = 1;

		if (getReadDataSourcePollPattern() == 1) {
			// 轮询方式
			long currValue = counter.incrementAndGet();
			if ((currValue + 1) >= MAX_POOL) {
				try {
					lock.lock();
					if ((currValue + 1) >= MAX_POOL) {
						counter.set(0);
					}
				} finally {
					lock.unlock();
				}
			}
			index = (int) (currValue % getReadDsSize());
		} else {
			// 随机方式
			index = ThreadLocalRandom.current().nextInt(0, getReadDsSize());
		}
		return getResolvedReadDataSources().get(index);
	}
}
