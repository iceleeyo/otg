/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

import com.springtour.otg.interfaces.transacting.facade.dto.RecommendedGatewayDto;
import java.util.List;

/**
 *
 * @author 006874
 */
public class UpdateRecommendedGatewaysRq extends GenericRq {

    private String partnerId;
    private List<RecommendedGatewayDto> dtos;

    public List<RecommendedGatewayDto> getDtos() {
        return dtos;
    }

    public void setDtos(List<RecommendedGatewayDto> dtos) {
        this.dtos = dtos;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
}
