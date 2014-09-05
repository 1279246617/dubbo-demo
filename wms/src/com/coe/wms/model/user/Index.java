package com.coe.wms.model.user;

import java.io.Serializable;

import com.google.code.ssm.api.CacheKeyMethod;

/**
 * 不同用户类型的首页
 * 
 * @author Administrator
 * 
 */
public class Index implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -726391150472173907L;

	/**
	 * id 主键
	 */
	private Long id;

	/**
	 * 首页名称
	 */
	private String indexName;

	/**
	 * 首页url
	 */
	private String indexUrl;

	/**
	 * 用户类型
	 */
	private String userType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	@CacheKeyMethod
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
