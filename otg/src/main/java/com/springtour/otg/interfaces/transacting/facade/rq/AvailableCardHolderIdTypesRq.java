/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.transacting.facade.rq;

/**
 *
 * @author 001595
 */
public class AvailableCardHolderIdTypesRq extends GenericRq {

    private String channelId;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
