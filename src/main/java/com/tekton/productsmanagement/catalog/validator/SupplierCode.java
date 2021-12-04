/* (C)2021 */
package com.tekton.productsmanagement.catalog.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = SupplierCodeValidator.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SupplierCode {

	String message() default "Supplier code is not valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
