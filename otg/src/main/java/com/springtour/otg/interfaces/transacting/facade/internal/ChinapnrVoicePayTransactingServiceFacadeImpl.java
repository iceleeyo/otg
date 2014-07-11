package com.springtour.otg.interfaces.transacting.facade.internal;

import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.channel.chinapnr.ChinapnrConstants;
import com.springtour.otg.infrastructure.channel.chinapnr.ChinapnrRequestWithCardInfoAdapterImpl;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.interfaces.transacting.facade.ChinapnrVoicePayTransactingServiceFacade;
import com.springtour.otg.interfaces.transacting.facade.arg.ChinapnrVoicePayRequestArgument;
import com.springtour.otg.interfaces.transacting.facade.result.ChinapnrVoicePayRequestResult;

public class ChinapnrVoicePayTransactingServiceFacadeImpl implements
        ChinapnrVoicePayTransactingServiceFacade {

    @Override
    public ChinapnrVoicePayRequestResult request(
            ChinapnrVoicePayRequestArgument arg) {
        try {
            LoggerFactory.getLogger().info(arg);
            Transaction transaction = transactionRepository.find(new TransactionNo(arg.getTransactionNo()));
            String result = voicePayHttpClient.request(
                    transaction, arg.getCardInfo());
            LoggerFactory.getLogger().info("返回给VP的结果为: " + result);
            return new ChinapnrVoicePayRequestResult(result);
        } catch (Exception e) {
            LoggerFactory.getLogger().error(e.getMessage(), e);
            return new ChinapnrVoicePayRequestResult(
                    ChinapnrConstants.PENDING);
        }
    }
    private TransactionRepository transactionRepository;
    private ChinapnrRequestWithCardInfoAdapterImpl voicePayHttpClient;

    public void setVoicePayHttpClient(ChinapnrRequestWithCardInfoAdapterImpl voicePayHttpClient) {
        this.voicePayHttpClient = voicePayHttpClient;
    }

    public void setTransactionRepository(
            TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public String request(String transactionNo, String cardInfo) {
        ChinapnrVoicePayRequestArgument arg = new ChinapnrVoicePayRequestArgument();
        arg.setCardInfo(cardInfo);
        arg.setTransactionNo(transactionNo);
        return this.request(arg).getStatusCode();
    }
}
