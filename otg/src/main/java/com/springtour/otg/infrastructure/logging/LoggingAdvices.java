package com.springtour.otg.infrastructure.logging;

import static com.springtour.otg.application.util.Constants.*;

import java.lang.reflect.*;

import lombok.*;

import org.springframework.aop.*;

public class LoggingAdvices implements MethodBeforeAdvice,
		AfterReturningAdvice, ThrowsAdvice {

	@Setter
	private LoggingSupport loggingSupport;

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		loggingSupport.notifyRequest(entryDerivedFrom(target, method), args);
	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		loggingSupport.notifyResponse(entryDerivedFrom(target, method),
				returnValue);
	}

	public void afterThrowing(Method method, Object[] args, Object target,
			Throwable throwable) throws Throwable {
		loggingSupport.notifyCatching(entryDerivedFrom(target, method),
				throwable);
	}

	private String entryDerivedFrom(Object target, Method method) {
		return method.getDeclaringClass().getName() + DOT + method.getName();
	}
}
