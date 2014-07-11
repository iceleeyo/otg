/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

import com.springtour.otg.interfaces.admin.facade.dto.GatewayDto;
import java.util.List;

/**
 *
 * @author 006874
 */
public class UpdateGatewaysRq extends GenericRq {
   private String channelId;
   private List<GatewayDto> gateways;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public List<GatewayDto> getGateways() {
        return gateways;
    }

    public void setGateways(List<GatewayDto> gateways) {
        this.gateways = gateways;
    }
   
}
