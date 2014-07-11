/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.integration;

import com.springtour.otg.domain.model.partner.*;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * @author 001595
 */
public class PartnerMatcher extends AggregateMatcher<Partner> {
    
    @Override
    protected String toString(Partner aggregate) {
        return new ReflectionToStringBuilder(aggregate, ToStringStyle.SIMPLE_STYLE).
                setExcludeFieldNames(new String[]{"recommendedGateways"}).append(toString(aggregate.getRecommendedGateways())).toString();
    }
    
    private String toString(List<RecommendedGateway> gateways) {
        StringBuilder builder = new StringBuilder();
        for (RecommendedGateway gateway : gateways) {
            builder.append(new ReflectionToStringBuilder(gateway,ToStringStyle.SIMPLE_STYLE).toString());
        }
        return builder.toString();
    }
}
