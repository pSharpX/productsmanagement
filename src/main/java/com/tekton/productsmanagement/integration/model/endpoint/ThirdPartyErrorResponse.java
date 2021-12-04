/* (C)2021 */
package com.tekton.productsmanagement.integration.model.endpoint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyErrorResponse {

	private String code;

	private String message;

	private String description;

}
