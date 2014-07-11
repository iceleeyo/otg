package com.springtour.otg.infrastructure.mail;

public interface MailManager {
	
	void send(String subject, String content);
	void send(String subject, String content, String application);
}
