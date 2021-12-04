/* (C)2021 */
package com.tekton.productsmanagement.catalog.model.dto;

import com.tekton.productsmanagement.catalog.model.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

	private String code;

	private String name;

	private String shortName;

	private String description;

	public CategoryDto(Category category) {
		this.code = category.getCode();
		this.name = category.getName();
		this.shortName = category.getShortName();
		this.description = category.getDescription();
	}

}
