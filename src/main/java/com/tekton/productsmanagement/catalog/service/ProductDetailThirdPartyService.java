/* (C)2021 */
package com.tekton.productsmanagement.catalog.service;

import com.tekton.productsmanagement.catalog.model.entity.Product;
import com.tekton.productsmanagement.common.util.Handler;
import com.tekton.productsmanagement.integration.ThirdPartyApiClient;
import com.tekton.productsmanagement.integration.model.endpoint.ProductStockRequest;
import com.tekton.productsmanagement.integration.model.endpoint.ProductStockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductDetailThirdPartyService implements Handler<Product, Product> {

	private final ThirdPartyApiClient apiClient;

	@Override
	public Product process(Product input) {
		log.debug("Fetching Product Detail from third-party service: {}", input.getName());
		ProductStockResponse response = this.apiClient.getProductStockDetail(new ProductStockRequest(input.getId()));
		input.setUnitPrice(response.getUnitPrice());
		input.setUnitsInStock(response.getUnitsInStock());
		input.setDiscontinued(response.isDiscontinued());
		return input;
	}

}
