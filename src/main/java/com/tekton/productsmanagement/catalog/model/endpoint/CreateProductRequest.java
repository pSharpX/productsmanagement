/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.endpoint;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.tekton.productsmanagement.catalog.validator.CategoryCode;
import com.tekton.productsmanagement.catalog.validator.CurrencyType;
import com.tekton.productsmanagement.catalog.validator.SupplierCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Size(max = 50)
	private String shortName;

	private String description;

	@NotNull
	private BigDecimal unitPrice;

	@CurrencyType
	private String currency;

	@NotNull
	private Long unitsInStock;

	private boolean discontinued;

	@CategoryCode
	private String categoryCode;

	@SupplierCode
	private String supplierCode;

}
