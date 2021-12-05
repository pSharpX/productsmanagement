/* (C)2021 */
package com.tekton.productsmanagement.common.model.endpoint;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tekton.productsmanagement.common.model.contants.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

	private ErrorType errorType;

	private String message;

	private String code;

	private String title;

	public ErrorResponse(ErrorType errorType) {
		this.errorType = errorType;
	}

}
