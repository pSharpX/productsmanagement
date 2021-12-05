/* (C)2021 */
package com.tekton.productsmanagement.web.rest.error;

import javax.validation.ConstraintViolationException;

import com.tekton.productsmanagement.common.exception.ServiceException;
import com.tekton.productsmanagement.common.model.contants.ErrorType;
import com.tekton.productsmanagement.common.model.endpoint.ErrorResponse;
import com.tekton.productsmanagement.common.util.ErrorMessageUtils;
import com.tekton.productsmanagement.common.util.ErrorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ControllerExceptionHandler {

	private final MessageSource messageSource;

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ErrorResponse serviceException(ServiceException ex){
		ErrorResponse errorResponse = new ErrorResponse(ErrorType.API_ERROR);
		String message = ErrorUtils.getMessage(this.messageSource, ex);
		errorResponse.setMessage(message);
		log.error("ServiceException: {}", message, ex);
		return errorResponse;
	}

	@ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class,
			ConstraintViolationException.class, HttpMessageNotReadableException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorResponse constraintException(Exception ex){
		ErrorResponse errorResponse = new ErrorResponse(ErrorType.INVALID_REQUEST_ERROR);
		String message = ErrorUtils.getMessage(this.messageSource, ErrorMessageUtils.MISSING_REQUEST_VALUE);
		errorResponse.setMessage(message);
		log.error("InvalidRequestException: {}", message, ex);
		return errorResponse;
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ErrorResponse missingParameterException(MissingServletRequestParameterException ex){
		ErrorResponse errorResponse = new ErrorResponse(ErrorType.INVALID_REQUEST_ERROR);
		String message = "Missing argument(s)";
		errorResponse.setMessage(message);
		log.error("MissingParameterException: {}", message, ex);
		return errorResponse;
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	private ErrorResponse globalExceptionHandler(Exception ex){
		ErrorResponse errorResponse = new ErrorResponse(ErrorType.API_ERROR);
		String message = ErrorMessageUtils.DEFAULT_ERROR_MESSAGE;
		errorResponse.setMessage(message);
		log.error("Exception: {}", message, ex);
		return errorResponse;
	}

}
