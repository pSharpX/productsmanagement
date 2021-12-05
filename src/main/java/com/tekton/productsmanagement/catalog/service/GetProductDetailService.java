/* (C)2021 */
package com.tekton.productsmanagement.catalog.service;

import com.tekton.productsmanagement.catalog.model.endpoint.ProductDetailResponse;
import com.tekton.productsmanagement.common.util.Pipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class GetProductDetailService {

	private final ProductDetailStoreService storeService;

	private final ProductDetailThirdPartyService thirdPartyService;

	private final CreateProductDetailService productDetailService;

	public ProductDetailResponse getProductDetail(Long productId){
		log.debug("Retrieving all product-detail information: {}", productId);
		Pipeline<Long, ProductDetailResponse> stages = new Pipeline<>(this.storeService)
				.addHandler(this.thirdPartyService)
				.addHandler(this.productDetailService);

		return stages.execute(productId);
	}

}
