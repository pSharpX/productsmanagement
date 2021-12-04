/* (C)2021 */
package com.tekton.productsmanagement.integration.model.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ThirdPartyApiEndpoint {

	MASTER_SERVICE("master"),
	DETAIL_SERVICE("detail");

	private final String id;

}
