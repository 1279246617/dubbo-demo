package com.coe.wms.dao.warehouse;

import java.util.List;

import com.coe.wms.model.warehouse.TrackingNo;

public interface ITrackingNoDao {

	/**
	 * 获取一个可用的跟踪号
	 * 
	 * @return
	 */
	public TrackingNo getAvailableTrackingNoByType(String type);
	
	public List<TrackingNo> findTrackingNo(String trackingNo, String type);

	/**
	 * 更新跟踪号
	 * 
	 * @param trackingNo
	 * @return
	 */
	public int usedTrackingNo(Long trackingNoId);

	/**
	 * 锁定跟踪号
	 * 
	 * @param trackingNo
	 * @return
	 */
	public int lockTrackingNo(Long trackingNoId);
}
