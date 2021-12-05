/* (C)2021 */
package com.tekton.productsmanagement.aop;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class LoggingAspect {

	@Pointcut(
			"within(com.tekton.productsmanagement.catalog.repository..*) || within(com.tekton.productsmanagement.catalog.service..*) || within(com.tekton.productsmanagement.catalog.resource..*) || within(com.tekton.productsmanagement.integration.client..*)"
	)
	public void monitor() { }

	@AfterThrowing(pointcut = "monitor()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e){
		Object cause = Optional.ofNullable((Object) e.getCause()).orElse("NULL");

		log.error("Exception in {}.{}() with cause = '{}' and exception = '{}'",
				joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), cause,
				e.getMessage(), e);
	}

	@Around("monitor()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		LocalTime begin = null;
		Object result = null;
		final Signature signature = joinPoint.getSignature();

		if(log.isDebugEnabled()) {
			begin = LocalTime.now();
		}

		try {
			result = joinPoint.proceed();
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument: {} in {}#{}", objectToString(joinPoint.getArgs(), "NO ARGS"),
					signature.getDeclaringTypeName(), signature.getName());
			throw e;
		} finally {
			if(log.isDebugEnabled()){
				final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;
				LocalTime end = LocalTime.now();
				LocalTime start = Optional.ofNullable(begin).orElse(end);
				long duration = ChronoUnit.MILLIS.between(start, end);

				String args = objectToString(joinPoint.getArgs(), "NO ARGS");
				String answer = objectToString(result, "NO RESULT");

				log.debug("Execute {}#{} start={} end={} duration={} ms. \nwith argument(s)={} \nresult={}",
						signature.getDeclaringTypeName(), signature.getName(), start.format(formatter),
						end.format(formatter), duration, args, answer);
			}
		}
	}

	private String objectToString(Object object, String otherwise) {
		return Optional.ofNullable(object).map(o -> new ReflectionToStringBuilder(object,
				RecursiveToStringStyle.JSON_STYLE).toString()).orElse(otherwise);
	}

}
