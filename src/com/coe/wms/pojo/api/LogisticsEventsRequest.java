package com.coe.wms.pojo.api;

import java.io.Serializable;

/**
 * 父类
 * 
 * @author Administrator
 * 
 */
public class LogisticsEventsRequest implements Serializable {

	private static final long serialVersionUID = 8395778276101740899L;

	private EventHeader eventHeader;

	private EventBody eventBody;
}
