/* (C)2021 */
package com.tekton.productsmanagement.catalog.service;

import com.tekton.productsmanagement.catalog.model.entity.Product;
import com.tekton.productsmanagement.catalog.repository.ProductRepository;
import com.tekton.productsmanagement.common.exception.ServiceException;
import com.tekton.productsmanagement.common.util.ErrorMessageUtils;
import com.tekton.productsmanagement.common.util.Handler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductDetailStoreService implements Handler<Long, Product> {

	private final ProductRepository productRepository;

	@Override
	public Product process(Long input) {
		log.debug("Fetching Product Detail from store: {}", input);
		return this.productRepository.findById(input).orElseThrow(() -> new ServiceException(
				ErrorMessageUtils.PRODUCT_NOT_FOUND));
	}

}
