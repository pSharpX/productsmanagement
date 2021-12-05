/* (C)2021 */
package com.tekton.productsmanagement.catalog.service;

import com.tekton.productsmanagement.catalog.model.endpoint.ProductDetailResponse;
import com.tekton.productsmanagement.catalog.model.entity.Product;
import com.tekton.productsmanagement.common.util.Handler;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateProductDetailService implements Handler<Product, ProductDetailResponse> {

	@Override
	public ProductDetailResponse process(Product input) {
		log.debug("Building product-detail information...");
		return ProductDetailResponse
				.builder()
					.id(input.getId())
					.name(input.getName())
					.shortName(input.getShortName())
					.description(input.getDescription())
					.unitPrice(input.getUnitPrice())
					.currency(input.getCurrency())
					.unitsInStock(input.getUnitsInStock())
					.discontinued(input.isDiscontinued())
					.categoryCode(input.getCategory().getCode())
					.categoryName(input.getCategory().getName())
					.supplierCode(input.getSupplier().getCode())
					.supplierName(input.getSupplier().getCompanyName())
				.build();
	}

}
