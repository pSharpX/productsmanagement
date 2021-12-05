/* (C)2021 */
package com.tekton.productsmanagement.integration.model.endpoint;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockResponse {

	private BigDecimal unitPrice;

	private Long unitsInStock;

	private boolean discontinued;

}
