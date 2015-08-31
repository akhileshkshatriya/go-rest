package com.goeuro.dev.test.exception;

public class ServiceException extends RuntimeException {

	static final long serialVersionUID = 1L;

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String code, Throwable cause) {
		super(cause);
	}

}
