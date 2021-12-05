/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.endpoint;

import java.math.BigDecimal;

import com.tekton.productsmanagement.common.model.contants.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductDetailResponse {

	private Long id;

	private String name;

	private String shortName;

	private String description;

	private BigDecimal unitPrice;

	private Currency currency;

	private Long unitsInStock;

	private boolean discontinued;

	private String categoryCode;

	private String categoryName;

	private String supplierCode;

	private String supplierName;

}
