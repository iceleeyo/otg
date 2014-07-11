package com.springtour.otg.interfaces.admin.facade;

import com.springtour.otg.interfaces.admin.facade.rq.*;
import com.springtour.otg.interfaces.admin.facade.rs.*;

public interface MerchantAdminServiceFacade {

    RegisterMerchantRs register(RegisterMerchantRq rq);

    UpdateMerchantInfoRs updateInfo(UpdateMerchantInfoRq rq);

    ListMerchantsRs list(ListMerchantsRq rq);
    
    FindMerchantRs findById(FindMerchantRq rq);
}
