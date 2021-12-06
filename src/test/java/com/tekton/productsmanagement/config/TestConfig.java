/* (C)2021 */
package com.tekton.productsmanagement.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan("com.tekton.productsmanagement")
@Import({SecurityConfiguration.class, RestClientConfiguration.class, MessageI18nConfiguration.class,
	JpaConfiguration.class, JacksonConfiguration.class, CacheConfiguration.class, LoggingAspectConfiguration.class})
public class TestConfig {
}
