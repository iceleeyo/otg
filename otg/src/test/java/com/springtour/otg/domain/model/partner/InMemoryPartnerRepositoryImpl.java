/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.partner;

import com.springtour.otg.domain.model.partner.Partner;
import com.springtour.otg.domain.model.partner.PartnerRepository;
import java.util.List;

/**
 *
 * @author 001595
 */
public class InMemoryPartnerRepositoryImpl implements PartnerRepository {

    @Override
    public Partner find(String partnerId) {
        Partner partner =  new Partner("1", "hhehe");
        return partner;
    }

    @Override
    public void store(Partner partner) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Partner> listAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
