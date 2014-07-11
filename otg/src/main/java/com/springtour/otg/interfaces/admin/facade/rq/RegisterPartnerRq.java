/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

/**
 *
 * @author 006874
 */
public class RegisterPartnerRq extends GenericRq {
   private String partnerId;
   private String name;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }
    
    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
}
