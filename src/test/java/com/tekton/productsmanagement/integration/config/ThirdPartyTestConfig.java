/* (C)2021 */
package com.tekton.productsmanagement.integration.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
@EnableConfigurationProperties(ThirdPartyApiClientProperties.class)
public class ThirdPartyTestConfig {

}
