package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.EmailManager;
import application.controller.service.EmailMessageSender;
import application.model.EmailAccount;
import application.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

public class ComposeWindowController extends BaseController implements Initializable {

	public ComposeWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxml) {
		super(emailManager, viewFactory, fxml);
	}

	@FXML
	private HTMLEditor htmlEditor;

	@FXML
	private TextField recipientId;

	@FXML
	private TextField subjectId;

	@FXML
	private ChoiceBox<EmailAccount> emailAccounts;

	@FXML
	void sendBtnAction() {

		EmailMessageSender emailMessageSender = new EmailMessageSender(emailAccounts.getValue(),subjectId.getText(), recipientId.getText(),
				htmlEditor.getHtmlText());
		System.out.println(emailMessageSender);
		emailMessageSender.start();
		emailMessageSender.setOnSucceeded(event -> {

			EmailSendingResult emailSendingResult = emailMessageSender.getValue();
			System.out.println(emailSendingResult);
			switch (emailSendingResult) {

			case SUCCESS:
				Stage stage = (Stage)recipientId.getScene().getWindow();
				viewFactory.closeStage(stage);
				break;
			case ERROR_BY_PROVIDER:

				break;
			case ERROR_BY_UNEXPECTED:
				break;

			}
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		emailAccounts.setItems(emailManager.getEmailAccount());
		emailAccounts.setValue(emailManager.getEmailAccount().get(0));
	}
}
