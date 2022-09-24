package application.controller.service;

import java.io.IOException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import application.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

public class MessageRendereService extends Service<Void> {

	private EmailMessage emailMessage;
	private WebEngine webEngine;
	private StringBuffer stringBuffer;

	public MessageRendereService(WebEngine webEngine) {
		this.webEngine = webEngine;
		this.stringBuffer = new StringBuffer();
		this.setOnSucceeded(event->{
		displayMessage();	
		});
	}
	
	public void setEmailMessage(EmailMessage emailMessage) {
		this.emailMessage = emailMessage;
	}
	public void displayMessage() {
		
		webEngine.loadContent(stringBuffer.toString());
	}
	
	@Override
	protected Task<Void> createTask() {

		return new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				// TODO Auto-generated method stub
				loadMessage();
				return null;
			}
		};
	}
	
	

	private void loadMessage() throws MessagingException {

		stringBuffer.setLength(0);
		Message message = emailMessage.getMessage();
		String contentType = message.getContentType();
		if(isSimpleType(contentType)) {
			//use here try-cath because we don't want to deligate exception to other or increasing just dependence or making just high coupling
			try {
				stringBuffer.append(message.getContent().toString());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			} catch (MessagingException e) {
				e.printStackTrace();
				return;
			}
		}
		else if(isMultipPart(contentType)) {
			
			//use here try-cath because we don't want to deligate exception to other or increasing just dependence or making just high coupling
			try {
				Multipart multipart = (Multipart)message.getContent();
				for(int i=multipart.getCount()-1;i>=0;i--) {

					BodyPart bodypart = multipart.getBodyPart(i);
					String bodypartContentType = bodypart.getContentType();
					if(isSimpleType(bodypartContentType)) {
						stringBuffer.append(bodypart.getContent().toString());
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
				return;
			} catch (MessagingException e) {
				e.printStackTrace();
				return;
			}
		}

	}

	private boolean isSimpleType(String contentType) {

		if (contentType.contains("TEXT/HTML") || (contentType.contains("mixed")) || (contentType.contains("text"))) {
			return true;
		} else
			return false;

	}
	
	private boolean isMultipPart(String contentType) {
		
		if(contentType.contains("multipart")) {
			
			return true;
		}
		else {
			return false;
		}
	}

}
