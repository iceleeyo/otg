/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

import com.springtour.otg.interfaces.admin.facade.dto.MerchantDto;
import java.util.List;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 *
 * @author 001595
 */
public class ListMerchantsRs extends GenericRs {

    private List<MerchantDto> merchants;
    private long total;

    public ListMerchantsRs(String statusCode, List<MerchantDto> merchants, long total) {
        super(statusCode);
        this.merchants = merchants;
        this.total = total;
    }

    public ListMerchantsRs(String statusCode, String message) {
        super(statusCode, message);
    }

    public long getTotal() {
        return total;
    }

    public List<MerchantDto> getMerchants() {
        return merchants;
    }@Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
