package com.springtour.otg.infrastructure.payment;

import com.springtour.otg.application.exception.CannotMakePaymentException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.service.MakePaymentService;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.infrastructure.mail.MailManager;
import com.springtour.util.DateUtils;
import java.text.SimpleDateFormat;

import lombok.Setter;

public class ExternalMakePaymentServiceImpl implements MakePaymentService {
	@Setter
	private MailManager mailManager;
    private String application;
    private static final String SUCCESS = "0";

    @Override
    public void makePayment(Transaction transaction) throws CannotMakePaymentException {
        final SimpleDateFormat sdf = DateUtils.simpleDateFormatDetail();
        try {
            String statusCode = makeAutoPaymentService.makePayment(transaction
					.getOrderId().getOrderId(), String.valueOf(transaction
					.getAmount().getAmount()), transaction.getAmount()
					.getCurrencyCode(), transaction.getTransactionNo()
					.getNumber(), String.valueOf(transaction.getChannel()
					.getId()), sdf.format(transaction.getHandlingActivity()
					.getWhenHandled()), transaction.getMerchantCode(), transaction.getChargeFor());
            if (isSuccess(statusCode)) {
                LoggerFactory.getLogger().info("TransactionNo ="
        				+ transaction.getTransactionNo().getNumber()+",make payment status code=" + statusCode);
            } else {
                throw new CannotMakePaymentException(transaction, statusCode);
            }
        } catch (Exception e) {
        	mailManager.send("客户付款成功，旅游系统订单处理失败！所属应用为："+transaction.getOrderId().getApplication(), "旅游系统交易流水号为：【"
    				+ transaction.getTransactionNo().getNumber()
    				+ "】，请人工处理该笔交易！", transaction.getOrderId().getApplication());
            throw new CannotMakePaymentException(transaction, e);
        }
    }

    @Override
    public boolean support(String application) {
        return this.application.equals(application);
    }

    /**
     * 判断业务系统是否支付成功
     * 
     * @param statusCode
     * @return
     */
    private boolean isSuccess(String statusCode) {
        if (SUCCESS.equals(statusCode)) {
            return true;
        } else {
            return false;
        }
    }
    private MakeAutoPaymentService makeAutoPaymentService;

    public void setMakeAutoPaymentService(
            MakeAutoPaymentService makeAutoPaymentService) {
        this.makeAutoPaymentService = makeAutoPaymentService;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }
}
