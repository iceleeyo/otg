/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

/**
 *
 * @author 006874
 */
public class RegisterPartnerRs extends GenericRs {

    public RegisterPartnerRs(String statusCode) {
        super(statusCode);
    }

    public RegisterPartnerRs(String statusCode, String message) {
        super(statusCode, message);
    }
    @Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
