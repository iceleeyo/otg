/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author 005073
 */
public class MoneyUtils {
    public static String money2String(BigDecimal amount, int digit, RoundingMode mode) {
        if ( amount == null ) {
            return BigDecimal.ZERO.setScale(digit, mode).toString();
        } else {
            return amount.setScale(digit, mode).toString();
        }
    }
}
