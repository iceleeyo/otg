/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.channel;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 *
 * @author 001595
 */
public class ChannelNameMatcher extends TypeSafeMatcher<Channel> {

    private final Channel expected;

    public static ChannelNameMatcher sameNameAs(Channel expected) {
        return new ChannelNameMatcher(expected);
    }

    private ChannelNameMatcher(Channel expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Channel actual) {
        return !expected.getName().equals(actual.getName());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a channel name equals ").appendValue(expected.getName());
    }
}
