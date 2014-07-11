/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal.assembler;

import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.shared.Money;
import com.springtour.otg.interfaces.admin.facade.dto.NotificationDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author 006874
 */
public class NotificationDtoAssembler {

    public static List<NotificationDto> toDtoList(List<Notification> notifications) {
        if (notifications == null) {
            return Collections.EMPTY_LIST;
        }
        List<NotificationDto> dtos = new ArrayList<NotificationDto>();
        for (Notification notification : notifications) {
            dtos.add(toDto(notification));
        }
        return dtos;
    }

    public static NotificationDto toDto(Notification notification) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        NotificationDto dto = new NotificationDto();
        if (notification == null) {
            return new NotificationDto();
        }
        Money money = notification.getAmount();
        if (money != null) {
            dto.setAmount(money.getAmount());
            dto.setCurrencyCode(money.getCurrencyCode());
        }
        dto.setCharged(Charge.getNameFromCode(notification.getCharged()));
        dto.setExtTxnNo(notification.getExtTxnNo());
        dto.setMessage(notification.getMessage());
        dto.setSequence(notification.getSequence());
        dto.setSignature(notification.getSignature());
        dto.setSynchronizingMethod(notification.getSynchronizingMethod());
        dto.setCardInfo(notification.getCardInfo());
        //如果执行会引起循环依赖， 导致死循环
        //dto.setTransactionNo(notification.getTransaction().getTransactionNo().getNumber());
        if (notification.getWhenReceived() != null) {
            dto.setWhenReceived(sdf.format(notification.getWhenReceived()));
        } else {
            dto.setWhenReceived("");
        }
        return dto;
    }
}
