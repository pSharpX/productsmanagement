/* (C)2021 */
package com.tekton.productsmanagement.integration.exception;

import org.springframework.http.HttpStatus;

public class ThirdPartyTimeoutException extends ThirdPartyApiException {

	public static final String DEFAULT_ERROR_MESSAGE = "The server couldn't complete complete the request because of timeout";

	public ThirdPartyTimeoutException(){
		this(DEFAULT_ERROR_MESSAGE);
	}

	public ThirdPartyTimeoutException(String message){
		super(message, HttpStatus.GATEWAY_TIMEOUT);
	}
}
