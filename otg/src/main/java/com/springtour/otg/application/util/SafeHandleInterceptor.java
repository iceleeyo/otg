/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.util;

import com.springtour.otg.application.exception.CannotReconsitituteNotificationException;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.aop.framework.ReflectiveMethodInvocation;

/**
 *
 * @author 001595
 */
public class SafeHandleInterceptor implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger();
    private int maxRetryCount = 3;

    public void setMaxRetryCount(int maxRetryCount) {
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        int retryCount = 0;
        while (true) {
            try {
                ReflectiveMethodInvocation inv = (ReflectiveMethodInvocation) invocation;
                // 在proceed调用之前必须clone，否则只能调用一次
                MethodInvocation anotherInvocation = inv.invocableClone();
                return anotherInvocation.proceed();
            } catch (CannotReconsitituteNotificationException e) {
                if (retryCount++ >= maxRetryCount) {
                    throw e;
                } else {
                    logger.info("retry for exception:" + e.getMessage(), e);
                    continue;
                }
            }
        }
    }
}
