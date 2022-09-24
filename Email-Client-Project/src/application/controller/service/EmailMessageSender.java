package application.controller.service;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import application.controller.EmailSendingResult;
import application.model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailMessageSender extends Service<EmailSendingResult> {

	private EmailAccount emailAccount;
	private String subject;
	private String recipient;
	private String content;

	public EmailMessageSender(EmailAccount emailAccount, String subject, String recipient, String content) {
		super();
		this.emailAccount = emailAccount;
		this.subject = subject;
		this.recipient = recipient;
		this.content = content;
	}

	@Override
	protected Task<EmailSendingResult> createTask() {
		// TODO Auto-generated method stub
		return new Task<EmailSendingResult>() {

			@Override
			protected EmailSendingResult call() {
				try {

					// create message
					MimeMessage message = new MimeMessage(emailAccount.getSession());
					message.setFrom(emailAccount.getEmailAddress());
					message.addRecipients(Message.RecipientType.TO, recipient);
					message.setSubject(subject);
					// set content
					Multipart multipart = new MimeMultipart();
					BodyPart bodypart = new MimeBodyPart();
					bodypart.setContent(content, "text/html");
					multipart.addBodyPart(bodypart);
					message.setContent(multipart);

					//sending message
					Transport transport = emailAccount.getSession().getTransport();
					transport.connect(emailAccount.getProperties().getProperty("outgoingHost"),
							emailAccount.getEmailAddress(),emailAccount.getEmailPassword());
					transport.sendMessage(message, message.getAllRecipients());
					transport.close();
					return EmailSendingResult.SUCCESS;

				} catch (MessagingException e) {
					e.printStackTrace();
					return EmailSendingResult.ERROR_BY_UNEXPECTED;
				} catch (Exception e) {
					return EmailSendingResult.ERROR_BY_PROVIDER;
				}
			}

		};
	}

}
