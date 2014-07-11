package com.springtour.otg.domain.model.partner;

import java.util.List;

public interface PartnerRepository {

    Partner find(String partnerId);

    void store(Partner partner);

    List<Partner> listAll(); 
}
