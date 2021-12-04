/* (C)2021 */
package com.tekton.productsmanagement.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer objectMapperBuilder() {
		return builder -> {
			builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
			builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			builder.featuresToEnable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
			builder.defaultViewInclusion(true);
		};
	}
}
