/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.exception;

import com.springtour.otg.domain.shared.IdentityType;

/**
 *
 * @author 006874
 */
public class CannotMapIdentityTypeException extends AbstractCheckedApplicationExceptionWithStatusCode {

    public CannotMapIdentityTypeException(Class translator, IdentityType type) {
        super("Translator=" + translator + ",type=" + type.getCode());
    }

    @Override
    public String getStatusCode() {
        return StatusCode.UNAVAILABLE_CARD_HOLDER_ID_TYPE;
    }
}
