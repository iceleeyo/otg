/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.shared;

/**
 *
 * @author 001595
 */
public enum IdentityType {

    IDENTITY("identity"), PASSPORT("passport"), OTHERS("others");
    private String code;

    private IdentityType(String code) {
        this.code = code;
    }

    //@Overrride
    public static IdentityType of(String code) {
        IdentityType result = null;
        for (IdentityType type : IdentityType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return result;
    }

    public boolean sameCodeAs(String code) {
        return (code != null) && (this.code.equals(code));
    }

    public String getCode() {
        return code;
    }
}
