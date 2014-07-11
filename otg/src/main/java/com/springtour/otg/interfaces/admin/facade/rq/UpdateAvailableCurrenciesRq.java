/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

/**
 *
 * @author 006874
 */
public class UpdateAvailableCurrenciesRq extends GenericRq {
    private String channelId;
    private String availableCurrencies;

    public String getAvailableCurrencies() {
        return availableCurrencies;
    }

    public void setAvailableCurrencies(String availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    
    
}
