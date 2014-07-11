package com.springtour.otg.infrastructure.payment;

import com.springtour.otg.application.exception.CannotMakePaymentException;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.application.exception.UnavailablePaymentApplicationException;
import com.springtour.otg.domain.service.MakePaymentService;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付服务工厂
 * 
 * @author Hippoom
 * 
 */
public class PaymentDispatcher implements MakePaymentService {

    private List<MakePaymentService> services = new ArrayList<MakePaymentService>();

    public void setServices(List<MakePaymentService> services) {
        this.services = services;
    }

    @Override
    public boolean support(String application) {
        boolean result = false;
        for (MakePaymentService service : services) {
            if (service.support(application)) {
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void makePayment(Transaction transaction) throws UnavailablePaymentApplicationException, CannotMakePaymentException {
        forward(transaction.getOrderId().getApplication()).makePayment(transaction);
    }

    /**
     * 根据应用返回其对应的支付服务实例
     * 
     * @param application
     * @return
     */
    private MakePaymentService forward(String application) throws UnavailablePaymentApplicationException {
        for (MakePaymentService service : services) {
            if (service.support(application)) {
                return service;
            }
        }
        throw new UnavailablePaymentApplicationException(application);
    }

    public List<MakePaymentService> getServices() {
        return services;
    }
}
