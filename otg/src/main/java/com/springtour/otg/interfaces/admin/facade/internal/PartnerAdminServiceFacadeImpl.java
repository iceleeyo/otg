/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal;

import com.springtour.otg.application.PartnerAdminService;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.domain.model.partner.*;
import com.springtour.otg.domain.model.channel.*;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.interfaces.admin.facade.PartnerAdminServiceFacade;
import com.springtour.otg.interfaces.admin.facade.dto.ChannelDto;
import com.springtour.otg.interfaces.admin.facade.dto.PartnerDto;
import com.springtour.otg.interfaces.admin.facade.internal.assembler.ChannelDtoAssembler;
import com.springtour.otg.interfaces.admin.facade.internal.assembler.PartnerDtoAssembler;
import com.springtour.otg.interfaces.admin.facade.rq.*;
import com.springtour.otg.interfaces.admin.facade.rs.*;
import com.springtour.otg.interfaces.transacting.facade.internal.assembler.RecommendedGatewayDtoAssembler;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author 006874
 */
public class PartnerAdminServiceFacadeImpl implements PartnerAdminServiceFacade {

    @Override
    public List<PartnerDto> listAllPartners() {
        try {
            List<Partner> partners = partnerRepository.listAll();
            return new PartnerDtoAssembler().toDtos(partners);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public RegisterPartnerRs register(RegisterPartnerRq rq) {
        logger.info(rq);
        try {
            partnerAdminService.register(rq.getPartnerId(),
                    rq.getName());
            RegisterPartnerRs rs = new RegisterPartnerRs(StatusCode.SUCCESS);
            logger.info(rs);
            return rs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new RegisterPartnerRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public UpdateAvailableChannelsRs updateAvailableChannels(UpdateAvailableChannelsRq rq) {
        logger.info(rq);
        try {
            partnerAdminService.updateAvailableChannels(rq.getPartnerId(), rq.getAvailableChannelIds());
            UpdateAvailableChannelsRs rs =  new UpdateAvailableChannelsRs(StatusCode.SUCCESS);
            logger.info(rs);
            return rs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new UpdateAvailableChannelsRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }

    }

    @Override
    public ListAvailableChannelsRs listAvailableChannels(ListAvailableChannelsRq rq) {
        try {
            List<ChannelDto> availChannels = new ArrayList<ChannelDto>();
            Partner partner = partnerRepository.find(rq.getPartnerId());
            availChannels = ChannelDtoAssembler.toDtos(partner.availableChannels(channelRepository));
            return new ListAvailableChannelsRs(StatusCode.SUCCESS, availChannels);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ListAvailableChannelsRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public ListRecommendedGatewaysRs listRecommendedGateways(ListRecommendedGatewaysRq rq) {
        try {
            Partner partner = partnerRepository.find(rq.getPartnerId());
            List<RecommendedGateway> recommendedGateways = partner.getRecommendedGateways();
            return new ListRecommendedGatewaysRs(StatusCode.SUCCESS, new RecommendedGatewayDtoAssembler().toDto(recommendedGateways));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ListRecommendedGatewaysRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public UpdateRecommendedGatewaysRs updateRecommendedGateways(UpdateRecommendedGatewaysRq rq) {
        logger.info(rq);
        try {
            List<RecommendedGateway> recommendedGateways = new RecommendedGatewayDtoAssembler().fromDto(rq.getDtos());
            partnerAdminService.updateRecommendedGateways(rq.getPartnerId(), recommendedGateways);
            UpdateRecommendedGatewaysRs rs =  new UpdateRecommendedGatewaysRs(StatusCode.SUCCESS);
            logger.info(rs);
            return rs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new UpdateRecommendedGatewaysRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }
    private PartnerRepository partnerRepository;
    private ChannelRepository channelRepository;
    private PartnerAdminService partnerAdminService;
    private Logger logger = LoggerFactory.getLogger();

    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public void setPartnerAdminService(PartnerAdminService partnerAdminService) {
        this.partnerAdminService = partnerAdminService;
    }

    public void setPartnerRepository(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }
}
