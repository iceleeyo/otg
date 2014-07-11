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
public class ChannelIdMatcher extends TypeSafeMatcher<Channel> {

    private final Channel expected;

    public static ChannelIdMatcher sameIdentityAs(Channel expected) {
        return new ChannelIdMatcher(expected);
    }

    private ChannelIdMatcher(Channel expected) {
        this.expected = expected;
    }

    @Override
    public boolean matchesSafely(Channel item) {
        return expected.getId().equals(item.getId());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a channel id equals ").appendValue(expected.getId());
    }
}
