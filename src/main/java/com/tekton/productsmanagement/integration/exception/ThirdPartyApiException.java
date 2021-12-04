/* (C)2021 */
package com.tekton.productsmanagement.integration.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class ThirdPartyApiException extends RuntimeException{

	private final HttpStatus status;

	public ThirdPartyApiException(String message, HttpStatus status){
		super(message);
		this.status = status;
	}

}
