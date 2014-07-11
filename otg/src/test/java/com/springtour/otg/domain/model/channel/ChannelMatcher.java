/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.channel;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author 001595
 */
public class ChannelMatcher extends TypeSafeMatcher<Channel> {

    private final Channel expected;

    public static ChannelMatcher sameAs(Channel expected) {
        return new ChannelMatcher(expected);
    }

    private ChannelMatcher(Channel expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Channel actual) {
        return toString(expected).equals(toString(actual));
    }

    private String toString(Channel channel) {
        return ReflectionToStringBuilder.reflectionToString(channel, ToStringStyle.SIMPLE_STYLE).toString();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a channel like ").appendValue(toString(expected));
    }
    
    @Override
    public void describeMismatchSafely(Channel actual, Description description) {
        description.appendText("a channel ").appendValue(toString(actual));
    }
}
