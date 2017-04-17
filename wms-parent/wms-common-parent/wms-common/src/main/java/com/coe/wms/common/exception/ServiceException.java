package com.coe.wms.common.exception;

/**
 * 业务异常父类
 * 
 * @ClassName: ServiceException
 * @author yechao
 * @date 2017年3月28日 下午5:25:32
 * @Description: TODO
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 2332608236621015980L;

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
