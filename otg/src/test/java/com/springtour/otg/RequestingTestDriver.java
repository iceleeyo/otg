/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg;

import com.springtour.otg.application.ResponseProcedure;
import com.springtour.otg.dbop.DataPrepareOperation;
import com.springtour.otg.domain.model.notification.Notification;
import com.springtour.otg.domain.model.notification.NotificationRepository;
import com.springtour.otg.domain.model.transaction.Transaction;
import com.springtour.otg.domain.model.transaction.TransactionNo;
import com.springtour.otg.domain.model.transaction.TransactionRepository;
import com.springtour.otg.infrastructure.channel.AbstractNotificationFactory;
import com.springtour.otg.infrastructure.channel.AlwaysPassNotificationValidator;
import com.springtour.otg.infrastructure.channel.NotificationHttpServletRequestAssembler;
import com.springtour.otg.infrastructure.channel.NotificationValidator;
import com.springtour.otg.infrastructure.payment.PaymentDispatcher;
import com.springtour.otg.interfaces.transacting.facade.RequestHandlerFacade;
import com.springtour.otg.interfaces.transacting.facade.TransactingServiceFacade;
import com.springtour.otg.interfaces.transacting.facade.rq.*;
import com.springtour.otg.interfaces.transacting.facade.rs.*;
import com.springtour.otg.interfaces.transacting.web.NotificationReceiver;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.DocumentException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 *
 * @author 001595
 */
public class RequestingTestDriver {

    private DataPrepareOperation givesAll;
    private RequestHandlerFacade transactingServiceFacade;
    private ApplicationContext applicationContext;
    private String partnerId = "2";
    private String channelId = "12";

    public RequestingTestDriver(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.transactingServiceFacade =
                (RequestHandlerFacade) applicationContext.getBean("otg.RequestHandlerFacadeImpl");
        this.givesAll = (DataPrepareOperation) applicationContext.getBean("otg.GivesAllForGenericRequestingHappyPath");
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void givesAPartnerWellConfigured() throws Exception {
        givesAll.appendData();
    }

    public AvailableGatewaysRs availableGateways() {
        AvailableGatewaysRq rq = new AvailableGatewaysRq();
        rq.setPartnerId(partnerId);
        return transactingServiceFacade.availableGateways(rq);
    }
    
    public AvailableCardHolderIdTypesRs availableCardHolderIdTypes() {
        AvailableCardHolderIdTypesRq rq = new AvailableCardHolderIdTypesRq();
        rq.setChannelId(channelId);
        return transactingServiceFacade.availableCardHolderIdTypes(rq);
    }
}
