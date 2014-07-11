/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal;

import com.springtour.otg.application.ChannelAdminService;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.interfaces.admin.facade.ChannelAdminServiceFacade;
import com.springtour.otg.interfaces.admin.facade.dto.ChannelDto;
import com.springtour.otg.interfaces.admin.facade.dto.GatewayDto;
import com.springtour.otg.interfaces.admin.facade.internal.assembler.ChannelDtoAssembler;
import com.springtour.otg.interfaces.admin.facade.internal.assembler.GatewayDtoAssembler;
import com.springtour.otg.interfaces.admin.facade.rq.*;
import com.springtour.otg.interfaces.admin.facade.rs.UpdateAvailableCurrenciesRs;
import com.springtour.otg.interfaces.admin.facade.rs.UpdateGatewaysRs;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author 001595
 */
public class ChannelAdminServiceFacadeImpl implements ChannelAdminServiceFacade {

    private ChannelRepository channelRepository;
    private ChannelAdminService channelAdminService;
    private Logger logger = LoggerFactory.getLogger();

    public void setChannelAdminService(ChannelAdminService channelAdminService) {
        this.channelAdminService = channelAdminService;
    }

    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public List<ChannelDto> listAll() {
        try {
            return ChannelDtoAssembler.toDtos(channelRepository.listAll());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public List<GatewayDto> listGateways(String channelId) {
        try {
            return GatewayDtoAssembler.toGatewayDtoList(channelRepository.find(channelId).getGateways());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public UpdateAvailableCurrenciesRs updateAvailableCurrencies(UpdateAvailableCurrenciesRq rq) {
        logger.info(rq);
        try {
            channelAdminService.updateAvailableCurrencies(rq.getChannelId(), rq.getAvailableCurrencies());
            UpdateAvailableCurrenciesRs rs = new UpdateAvailableCurrenciesRs(StatusCode.SUCCESS);
            logger.info(rs);
            return rs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new UpdateAvailableCurrenciesRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public UpdateGatewaysRs updateGateways(UpdateGatewaysRq rq) {
        logger.info(rq);
        try {
            channelAdminService.updateGateways(rq.getChannelId(), new GatewayDtoAssembler().fromDto(rq.getGateways()));
            UpdateGatewaysRs rs = new UpdateGatewaysRs(StatusCode.SUCCESS);
            logger.info(rs);
            return rs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new UpdateGatewaysRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public ChannelDto findChannelById(String channelId) {
        ChannelDto channel = new ChannelDto();
        try {
            channel = ChannelDtoAssembler.toChannelDto(channelRepository.find(channelId));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return channel;
    }
}
