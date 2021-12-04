/* (C)2021 */
package com.tekton.productsmanagement.integration.config;

import java.io.IOException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekton.productsmanagement.integration.exception.ThirdPartyApiException;
import com.tekton.productsmanagement.integration.exception.ThirdPartyTimeoutException;
import com.tekton.productsmanagement.integration.model.endpoint.ThirdPartyErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class ThirdPartyApiErrorHandler implements ResponseErrorHandler {

	private static final Logger LOG = LoggerFactory.getLogger(ThirdPartyApiErrorHandler.class);

	private static final String DEFAULT_ERROR_MESSAGE = "Something went wrong. Try again";

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		HttpStatus status = response.getStatusCode();
		HttpStatus.Series series = status.series();

		if(HttpStatus.Series.SERVER_ERROR == series) {
			if(HttpStatus.GATEWAY_TIMEOUT == status) {
				throw new ThirdPartyTimeoutException();
			}
			throw new ThirdPartyApiException(DEFAULT_ERROR_MESSAGE, status);
		} else if(HttpStatus.Series.CLIENT_ERROR == series) {
			try{
				ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				ThirdPartyErrorResponse error = objectMapper.readValue(response.getBody(), ThirdPartyErrorResponse.class);
				throw new ThirdPartyApiException(error.getMessage(), status);
			}catch (ThirdPartyApiException ex){
				throw ex;
			}catch (Exception ex){
				LOG.error("Something went wrong when sending request: ", ex);
				throw new ThirdPartyApiException(DEFAULT_ERROR_MESSAGE, status);
			}
		}
	}

}
