package com.coe.wms.model.warehouse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.coe.wms.util.StringUtil;

public class TrackingNos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 309830851812517207L;

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
	 * 是否已使用
	 */
	private String isUsed;

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

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
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
