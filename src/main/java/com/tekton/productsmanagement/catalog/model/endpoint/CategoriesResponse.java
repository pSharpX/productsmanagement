/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.endpoint;

import java.util.List;

import com.tekton.productsmanagement.catalog.model.dto.CategoryDto;
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
public class CategoriesResponse {

	private List<CategoryDto> categories;

}
