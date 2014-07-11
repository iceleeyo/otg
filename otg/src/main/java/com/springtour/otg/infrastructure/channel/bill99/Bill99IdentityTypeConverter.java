/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.bill99;

import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.shared.IdentityType;

/**
 *
 * @author Future
 */
public class Bill99IdentityTypeConverter {

    public static String convert(IdentityType type) throws CannotMapIdentityTypeException {
        //TODO 证件类型不全
        if (IdentityType.IDENTITY.equals(type)) {
            return Bill99Constants.ID_TYPE_IDENTITY;
        } else if (IdentityType.PASSPORT.equals(type)) {
            return Bill99Constants.ID_TYPE_PASSPORT;
        } else if (IdentityType.OTHERS.equals(type)) {
            return Bill99Constants.ID_TYPE_OTHERS;
        } else {
            throw new CannotMapIdentityTypeException(Bill99IdentityTypeConverter.class, type);
        }
    }
}
