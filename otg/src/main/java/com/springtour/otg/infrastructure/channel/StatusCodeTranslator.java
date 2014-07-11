/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel;

import com.springtour.otg.application.exception.StatusCode;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 001595
 */
public class StatusCodeTranslator {

    private List<String> unavailableCreditList = Collections.EMPTY_LIST;
    private List<String> unsupportedServiceList = Collections.EMPTY_LIST;
    private List<String> invalidCardInfoList = Collections.EMPTY_LIST;
    private List<String> invalidCardHolderList = Collections.EMPTY_LIST;

    public String convertTo(String channelDialect) {
        if (unavailableCreditList.contains(channelDialect)) {
            return StatusCode.UNAVAILABLE_CREDIT;
        } else if (invalidCardHolderList.contains(channelDialect)) {
            return StatusCode.INVALID_CARD_HOLDER;
        } else if (invalidCardInfoList.contains(channelDialect)) {
            return StatusCode.INVALID_CARD_INFO;
        } else if (unsupportedServiceList.contains(channelDialect)) {
            return StatusCode.UNSUPPORTED_SERVICE;
        } else {
            return channelDialect;
        }
    }

    public void setInvalidCardHolderList(List<String> invalidCardHolderList) {
        this.invalidCardHolderList = invalidCardHolderList;
    }

    public void setInvalidCardInfoList(List<String> invalidCardInfoList) {
        this.invalidCardInfoList = invalidCardInfoList;
    }

    public void setUnavailableCreditList(List<String> unavailableCreditList) {
        this.unavailableCreditList = unavailableCreditList;
    }

    public void setUnsupportedServiceList(List<String> unsupportedServiceList) {
        this.unsupportedServiceList = unsupportedServiceList;
    }
}
