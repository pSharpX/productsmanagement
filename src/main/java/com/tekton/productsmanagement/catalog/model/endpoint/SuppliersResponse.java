/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.endpoint;

import java.util.List;

import com.tekton.productsmanagement.catalog.model.dto.SupplierDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SuppliersResponse {

	private List<SupplierDto> suppliers;

}
