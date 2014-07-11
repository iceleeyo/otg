package com.springtour.otg.interfaces.admin.facade;

import com.springtour.otg.interfaces.admin.facade.dto.ChannelDto;
import com.springtour.otg.interfaces.admin.facade.dto.GatewayDto;
import com.springtour.otg.interfaces.admin.facade.rq.UpdateAvailableCurrenciesRq;
import com.springtour.otg.interfaces.admin.facade.rq.UpdateGatewaysRq;
import com.springtour.otg.interfaces.admin.facade.rs.UpdateAvailableCurrenciesRs;
import com.springtour.otg.interfaces.admin.facade.rs.UpdateGatewaysRs;
import java.util.List;

public interface ChannelAdminServiceFacade {

    List<ChannelDto> listAll();

    List<GatewayDto> listGateways(String channelId);

    UpdateAvailableCurrenciesRs updateAvailableCurrencies(UpdateAvailableCurrenciesRq rq);

    UpdateGatewaysRs updateGateways(UpdateGatewaysRq rq);
    
    ChannelDto findChannelById(String channelId);
}
