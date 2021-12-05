/* (C)2021 */
package com.tekton.productsmanagement.integration;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

import com.tekton.productsmanagement.integration.config.ThirdPartyApiClientProperties;
import com.tekton.productsmanagement.integration.model.constants.ThirdPartyApiEndpoint;
import com.tekton.productsmanagement.integration.model.endpoint.ProductStockRequest;
import com.tekton.productsmanagement.integration.model.endpoint.ProductStockResponse;
import com.tekton.productsmanagement.integration.util.ThirdPartyApiUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class ThirdPartyApiClient {

	private final ThirdPartyApiClientProperties properties;

	private final RestTemplate restTemplate;

	public ProductStockResponse getProductStockDetail(ProductStockRequest request) {
		return handle(HttpMethod.GET, ThirdPartyApiEndpoint.DETAIL_SERVICE, request, ProductStockResponse.class);
	}

	private <I, O> O handle(HttpMethod method, ThirdPartyApiEndpoint endpoint, I request, Class<O> responseClass){
		return handle(method, endpoint, null, null, request, responseClass);
	}

	private <I, O> O handle(HttpMethod method, ThirdPartyApiEndpoint endpoint, MultiValueMap<String, String> queryParams, Map<String, Object> pathParams, I requestBody, Class<O> responseClass) {
		URI uri = ThirdPartyApiUtils.buildUri(this.properties.getScheme(), this.properties.getHost(), this.properties.getPort(), this.properties.getServices().get(endpoint.getId()), queryParams, pathParams);

		RequestEntity.BodyBuilder requestBuilder = RequestEntity.method(method, uri);
		RequestEntity requestEntity = Objects.isNull(requestBody) ? requestBuilder.build(): requestBuilder.body(requestBody);

		ResponseEntity<O> responseEntity = this.restTemplate.exchange(requestEntity, responseClass);
		return responseEntity.getBody();
	}

}
