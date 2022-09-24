
/**
 * BaseController class extends all controller
 */
package application.controller;

import application.EmailManager;
import application.model.EmailAccount;
import application.view.ViewFactory;

public abstract class BaseController {

	protected EmailManager emailManager;
	protected ViewFactory viewFactory;
	private String fxml;
	public BaseController(EmailManager emailManager, ViewFactory viewFactory, String fxml) {
		super();
		this.emailManager = emailManager;
		this.viewFactory = viewFactory;
		this.fxml = fxml;
	}
	
	public String getFxml() {
		
		return fxml;
	}
	
	
}
