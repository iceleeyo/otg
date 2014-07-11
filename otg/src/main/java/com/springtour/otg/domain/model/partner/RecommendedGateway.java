/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.partner;

/**
 *
 * @author 001595
 */
public class RecommendedGateway {

    private int priority;
    private String channel;
    private String gateway;

    public RecommendedGateway(int priority, String channel, String gateway) {
        this.priority = priority;
        this.channel = channel;
        this.gateway = gateway;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public RecommendedGateway() {
    }
}
