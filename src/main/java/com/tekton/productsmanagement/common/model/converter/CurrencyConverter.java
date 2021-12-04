/* (C)2021 */
package com.tekton.productsmanagement.common.model.converter;

import com.tekton.productsmanagement.common.model.contants.Currency;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter implements AttributeConverter<Currency, String> {

	@Override
	public String convertToDatabaseColumn(Currency attribute) {
		if (Objects.nonNull(attribute)) {
			return attribute.getCode();
		}
		return null;
	}

	@Override
	public Currency convertToEntityAttribute(String dbData) {
		if (Objects.nonNull(dbData)) {
			return Currency.getCurrency(dbData);
		}
		return null;
	}
}
