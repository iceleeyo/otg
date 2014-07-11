/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.application.exception;

/**
 *
 * @author 001595
 */
public class CannotReconsitituteNotificationException extends AbstractCheckedApplicationException {

    public CannotReconsitituteNotificationException(String notificationSeq) {
        super("Cannot reconsititute notificaiton by sequence:" + notificationSeq);
    }
}
