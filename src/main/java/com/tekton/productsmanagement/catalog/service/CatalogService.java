/* (C)2021 */
package com.tekton.productsmanagement.catalog.service;

import com.tekton.productsmanagement.catalog.model.endpoint.ProductDetailResponse;
import com.tekton.productsmanagement.catalog.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CatalogService {

	private final ProductRepository productRepository;

	public ProductDetailResponse fetchProductById(Long productId){
		return new ProductDetailResponse();
	}

}
