/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.domain.model.channel.*;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author 001595
 */
public class ChannelMatcher extends AggregateMatcher<Channel> {
    
    @Override
    protected String toString(Channel aggregate) {
        return new ReflectionToStringBuilder(aggregate, ToStringStyle.SIMPLE_STYLE).
                setExcludeFieldNames(new String[]{"gateways"}).append(toString(aggregate.getGateways())).toString();
    }
    
    private String toString(List<Gateway> gateways) {
        StringBuilder builder = new StringBuilder();
        for (Gateway gateway : gateways) {
            builder.append(new ReflectionToStringBuilder(gateway,ToStringStyle.SIMPLE_STYLE).toString());
        }
        return builder.toString();
    }
}
