/* (C)2021 */
package com.tekton.productsmanagement.catalog.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tekton.productsmanagement.catalog.model.dto.CategoryDto;
import com.tekton.productsmanagement.catalog.service.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Slf4j
@Component
public class CategoryCodeValidator implements ConstraintValidator<CategoryCode, String> {

	private final CatalogService catalogService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!StringUtils.hasText(value)) {
			log.debug("The value for the category is not valid");
			return false;
		}

		Optional<CategoryDto> category = this.catalogService.findCategoryByCode(value);
		return category.isPresent();
	}
}
