package org.mybatis.plugin.exception;

/**
 * 查询条件类没有继承QueryParam类的异常
 * @author shiw
 *
 */
public class NotQueryParamSubclassException extends Exception {

	private static final long serialVersionUID = -7681983712529826480L;

	public NotQueryParamSubclassException() {
		super();
	}

	public NotQueryParamSubclassException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotQueryParamSubclassException(String message) {
		super(message + "查询条件类必须继承QueryParam类");
	}

	public NotQueryParamSubclassException(Throwable cause) {
		super(cause);
	}

}
