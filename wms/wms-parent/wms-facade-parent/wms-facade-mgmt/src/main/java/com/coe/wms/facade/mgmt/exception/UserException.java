package com.coe.wms.facade.mgmt.exception;

import com.coe.wms.common.exception.ServiceException;

public class UserException extends ServiceException {

	public UserException(String message) {
		super(message);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -5050936470655514263L;

}
