/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg;

import com.springtour.otg.domain.model.transaction.OrderIdentity;

/**
 *
 * @author 001595
 */
public interface FakeOrderIdentityGenerator {

    OrderIdentity newOrderIdentity();
}
