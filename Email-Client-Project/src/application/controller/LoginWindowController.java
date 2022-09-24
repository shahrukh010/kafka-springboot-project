package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.EmailManager;
import application.controller.service.EmailLoginResult;
import application.controller.service.LoginService;
import application.model.EmailAccount;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController implements Initializable {

	public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxml) {
		super(emailManager, viewFactory, fxml);
	}

	@FXML
	private TextField emailAddressField;

	@FXML
	private Label errorLabel;

	@FXML
	private PasswordField passwordField;

	@FXML
	void loginbtnOnAction() {

		if (isValidField()) {
			EmailAccount account = new EmailAccount(emailAddressField.getText(), passwordField.getText());
			LoginService loginService = new LoginService(account, emailManager);
			loginService.start();
			loginService.setOnSucceeded(event -> {

				EmailLoginResult emailLoginResult = loginService.getValue();
				switch (emailLoginResult) {

				case SUCCESS:
					 
					Stage stage = (Stage)errorLabel.getScene().getWindow();
					if(!viewFactory.isMainWindowInitialize)
						viewFactory.mainWindow();
					viewFactory.closeStage(stage);
					return;

				case FAILED_BY_CREDENTIALS:
					System.out.println(EmailLoginResult.FAILED_BY_CREDENTIALS);
					return;

				case FAILED_BY_NETWORK:
					System.out.println(EmailLoginResult.FAILED_BY_NETWORK);
					return;

				case FAILED_BY_UNEXPECTED_ERROR:
					System.out.println(EmailLoginResult.FAILED_BY_UNEXPECTED_ERROR);
					return;
				}
			});
		}
	}

	private boolean isValidField() {

		if (emailAddressField.getText().isEmpty())

		{
			errorLabel.setText("please enter mail");
			return false;
		}
		if (passwordField.getText().isEmpty()) {
			errorLabel.setText("please enter password");
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		emailAddressField.setText("hector01k01@gmail.com");
		passwordField.setText("------");
	}
}
