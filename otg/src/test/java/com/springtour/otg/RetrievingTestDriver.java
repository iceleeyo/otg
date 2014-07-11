/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg;

import com.springtour.otg.application.NotificationService;
import com.springtour.otg.application.ResponseProcedure;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.SynchronizingMethod;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.payment.PaymentDispatcher;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author 001595
 */
public class RetrievingTestDriver {

    private DataPrepareOperation givesATransactionWasResponsedSuccess;
    private DataPrepareOperation givesATransactionWasResponsedSuccessAndAlwaysFailsToMakePayment;
    private DataPrepareOperation givesATransactionWasResponsedSuccessAndAlwaysErrorToMakePayment;
    private DataPrepareOperation givesATransactionWasRequested;
    private DataPrepareOperation givesATransactionWasChargedFailure;
    private DataPrepareOperation givesATransactionWasConcluded;
    private ResponseProcedure responseProcedure;
    private NotificationService notificationService;
    private TransactionNo transactionNo = new TransactionNo("2011042709016612");
    private String extTxnNo = "123456";
    private TransactionRepository transactionRepository;
    private PaymentDispatcher paymentDispatcher;

    public RetrievingTestDriver(ConfigurableApplicationContext applicationContext) {
        givesATransactionWasResponsedSuccess =
                (DataPrepareOperation) applicationContext.getBean("otg.GivesATransactionWasResponsedSuccess");

        givesATransactionWasRequested =
                (DataPrepareOperation) applicationContext.getBean("otg.GivesATransactionWasRequested");

        givesATransactionWasResponsedSuccessAndAlwaysFailsToMakePayment =
                (DataPrepareOperation) applicationContext.getBean("otg.GivesATransactionWasResponsedSuccessAndAlwaysFailsToMakePayment");

        givesATransactionWasResponsedSuccessAndAlwaysErrorToMakePayment =
                (DataPrepareOperation) applicationContext.getBean("otg.GivesATransactionWasResponsedSuccessAndAlwaysErrorToMakePayment");

        givesATransactionWasChargedFailure = (DataPrepareOperation) applicationContext.getBean("otg.GivesATransactionWasChargedFailure");

        givesATransactionWasConcluded = (DataPrepareOperation) applicationContext.getBean("otg.GivesATransactionWasConcluded");

        this.responseProcedure = (ResponseProcedure) applicationContext.getBean("otg.ResponseProcedure");
        this.transactionRepository = (TransactionRepository) applicationContext.getBean("otg.IBatisTransactionRepositoryImpl");
        this.notificationService = (NotificationService) applicationContext.getBean("otg.NotificationServiceImpl");

        //将MakePaymentService替换成替身，模拟成功、失败、异常三种情况
        paymentDispatcher = (PaymentDispatcher) applicationContext.getBean("otg.MakePaymentServiceFactoryStub");
        responseProcedure.setMakePaymentService(paymentDispatcher);
    }

    public void givesATransactionWasResponsedSuccess() throws Exception {
        givesATransactionWasResponsedSuccess.appendData();
    }

    public void givesATransactionWasResponsedSuccessAndAlwaysFailsToMakePayment() throws Exception {
        givesATransactionWasResponsedSuccessAndAlwaysFailsToMakePayment.appendData();
    }

    public void givesATransactionWasResponsedSuccessAndAlwaysErrorToMakePayment() throws Exception {
        givesATransactionWasResponsedSuccessAndAlwaysErrorToMakePayment.appendData();
    }

    public void givesATransactionWasRequested() throws Exception {
        givesATransactionWasRequested.appendData();
    }

    public void givesATransactionWasChargedFailure() throws Exception {
        givesATransactionWasChargedFailure.appendData();
    }

    public void givesATransactionWasConcluded() throws Exception {
        givesATransactionWasConcluded.appendData();
    }

    public void retryPaymentMaking() throws Exception {
        responseProcedure.retryPaymentMaking(transactionNo);
    }

    public void handMakeAChargedNotification() throws Exception {
        Notification notification = notificationService.retrieve(transactionNo, extTxnNo, Charge.SUCCESS,SynchronizingMethod.MANUAL);
        responseProcedure.handle(notification.getSequence());
    }

    public Transaction getTransaction() {
        return transactionRepository.find(transactionNo);
    }
}
