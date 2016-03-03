package com.stm.salesfast.backend.utils;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;
import com.stm.salesfast.backend.dto.UserDto;

@Component
public class SalesFastEmailSendGridImpl implements SalesFastEmail {
	
	private Logger log = LoggerFactory.getLogger(SalesFastEmailSendGridImpl.class.getName());
	private static SendGrid sendGrid = new SendGrid("api-key-here");
	private String subject = "";
	private String textBody = "";
	private String htmlBody = "";
	private String fromEmail = "no-reply@xrates.io";
	private String fromName = "Exchange Rates";
	private List<UserDto> toList = null;

	@Override
	public void addTo(UserDto user) {
		if (toList == null) {
			toList = new ArrayList<UserDto>();
		}
		toList.add(user);
	}

	@Override
	public void addTo(List<UserDto> users) {
		if (toList == null) {
			toList = users;
		} else {
			toList.addAll(users);
		}
	}

	@Override
	public void addSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public void addTextBody(String textBody) {
		this.textBody = textBody;
	}

	@Override
	public void addHTMLBody(String htmlBody) {
		this.htmlBody = htmlBody;
	}
	
	@Override
	public void setFromEmail(String emailAddress) {
		fromEmail = emailAddress;
	}
	
	@Override
	public void setFromName(String name) {
		this.fromName = name;
	};

	@Override
	public void sendMail() {
		for (UserDto user:toList) {
			Email email = new Email();
			email.addTo(user.getEmail());
			email.setFrom(fromEmail);
			email.setFromName(fromName);
			if (subject != null && !subject.equals("")) {
				email.setSubject(subject);
			}
			if (textBody != null && !textBody.equals("")) {
				email.setText(textBody);
			}
			if (htmlBody != null && !htmlBody.equals("")) {
				email.setHtml(htmlBody);
			}
			try {
				sendGrid.send(email);
			} catch (SendGridException e) {
				log.error("SendGridException in sending email : " + e);
			}
		}
	}

}