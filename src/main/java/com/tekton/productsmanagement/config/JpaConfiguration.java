/* (C)2021 */
package com.tekton.productsmanagement.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.tekton.productsmanagement")
@EntityScan("com.tekton.productsmanagement")
public class JpaConfiguration {}
