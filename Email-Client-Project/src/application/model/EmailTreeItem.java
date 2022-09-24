package application.model;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeMessage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem; 
public class EmailTreeItem<String> extends TreeItem<String>{

	private String name;
	private int incrementUnreadMessage;
	private ObservableList<EmailMessage> messages;
	
	public EmailTreeItem(String name) {
		super(name);
		this.name = name;
		this.messages = FXCollections.observableArrayList();
	}
	
	public EmailMessage addEmail(Message message) throws MessagingException {
		
		boolean isReadMessage = message.getFlags().contains(Flag.FLAGGED.SEEN);

		EmailMessage emailMessage = new EmailMessage(
				message.getFrom()[0].toString(),
				message.getSubject().toString(),
				message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
				message.getSize(),message.getSentDate(),
				isReadMessage,message);
		messages.add(emailMessage);
		
		if(!isReadMessage) {
			incrementMessage();
		}
		return emailMessage;
	}
	public ObservableList<EmailMessage> getMessage() {
		return messages;
	}

	private void incrementMessage() {
		
		incrementUnreadMessage++;
		updateName();
	}

	private void updateName() {
		
		if(incrementUnreadMessage>0)
		this.setValue((String) (name+"("+incrementUnreadMessage+")"));
		else
			this.setValue(name);
	}

}
