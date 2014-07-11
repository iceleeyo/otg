/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.transacting.facade.rs;

import java.util.List;

/**
 *
 * @author 001595
 */
public class AvailableCardHolderIdTypesRs extends GenericRs {

    /**
     * 可用证件类型列表
     */
    private List<String> idTypes;

    public AvailableCardHolderIdTypesRs(String success, List<String> idTypes) {
        super(success);
        this.idTypes = idTypes;
    }

    public AvailableCardHolderIdTypesRs(String success) {
        super(success);
    }

    public List<String> getIdTypes() {
        return idTypes;
    }

    @Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
