package com.springtour.otg.interfaces.admin.facade;

import com.springtour.otg.interfaces.admin.facade.dto.PartnerDto;
import com.springtour.otg.interfaces.admin.facade.rq.*;
import com.springtour.otg.interfaces.admin.facade.rs.*;
import java.util.List;

public interface PartnerAdminServiceFacade {

     List<PartnerDto> listAllPartners();
             
    RegisterPartnerRs register(RegisterPartnerRq rq);

    UpdateAvailableChannelsRs updateAvailableChannels(UpdateAvailableChannelsRq rq);

    ListAvailableChannelsRs listAvailableChannels(ListAvailableChannelsRq rq);

    ListRecommendedGatewaysRs listRecommendedGateways(ListRecommendedGatewaysRq rq);

    UpdateRecommendedGatewaysRs updateRecommendedGateways(UpdateRecommendedGatewaysRq rq);
}
