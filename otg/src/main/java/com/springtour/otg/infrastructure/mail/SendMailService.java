package com.springtour.otg.infrastructure.mail;

import lombok.Getter;
import lombok.Setter;

import com.spring.sendmail.model.MailConfig;
import com.spring.sendmail.model.MailInfo;
import com.spring.sendmail.service.SendMailWebPowerService;
import com.springtour.otg.domain.model.application.Application;
import com.springtour.otg.infrastructure.logging.LoggerFactory;

public class SendMailService implements MailManager {

	@Setter 
	@Getter
	private SendMailWebPowerService sendMailService;
	@Setter 
	@Getter
	private MailConfig mailConfig;
	@Setter 
	@Getter
	private MailConfig airMailConfig;
	@Setter 
    @Getter
    private MailConfig ticketMailConfig;
	@Setter 
    @Getter
    private MailConfig cruiseMailConfig;
	@Setter 
    @Getter
    private MailConfig visaMailConfig;
	@Setter 
    @Getter
    private MailConfig groupTourMailConfig;
	@Setter 
    @Getter
    private MailConfig freeTourMailConfig;
	
	@Override
	public void send(String title, String content) {
		this.send(title, content, "");
	}

	@Override
	public void send(String title, String content, String application) {
		try {
			//9代表机票订单与其他订单发送的邮件地址不同
			if(Application.AIR.getId().equals(application)){
				sendMailService.sendEmail(airMailConfig, 
						new MailInfo(title, content));
			} else if(Application.TICKET.getId().equals(application)) {
			    sendMailService.sendEmail(ticketMailConfig, 
                        new MailInfo(title, content));
			} else if(Application.CRUISE.getId().equals(application)) {
                sendMailService.sendEmail(cruiseMailConfig, 
                        new MailInfo(title, content));
            } else if(Application.VISA.getId().equals(application)) {
                sendMailService.sendEmail(visaMailConfig, 
                        new MailInfo(title, content));
            } else if(Application.GROUPTOUR.getId().equals(application)) {
                sendMailService.sendEmail(groupTourMailConfig, 
                        new MailInfo(title, content));
            } else if(Application.FREETOUR.getId().equals(application)) {
                sendMailService.sendEmail(freeTourMailConfig, 
                        new MailInfo(title, content));
            } else {
				sendMailService.sendEmail(mailConfig, 
						new MailInfo(title, content));
			}
		} catch (Exception e) {
			LoggerFactory.getLogger().error(e.getMessage(), e);
		}
	}

}
