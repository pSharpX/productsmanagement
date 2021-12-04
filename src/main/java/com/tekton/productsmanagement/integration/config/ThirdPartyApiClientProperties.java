/* (C)2021 */
package com.tekton.productsmanagement.integration.config;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "tp.api")
public class ThirdPartyApiClientProperties {

	private String scheme;

	private String host;

	private String port;

	private Map<String, String> services;

}
