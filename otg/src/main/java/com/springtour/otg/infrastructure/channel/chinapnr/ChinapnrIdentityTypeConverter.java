/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.channel.chinapnr;

import com.springtour.otg.application.exception.CannotMapIdentityTypeException;
import com.springtour.otg.domain.shared.IdentityType;

/**
 *
 * @author 006874
 */
public class ChinapnrIdentityTypeConverter {

    public static String convert(IdentityType type) throws CannotMapIdentityTypeException {
        if (IdentityType.IDENTITY.equals(type)) {
            return ChinapnrConstants.IDENTITY_TYPE_IDENTITY;
        } else if (IdentityType.OTHERS.equals(type)) {
            return ChinapnrConstants.IDENTITY_TYPE_OTHERS;
        } else if (IdentityType.PASSPORT.equals(type)) {
            return ChinapnrConstants.IDENTITY_TYPE_PASSPORT;
        } else {
            throw new CannotMapIdentityTypeException(ChinapnrIdentityTypeConverter.class, type);
        }
    }
}
