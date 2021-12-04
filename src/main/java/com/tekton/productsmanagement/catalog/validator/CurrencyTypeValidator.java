/* (C)2021 */
package com.tekton.productsmanagement.catalog.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.tekton.productsmanagement.common.model.contants.Currency;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Slf4j
@Component
public class CurrencyTypeValidator implements ConstraintValidator<CurrencyType, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(!StringUtils.hasText(value)) {
			log.debug("The value for the currency is not valid");
			return false;
		}

		Currency currency = Currency.getCurrencyById(value);
		return !Currency.NONE.equals(currency);
	}

}
