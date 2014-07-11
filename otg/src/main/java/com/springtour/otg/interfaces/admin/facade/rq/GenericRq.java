/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rq;

import java.io.Serializable;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

/**
 *
 * @author 001595
 */
public abstract class GenericRq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this,
                StandardToStringStyle.SHORT_PREFIX_STYLE);
    }
}
