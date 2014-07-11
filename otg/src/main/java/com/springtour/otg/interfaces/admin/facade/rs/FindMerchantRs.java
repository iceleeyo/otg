/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

import com.springtour.otg.interfaces.admin.facade.dto.MerchantDto;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 *
 * @author 006874
 */
public class FindMerchantRs  extends GenericRs{
    private MerchantDto merchant;
    
      public FindMerchantRs(String statusCode, MerchantDto merchantDto) {
        super(statusCode);
        this.merchant = merchantDto;
    }

    public FindMerchantRs(String statusCode, String message) {
        super(statusCode, message);
    }

    public MerchantDto getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDto merchant) {
        this.merchant = merchant;
    }@Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
