package org.mybatis.plugin.exception;

public class UnsetMapperException extends RuntimeException {

	private static final long serialVersionUID = -7368431036032813535L;

	public UnsetMapperException() {
		super("没有在setMapper方法中指定Mapper对象");
	}

	public UnsetMapperException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UnsetMapperException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public UnsetMapperException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
