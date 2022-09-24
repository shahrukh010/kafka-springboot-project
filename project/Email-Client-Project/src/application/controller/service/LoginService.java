package application.controller.service;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import application.EmailManager;
import application.model.EmailAccount;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class LoginService extends Service<EmailLoginResult>{
	
	private EmailAccount emailAccount;
	private EmailManager emailManager;
	
	public LoginService(EmailAccount emailAccount,EmailManager emailManager) {
		this.emailAccount = emailAccount;
		this.emailManager = emailManager;
	}
	
	
	private EmailLoginResult login() {
		Authenticator authenticator = new Authenticator() { protected PasswordAuthentication getPasswordAuthentication()
		{ return new PasswordAuthentication(emailAccount.getEmailAddress(),emailAccount.getEmailPassword());
		} }; 
		try {
		Session session = Session.getInstance(emailAccount.getProperties());
			emailAccount.setSession(session);
			Store store = session.getStore();
			store.connect(emailAccount.getProperties().getProperty("incomingHost"),
					emailAccount.getEmailAddress(),emailAccount.getEmailPassword());
			        emailAccount.setStore(store);
			        emailManager.addAccount(emailAccount);
	} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return EmailLoginResult.FAILED_BY_UNEXPECTED;
		}
		catch(MessagingException e) {
			e.printStackTrace();
			return EmailLoginResult.FAILED_BY_CREDENTIALS;
		}
		catch(Exception e) {
			e.printStackTrace();
			return EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR;
		}
	
		return EmailLoginResult.SUCCESS;
	}


	@Override
	protected Task<EmailLoginResult> createTask() {
		return new Task<EmailLoginResult>() {

			@Override
			protected EmailLoginResult call() throws Exception {
				// TODO Auto-generated method stub
				return login();
			}
			
		};
	}

}
