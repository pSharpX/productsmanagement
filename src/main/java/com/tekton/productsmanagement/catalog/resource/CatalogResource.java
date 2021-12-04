/* (C)2021 */
package com.tekton.productsmanagement.catalog.resource;

import com.tekton.productsmanagement.catalog.model.endpoint.ProductDetailResponse;
import com.tekton.productsmanagement.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogResource {

	private final CatalogService catalogService;


	@GetMapping("{id}")
	public ProductDetailResponse get(@PathVariable("id")  Long productId) {
		return this.catalogService.fetchProductById(productId);
	}

}
