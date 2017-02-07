package org.mybatis.plugin.exception;

/**
 * 调用Mapper类的方法时发生的异常
 * @author shiw
 *
 */
@SuppressWarnings("serial")
public class InvokeMethodException extends RuntimeException {

	public InvokeMethodException() {
		super();
	}

	public InvokeMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvokeMethodException(String message) {
		super(message);
	}

	public InvokeMethodException(Throwable cause) {
		super(cause);
	}

}
