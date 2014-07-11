/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.partner;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author 001595
 */
public class PartnerFixture {

    public static final String DEFAULT_PARTNER = "3";
    public static final String DEFAULT_AVAILABLE_CHANNELS = "12,15";
    public static final List<RecommendedGateway> DEFAULT_RECOMMENDED_GATEWAYS =
            Arrays.asList(new RecommendedGateway(0, "12", "CCB"), new RecommendedGateway(1, "12", "ICBC"));
    private String id = DEFAULT_PARTNER;
    private String availableChannels = DEFAULT_AVAILABLE_CHANNELS;
    private List<RecommendedGateway> recommendedGateways = DEFAULT_RECOMMENDED_GATEWAYS;

    public Partner build() {
        Partner partner = new Partner(id, "ORM");
        partner.updateAvailableChannels(availableChannels);
        partner.updateRecommendedGateways(recommendedGateways);
        return partner;
    }
}
