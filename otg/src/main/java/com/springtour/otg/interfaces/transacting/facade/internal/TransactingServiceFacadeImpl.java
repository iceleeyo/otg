package com.springtour.otg.interfaces.transacting.facade.internal;

import com.springtour.otg.application.exception.AbstractCheckedApplicationExceptionWithStatusCode;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import com.springtour.otg.application.TransactingService;
import com.springtour.otg.domain.model.merchant.Merchant;
import com.springtour.otg.domain.service.RequestWithCardInfoService;
import com.springtour.otg.domain.service.RequestWithoutCardInfoService;
import com.springtour.otg.interfaces.transacting.facade.TransactingServiceFacade;
import com.springtour.otg.interfaces.transacting.facade.arg.FindMerchantArgument;
import com.springtour.otg.interfaces.transacting.facade.arg.NextTransactionNoArgument;
import com.springtour.otg.interfaces.transacting.facade.dto.MerchantDTO;
import com.springtour.otg.interfaces.transacting.facade.internal.assembler.MerchantDTOAssembler;
import com.springtour.otg.interfaces.transacting.facade.result.FindMerchantResult;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.domain.model.channel.ChannelRepository;
import com.springtour.otg.domain.model.partner.PartnerRepository;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

@WebService(endpointInterface = "com.springtour.otg.interfaces.transacting.facade.TransactingServiceFacade")
@BindingType(value = SOAPBinding.SOAP11HTTP_BINDING)
public class TransactingServiceFacadeImpl implements TransactingServiceFacade {

    @Override
    public FindMerchantResult findMerchant(FindMerchantArgument arg) {
        LoggerFactory.getLogger().info(arg);
        try {
            Merchant merchant = transactingService.findMerchant(arg.getChannelId(), arg.getOrgId());
            // 若找到商户
            MerchantDTOAssembler assembler = new MerchantDTOAssembler();
            // 装配DTO
            MerchantDTO merchantDTO = assembler.toDTO(merchant);
            // 返回结果
            FindMerchantResult rs = new FindMerchantResult(StatusCode.SUCCESS, merchantDTO);
            LoggerFactory.getLogger().info(rs);
            return rs;
        } catch (AbstractCheckedApplicationExceptionWithStatusCode applicationException) {
            LoggerFactory.getLogger().error(applicationException.getMessage(), applicationException);
            return new FindMerchantResult(applicationException.getStatusCode());
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
            return new FindMerchantResult(StatusCode.UNKNOWN_ERROR);
        }

    }
    private TransactingService transactingService;
    private PartnerRepository partnerRepository;
    private ChannelRepository channelRepository;
    private TransactionRepository transactionRepository;
    private RequestWithCardInfoService requestAdapter;
    private RequestWithoutCardInfoService requestWithoutCardInfoAdapter;

    public void setRequestAdapter(RequestWithCardInfoService requestAdapter) {
        this.requestAdapter = requestAdapter;
    }

    public void setTransactingService(TransactingService transactingService) {
        this.transactingService = transactingService;
    }

    public void setRequestWithoutCardInfoAdapter(RequestWithoutCardInfoService requestWithoutCardInfoAdapter) {
        this.requestWithoutCardInfoAdapter = requestWithoutCardInfoAdapter;
    }

    public void setPartnerRepository(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public void setChannelRepository(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
}
