package com.coe.message.entity;

/**信息实体类*/
public class Message {

	private Long id;
	/**请求id,长度36的UUID,不可重复*/
	private String requestId;
	/**用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类*/
	private String keyword1;
	/**用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类*/
	private String keyword2;
	/**用于搜索报文的关键字 一般如:订单号,跟踪单号,订单id,客户id之类*/
	private String keyword3;
	/**报文创建时间*/
	private Long createdTime;
	/**发送次数*/
	private Integer count;
	/**0:未推送,1:已推送并得到http status 200响应 2:已推送,但响应失败*/
	private Integer status;
	/**是否有效,0:是(存入队列发送)，1：否(不存入队列发送)*/
	private Integer isValid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getKeyword3() {
		return keyword3;
	}

	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}

	public String getKeyword1() {
		return keyword1;
	}

	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}

	public String getKeyword2() {
		return keyword2;
	}

	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

}