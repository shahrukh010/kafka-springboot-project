
/**
 * EmailAccount class Access by LoginService 
 * for authentication 
 */
package application.model;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Store;

public class EmailAccount {

	private String emailAddress;
	private String emailPassword;

	private Properties properties;
	private Store store;
	private Session session;
	

	public EmailAccount(String emailAddress, String emailPassword) {
		super();
		this.emailAddress = emailAddress;
		this.emailPassword = emailPassword;
		this.properties = new Properties();

		properties.put("incomingHost", "imap.gmail.com");
		properties.put("mail.store.protocol", "imaps");

		properties.put("mail.transport.protocol", "smtps");
		properties.put("mail.smtps.host", "smtp.gmail.com");
		properties.put("mail.smtps.auth", "true");
		properties.put("outgoingHost", "smtp.gmail.com");
	}

	public Store getStore() {
		return store;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public Properties getProperties() {
		return properties;
	}

	public String toString() {

		return this.emailAddress;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public Session getSession() {

		return session;
	}

}
