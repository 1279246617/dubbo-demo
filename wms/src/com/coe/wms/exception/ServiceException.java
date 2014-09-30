package com.coe.wms.exception;


public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -7384963515226728635L;

	public ServiceException(String message) {
		super(message);
	}
}
