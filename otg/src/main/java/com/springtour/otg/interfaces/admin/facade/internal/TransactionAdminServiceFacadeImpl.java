/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.interfaces.admin.facade.internal;

import com.springtour.otg.application.NotificationService;
import com.springtour.otg.application.ResponseProcedure;
import com.springtour.otg.application.exception.AbstractCheckedApplicationExceptionWithStatusCode;
import com.springtour.otg.application.exception.StatusCode;
import com.springtour.otg.domain.model.notification.Charge;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.notification.SynchronizingMethod;
import com.springtour.otg.domain.model.transaction.OrderIdentity;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.logging.LoggerFactory;
import com.springtour.otg.infrastructure.persistence.TransactionCriteria;
import com.springtour.otg.interfaces.admin.facade.TransactionAdminServiceFacade;
import com.springtour.otg.interfaces.admin.facade.dto.TransactionDto;
import com.springtour.otg.interfaces.admin.facade.internal.assembler.NotificationDtoAssembler;
import com.springtour.otg.interfaces.admin.facade.internal.assembler.TransactionDtoAssembler;
import com.springtour.otg.interfaces.admin.facade.rq.ChangeOrderInfoRq;
import com.springtour.otg.interfaces.admin.facade.rq.HandMakeRq;
import com.springtour.otg.interfaces.admin.facade.rq.ListTransactionsRq;
import com.springtour.otg.interfaces.admin.facade.rq.RetryHandlingRq;
import com.springtour.otg.interfaces.admin.facade.rs.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 
 * @author 001595
 */
public class TransactionAdminServiceFacadeImpl implements
		TransactionAdminServiceFacade {

	private ResponseProcedure responseProcedure;
	private NotificationService notificationService;
	private TransactionRepository transactionRepository;
	private NotificationRepository notificationRepository;
	private Logger logger = LoggerFactory.getLogger();

    @Override
    public HandMakeRs handMake(HandMakeRq rq) {
        logger.info(rq);
        try {
        	Notification notification = notificationService.retrieve(new TransactionNo(rq.getTransactionNo()), rq.getExtTxnNo(), Charge.of(rq.getCharged()),SynchronizingMethod.MANUAL);
            responseProcedure.handle(notification.getSequence());
            HandMakeRs rs = new HandMakeRs(StatusCode.SUCCESS);
            logger.info(rs);
            return rs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new HandMakeRs(StatusCode.UNKNOWN_ERROR, e.getMessage());
        }
    }

	@Override
	public RetryHandlingRs retryHandling(RetryHandlingRq rq) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public RetryPaymentMakingRs retryPaymentMaking(String transactionNo) {
		logger.info(transactionNo);
		try {
			responseProcedure.retryPaymentMaking(new TransactionNo(
					transactionNo));
			RetryPaymentMakingRs rs = new RetryPaymentMakingRs(
					StatusCode.SUCCESS);
			logger.info(rs);
			return rs;
		} catch (AbstractCheckedApplicationExceptionWithStatusCode e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
			return new RetryPaymentMakingRs(e.getStatusCode(), e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new RetryPaymentMakingRs(StatusCode.UNKNOWN_ERROR,
					e.getMessage());
		}
	}

	@Override
	public ListTransactionsRs listTransactions(ListTransactionsRq rq) {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date whenRequestedGt = null;
		Date whenRequestedLt = null;
		try {
			if (rq.getWhenRequestedGt() != null
					&& !"".equals(rq.getWhenRequestedGt())) {
				whenRequestedGt = sdf.parse(rq.getWhenRequestedGt());
			}
			if (rq.getWhenRequestedLt() != null
					&& !"".equals(rq.getWhenRequestedLt())) {
				whenRequestedLt = sdf.parse(rq.getWhenRequestedLt());
			}

			TransactionCriteria criteria = new TransactionCriteria.Builder()
					.transactionNoEq(rq.getTransactionNoEq())
					.merchantCodeEq(rq.getMerchantCodeEq())
					.stateEq(rq.getStateEq())
					.channelIdEq(rq.getChannelIdEq())
					.whenRequestedGt(whenRequestedGt)
					.whenRequestedLt(whenRequestedLt)
					.transactionTypeEq(rq.getTransactionTypeEq())
					.orderIdEq(rq.getApplicationEq(), rq.getOrderIdEq(),
							rq.getOrderNoEq()).partnerEq(rq.getPartnerEq()).firstResult(rq.getFirstResult())
					.maxResults(rq.getMaxResults()).build();
			List<TransactionDto> dtos = new ArrayList<TransactionDto>();
			List<Transaction> list = transactionRepository.find(criteria);
			dtos = TransactionDtoAssembler.toDtoList(list);
			Long count = transactionRepository.count(criteria);
			ListTransactionsRs rs = new ListTransactionsRs(StatusCode.SUCCESS,
					dtos, count);
			// logger.info(rs); 查询记录结果集不记录日志，否则数据太大
			return rs;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ListTransactionsRs(StatusCode.UNKNOWN_ERROR,
					e.getMessage());
		}
	}

	@Override
	public ListNotificationsRs listNotifications(String transactionNo) {
		try {
			List<Notification> notifications = notificationRepository
					.find(new TransactionNo(transactionNo));
			ListNotificationsRs rs = new ListNotificationsRs(
					StatusCode.SUCCESS,
					NotificationDtoAssembler.toDtoList(notifications),
					notifications.size());
			// logger.info(rs); 查询记录结果集不记录日志，否则数据太大
			return rs;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ListNotificationsRs(StatusCode.UNKNOWN_ERROR,
					e.getMessage());
		}
	}

	@Override
	public DetailTransactionRs detailTransaction(String transactionNo) {
		try {
			Transaction txn = transactionRepository.find(new TransactionNo(
					transactionNo));
			TransactionDto tansactionDto = TransactionDtoAssembler.toDto(txn);
			DetailTransactionRs rs = new DetailTransactionRs(
					StatusCode.SUCCESS, tansactionDto);
			// logger.info(rs); 查询记录结果集不记录日志，否则数据太大
			return rs;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new DetailTransactionRs(StatusCode.UNKNOWN_ERROR,
					e.getMessage());
		}
	}
	
	@Override
    public ChangeOrderInfoRs changeOrderInfo(ChangeOrderInfoRq rq) {
	    logger.info(rq);
	    try {
            Transaction txn = transactionRepository.find(new TransactionNo(
                    rq.getTransactionNo()));
            OrderIdentity orderIdentity = new OrderIdentity(rq.getApplication(),rq.getOrderId(),rq.getOrderNo());
            txn.changeOrderInfo(orderIdentity, rq.getChargeFor());
            transactionRepository.store(txn);
            ChangeOrderInfoRs rs = new ChangeOrderInfoRs(
                    StatusCode.SUCCESS);
            return rs;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ChangeOrderInfoRs(StatusCode.UNKNOWN_ERROR,
                    e.getMessage());
        }
	    
        
    }

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	public void setResponseProcedure(ResponseProcedure responseProcedure) {
		this.responseProcedure = responseProcedure;
	}

	public void setTransactionRepository(
			TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	public void setNotificationRepository(
			NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

}
