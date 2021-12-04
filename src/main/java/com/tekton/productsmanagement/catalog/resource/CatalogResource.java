/* (C)2021 */
package com.tekton.productsmanagement.catalog.resource;

import javax.validation.Valid;

import com.tekton.productsmanagement.catalog.model.endpoint.CategoriesResponse;
import com.tekton.productsmanagement.catalog.model.endpoint.CreateProductRequest;
import com.tekton.productsmanagement.catalog.model.endpoint.ProductDetailResponse;
import com.tekton.productsmanagement.catalog.model.endpoint.SuppliersResponse;
import com.tekton.productsmanagement.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogResource {

	private final CatalogService catalogService;

	@GetMapping("{id}")
	public ProductDetailResponse get(@PathVariable("id")  Long productId) {
		return this.catalogService.findProductById(productId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody CreateProductRequest productRequest){
		this.catalogService.create(productRequest);
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public void update(@PathVariable("id")  Long productId, @Valid @RequestBody CreateProductRequest productRequest){
		this.catalogService.update(productId, productRequest);
	}

	@GetMapping("/categories")
	public CategoriesResponse categories() {
		return this.catalogService.fetchCategories();
	}

	@GetMapping("/suppliers")
	public SuppliersResponse suppliers() {
		return this.catalogService.fetchSuppliers();
	}

}
