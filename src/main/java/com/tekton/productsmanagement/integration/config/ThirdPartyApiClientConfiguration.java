/* (C)2021 */
package com.tekton.productsmanagement.integration.config;

import javax.annotation.PostConstruct;

import com.tekton.productsmanagement.integration.client.ThirdPartyApiClient;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(ThirdPartyApiClientProperties.class)
@RequiredArgsConstructor
public class ThirdPartyApiClientConfiguration {

	public static final String TP_REST_TEMPLATE = "tpRestTemplate";

	private final ThirdPartyApiClientProperties properties;

	private final RestTemplate restTemplate;

	@PostConstruct
	public void configure() {
		this.restTemplate.setErrorHandler(new ThirdPartyApiErrorHandler());
	}

	@Bean
	public ThirdPartyApiClient client(){
		return new ThirdPartyApiClient(this.properties, this.restTemplate);
	}

}
