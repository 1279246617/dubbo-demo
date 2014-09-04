package com.coe.wms.model.warehouse.transport;

import java.io.Serializable;

/**
 * 订单状态表
 * 
 * @author yechao
 * @date 2013年11月2日
 */
public class OrderStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1769245586603559281L;
	private Long id;

	private Integer status;

	private Integer sort;

	private String statusNameCN;

	private String statusNameEN;

	private String remark;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStatusNameCN() {
		return statusNameCN;
	}

	public void setStatusNameCN(String statusNameCN) {
		this.statusNameCN = statusNameCN;
	}

	public String getStatusNameEN() {
		return statusNameEN;
	}

	public void setStatusNameEN(String statusNameEN) {
		this.statusNameEN = statusNameEN;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
