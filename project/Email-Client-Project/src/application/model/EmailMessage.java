package application.model;

import java.util.Date;

import javax.mail.Message;

import application.controller.SizeInteger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmailMessage {

	private SimpleStringProperty sender;
	private SimpleStringProperty subject;
	private SimpleStringProperty recipient;
	private SizeInteger size;
	private SimpleObjectProperty<Date> date;
	private boolean isRead;
	private Message message;
	
	public EmailMessage(String sender,String subject,String recipient,int size,Date date,boolean isRead,Message message) {
		
		this.sender = new SimpleStringProperty(sender);
		this.subject= new SimpleStringProperty(subject);
		this.recipient = new SimpleStringProperty(recipient);
		this.size = new SizeInteger(size);
		this.date = new SimpleObjectProperty<Date>(date);
		this.isRead = isRead;
		this.message = message;
	}

	public String getSender() {
		return this.sender.get();
	}

	public String getSubject() {
		return subject.get();
	}

	public String getRecipient() {
		return recipient.get();
	}

	public SizeInteger getSize() {
		return size;
	}

	public Date getDate() {
		return date.get();
	}

	public boolean isRead() {
		return isRead;
	}

	public Message getMessage() {
		return message;
	}
}
