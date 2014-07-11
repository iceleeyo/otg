package com.springtour.otg.application;

import com.springtour.otg.domain.model.partner.RecommendedGateway;
import java.util.List;

public interface PartnerAdminService {

    void register(String partnerId, String name);

    void updateAvailableChannels(String partnerId, List<String> availableChannelIds);

    void updateRecommendedGateways(String partnerId, List<RecommendedGateway> recommendedGateways);
}
