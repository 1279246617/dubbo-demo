package com.coe.wms.common.core.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 
 * @ClassName: DynamicDataSource
 * @author yechao
 * @date 2017年4月21日 上午10:58:26
 * @Description: TODO
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	private Object dataSourceMaster;

	private List<Object> dataSourceSlaves;

	private Random random = new Random();

	private List<String> dataSourceSlaveKeys = new ArrayList<String>();

	/*
	 * (non-Javadoc) spring 从这里获取数据源 key
	 * 
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		if (DataSourceHolder.getType() == null || DataSourceHolder.getType().equals(DataSourceType.Master.name())) {
			return DataSourceType.Master.name();
		}
		// 从库随机选择其中一个
		return dataSourceSlaveKeys.get(random.nextInt(dataSourceSlaveKeys.size()));
	}

	@Override
	public void afterPropertiesSet() {
		if (this.dataSourceMaster == null) {
			throw new IllegalArgumentException("Property 'dataSourceMaster' is required");
		}
		// 设置默认数据源
		setDefaultTargetDataSource(dataSourceMaster);

		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.Master, dataSourceMaster);
		if (dataSourceSlaves != null) {
			for (int i = 0; i < dataSourceSlaves.size(); i++) {
				String slaveKey = DataSourceType.Slave.name() + i;
				dataSourceSlaveKeys.add(slaveKey);

				targetDataSources.put(slaveKey, dataSourceSlaves.get(i));
			}
		}
		// 设置全部数据源
		setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	public Object getDataSourceMaster() {
		return dataSourceMaster;
	}

	public void setDataSourceMaster(Object dataSourceMaster) {
		this.dataSourceMaster = dataSourceMaster;
	}

	public List<Object> getDataSourceSlaves() {
		return dataSourceSlaves;
	}

	public void setDataSourceSlaves(List<Object> dataSourceSlaves) {
		this.dataSourceSlaves = dataSourceSlaves;
	}

}
