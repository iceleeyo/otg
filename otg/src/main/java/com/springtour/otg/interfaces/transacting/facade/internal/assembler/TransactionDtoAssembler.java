package com.springtour.otg.interfaces.transacting.facade.internal.assembler;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.interfaces.transacting.facade.dto.TransactionDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionDtoAssembler {

    public TransactionDto toDto(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionNo(transaction.getTransactionNo().getNumber());
        dto.setAmount(transaction.getAmount().getAmount());
        dto.setCurrency(transaction.getAmount().getCurrencyCode());
        dto.setState(transaction.getState());
        dto.setWhenRequested(transaction.getWhenRequested());
        dto.setChannelId(transaction.getChannel().getId());
        dto.setChannelName(transaction.getChannel().getName());
        dto.setGateway(transaction.getGateway());
        dto.setMerchantId(String.valueOf(transaction.getMerchant().getId()));
        dto.setMerchantName(transaction.getMerchant().getName());
        dto.setPartnerId(transaction.getPartner().getId());
        dto.setPartnerName(transaction.getPartner().getName());
        return dto;
    }

    public List<TransactionDto> toDto(List<Transaction> transactions) {
        if (transactions == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<TransactionDto> dtos = new ArrayList<TransactionDto>();
            for (Transaction transaction : transactions) {
                dtos.add(toDto(transaction));
            }
            return dtos;
        }
    }
}
