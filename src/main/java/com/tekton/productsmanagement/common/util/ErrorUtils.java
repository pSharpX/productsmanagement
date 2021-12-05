/* (C)2021 */
package com.tekton.productsmanagement.common.util;

import com.tekton.productsmanagement.common.exception.ServiceException;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public final class ErrorUtils {

	private ErrorUtils() {
	}

	public static String getMessage(MessageSource messageSource, Exception ex){
		if (ex instanceof ServiceException) {
			ServiceException serviceException = (ServiceException)ex;
			String errorCode = serviceException.getKey();
			String message = getMessage(messageSource, errorCode);

			if(StringUtils.hasText(message)){
				return message;
			}
		}

		return ErrorMessageUtils.DEFAULT_ERROR_MESSAGE;
	}

	public static String getMessage(MessageSource messageSource, String code) {
		try {
			return messageSource.getMessage(code, null, ErrorMessageUtils.DEFAULT_ERROR_MESSAGE, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException ex) {
			return ErrorMessageUtils.DEFAULT_ERROR_MESSAGE;
		}
	}

}
