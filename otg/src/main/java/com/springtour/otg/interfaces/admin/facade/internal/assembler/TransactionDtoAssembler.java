/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal.assembler;

import com.springtour.otg.domain.model.channel.Gateways;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.transaction.CheckingState;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionType;
import com.springtour.otg.interfaces.admin.facade.dto.NotificationDto;
import com.springtour.otg.interfaces.admin.facade.dto.TransactionDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * @author 006874
 */
public class TransactionDtoAssembler {

	public static List<TransactionDto> toDtoList(List<Transaction> list) {
		if (list == null) {
			return Collections.EMPTY_LIST;
		}
		List<TransactionDto> dtos = new ArrayList<TransactionDto>();
		for (Transaction txn : list) {
			dtos.add(toDto(txn));
		}
		return dtos;
	}

	public static TransactionDto toDto(Transaction txn) {

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TransactionDto dto = new TransactionDto();
		dto.setAmount(txn.getAmount().getAmount());
		dto.setApplication(txn.getOrderId().getApplication());
		dto.setChannelId(txn.getChannel().getId());
		dto.setChannelName(txn.getChannel().getName());
		dto.setCurrency(txn.getAmount().getCurrencyCode());
		dto.setGateway(Gateways.getName(txn.getGateway()));
		dto.setMerchantCode(txn.getMerchantCode());
		dto.setMerchantName(txn.getMerchant().getName());
		dto.setOrderId(txn.getOrderId().getOrderId());
		dto.setOrderNo(txn.getOrderId().getOrderNo());
		dto.setPartnerId(txn.getPartner().getId());
		dto.setPartnerName(txn.getPartner().getName());
		dto.setStateName(Transaction.State.getNameFromCode(txn.getState()));
		dto.setTransactionNo(txn.getTransactionNo().getNumber());
		dto.setCheckingState(getNameFromCode(txn.getCheckingState()));
		dto.setTransactionTypeName(txn.transactionType().getName());
		dto.setReferenceTxnNo(txn.getReferenceTransactionNumber());
		if (txn.getUpdateTime() != null) {
			dto.setUpdateTime(sdf.format(txn.getUpdateTime()));
		} else {
			dto.setUpdateTime("");
		}
		if (txn.getWhenConcluded() != null) {
			dto.setWhenConcluded(sdf.format(txn.getWhenConcluded()));
		} else {
			dto.setWhenConcluded("");
		}
		if (txn.getHandlingActivity() != null) {
			if (txn.getHandlingActivity().getWhenHandled() != null) {
				dto.setWhenHandled(sdf.format(txn.getHandlingActivity()
						.getWhenHandled()));
			} else {
				dto.setWhenHandled("");
			}
			Notification notification = txn.getHandlingActivity()
					.getNotification();
			// 遗留数据
			if (notification != null) {
				dto.setNotificationDto(NotificationDtoAssembler
						.toDto(notification));
			}
		} else {
			dto.setNotificationDto(new NotificationDto());
			dto.setWhenHandled("");
		}
		if (txn.getWhenRequested() != null) {
			dto.setWhenRequested(sdf.format(txn.getWhenRequested()));
		} else {
			dto.setWhenRequested("");
		}

		return dto;
	}

	private static String getNameFromCode(String code) {
		for (CheckingState st : CheckingState.values()) {
			if (st.sameCodeAs(code)) {
				return st.name();
			}
		}
		return "";
	}
}
