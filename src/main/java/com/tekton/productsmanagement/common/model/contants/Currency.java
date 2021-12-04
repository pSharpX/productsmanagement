/* (C)2021 */
package com.tekton.productsmanagement.common.model.contants;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
	PEN("PEN", "Soles", "0000", "S/"),
	USD("USD", "Dolares", "0001", "US$"),
	NONE("", "", "", "");

	private final String id;

	private final String displayText;

	private final String code;

	private final String symbol;

	public static Currency getCurrency(String code) {
		return Arrays.stream(values()).filter(c -> c.getCode().equals(code)).findAny().orElse(NONE);
	}
}
