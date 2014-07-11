/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author 001595
 */
public abstract class AggregateMatcher<T> {

    public boolean matchesSafely(T expect, T actual) {
        System.out.println("expect=" + toString(expect));
        System.out.println("actual=" + toString(actual));
        return toString(expect).equals(toString(actual));
    }

    protected String toString(T aggreate) {
        return ReflectionToStringBuilder
        .reflectionToString(aggreate, ToStringStyle.SIMPLE_STYLE).toString();
    }
}
