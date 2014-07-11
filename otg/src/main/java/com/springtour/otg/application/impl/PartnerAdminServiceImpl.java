/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.impl;

import com.springtour.otg.application.PartnerAdminService;
import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.partner.PartnerRepository;
import com.springtour.otg.domain.model.partner.RecommendedGateway;
import java.util.List;

/**
 *
 * @author 001595
 */
public class PartnerAdminServiceImpl implements PartnerAdminService {

    private PartnerRepository partnerRepository;

    public void setPartnerRepository(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public void register(String partnerId, String name) {
        Partner partner = new Partner(partnerId, name);
        partnerRepository.store(partner);
    }

    @Override
    public void updateAvailableChannels(String partnerId, List<String> availableChannelIds) {
        Partner partner = partnerRepository.find(partnerId);
        partner.updateAvailableChannels(partner.availableChannels(availableChannelIds));
        partnerRepository.store(partner);
    }

    @Override
    public void updateRecommendedGateways(String partnerId, List<RecommendedGateway> recommendedGateways) {
        Partner partner = partnerRepository.find(partnerId);
        partner.updateRecommendedGateways(recommendedGateways);
        partnerRepository.store(partner);
    }
}
