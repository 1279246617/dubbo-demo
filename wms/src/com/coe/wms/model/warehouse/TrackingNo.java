package com.coe.wms.model.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.coe.wms.util.StringUtil;

public class TrackingNo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 309830851812517207L;

	/**
	 * 可用的
	 */
	public static int STATUS_AVAILABLE = 0;

	/**
	 * 锁定的
	 */
	public static int STATUS_LOCKED = 1;

	/**
	 * 已经使用的
	 * 
	 */
	public static int STATUS_USED = 2;

	/**
	 * COE单号
	 */
	public static String TYPE_COE = "COE";

	/**
	 * sf单号
	 */
	public static String TYPE_SF = "SF";

	/**
	 * etk单号
	 */
	public static String TYPE_ETK = "ETK";
	private Long id;
	/**
	 * 单号类型; 如 COE,SF,ETK
	 * 
	 */
	private String type;

	/**
	 * 跟踪号
	 */
	private String trackingNo;

	/**
	 * 创建时间
	 */
	private Long createdTime;

	/**
	 * 是否已使用 0.未使用的 1.被锁定的 2.已经使用了
	 */
	private String status;

	/**
	 * 锁定时间
	 */
	private Long lockedTime;

	/**
	 * 使用时间
	 */
	private Long usedTime;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTrackingNo() {
		return trackingNo;
	}

	public void setTrackingNo(String trackingNo) {
		this.trackingNo = trackingNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getLockedTime() {
		return lockedTime;
	}

	public void setLockedTime(Long lockedTime) {
		this.lockedTime = lockedTime;
	}

	public Long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Long usedTime) {
		this.usedTime = usedTime;
	}

	public static List<String> generateTrackingNos(String type, int start, int end) {
		List<String> trackingNoList = new ArrayList<String>();
		if (StringUtil.isEqual(type, TYPE_COE)) {

		}
		for (int i = start; i < end; i++) {
			String trackingNo = "";
			trackingNoList.add(trackingNo);
		}
		return trackingNoList;
	}
}
