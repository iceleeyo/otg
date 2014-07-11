/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.channel;

public enum Channels {

    SPRINGCARD("2"),
    SHFFT("4"),
    TENPAY("5"),
    CCB("6"),
    ALIPAY("7"),
    CHINAPNR("12"),
    BILL99("15");
    private final String id;

    private Channels(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean identityEqual(String channel) {
        return id.equals(channel);
    }
}
