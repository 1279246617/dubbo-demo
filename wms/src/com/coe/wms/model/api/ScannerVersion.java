package com.coe.wms.model.api;

import java.io.Serializable;

import com.coe.wms.util.GsonUtil;

public class ScannerVersion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4582026339024276501L;

	private Long id;

	private String version;

	private String isMustUpdate;

	private String url;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIsMustUpdate() {
		return isMustUpdate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setIsMustUpdate(String isMustUpdate) {
		this.isMustUpdate = isMustUpdate;
	}
}
