/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.rs;

import com.springtour.otg.interfaces.admin.facade.dto.NotificationDto;
import java.util.List;

/**
 *
 * @author 001595
 */
public class ListNotificationsRs extends GenericRs {

    private List<NotificationDto> notifications;
    private long total;

    public ListNotificationsRs(String statusCode, List<NotificationDto> notifications, long total) {
        super(statusCode);
        this.notifications = notifications;
        this.total = total;
    }

    public ListNotificationsRs(String statusCode, String message) {
        super(statusCode, message);
    }

    public long getTotal() {
        return total;
    }

    public List<NotificationDto> getNotifications() {
        return notifications;
    }@Override
    public String toString() {
        return super.toString();//查询rs不记录查询结果，否则结果集太大
    }
}
