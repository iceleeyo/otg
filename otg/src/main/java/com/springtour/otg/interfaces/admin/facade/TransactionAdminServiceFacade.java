/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade;

import com.springtour.otg.interfaces.admin.facade.rq.*;
import com.springtour.otg.interfaces.admin.facade.rs.*;

/**
 *
 * @author 001595
 */
public interface TransactionAdminServiceFacade {

    ListTransactionsRs listTransactions(ListTransactionsRq rq);
    
    ListNotificationsRs listNotifications(String transactionNo);

    HandMakeRs handMake(HandMakeRq rq);
    
    RetryHandlingRs retryHandling(RetryHandlingRq rq);
    
    RetryPaymentMakingRs retryPaymentMaking(String transactionNo);
    
    DetailTransactionRs detailTransaction(String transactionNo); 
    
    ChangeOrderInfoRs changeOrderInfo(ChangeOrderInfoRq rq);
}
