package com.stm.salesfast.backend.utils;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;

@Component
public class SalesFastEmailSendGridImpl implements SalesFastEmail {
	
	private Logger log = LoggerFactory.getLogger(SalesFastEmailSendGridImpl.class.getName());
	private static SendGrid sendGrid = new SendGrid("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");
	private String subject = "";
	private String textBody = "";
	private String htmlBody = "";
	private String fromEmail = "no-reply@xrates.io";
	private String fromName = "Exchange Rates";

/*	@Override
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
	}*/

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
		Email email = new Email();
		email.addTo("salesfast.stm@gmail.com");
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
	/*public static void main(String[] args) {
		SalesFastEmailSendGridImpl o = new SalesFastEmailSendGridImpl();
		o.setFromEmail("noreply@biopharma.com");
		o.setFromName("BioPharma Sales Team");
		o.addSubject("Test Email");
		o.addTextBody("This is a test email. Do not reply.");
		o.sendMail();
	}*/
}