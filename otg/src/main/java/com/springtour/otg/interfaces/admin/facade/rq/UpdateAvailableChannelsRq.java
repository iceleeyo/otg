/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

import java.util.List;

/**
 *
 * @author 006874
 */
public class UpdateAvailableChannelsRq extends GenericRq {
    private String partnerId;
    private List<String> availableChannelIds;

    public List<String> getAvailableChannelIds() {
        return availableChannelIds;
    }

    public void setAvailableChannelIds(List<String> availableChannelIds) {
        this.availableChannelIds = availableChannelIds;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    
}
