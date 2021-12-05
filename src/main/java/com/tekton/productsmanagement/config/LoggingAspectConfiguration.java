/* (C)2021 */
package com.tekton.productsmanagement.config;

import com.tekton.productsmanagement.aop.LoggingAspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}

}
