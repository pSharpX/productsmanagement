/* (C)2021 */
package com.tekton.productsmanagement.common.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{

	private final String key;

	private final Object[] args;

	public ServiceException(String message){
		super(message);
		this.key = message;
		this.args = null;
	}

	public ServiceException(String message, Throwable cause){
		super(message, cause);
		this.key = message;
		this.args = null;
	}

	public ServiceException(String key, Object... objects){
		super(key);
		this.key = key;
		this.args = objects;
	}

}
