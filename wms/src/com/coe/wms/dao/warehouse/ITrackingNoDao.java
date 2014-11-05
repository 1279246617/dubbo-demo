package com.coe.wms.dao.warehouse;

import java.util.List;

import com.coe.wms.model.warehouse.TrackingNo;

public interface ITrackingNoDao {

	public List<TrackingNo> findTrackingNos();

	/**
	 * 获取一个可用的跟踪号
	 * 
	 * @return
	 */
	public TrackingNo getAvailableTrackingNoByType(String type);

	/**
	 * 更新跟踪号
	 * 
	 * @param trackingNo
	 * @return
	 */
	public int updateTrackingNo(TrackingNo trackingNo);
}
